package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.dto.EnrollmentDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.*;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentFinancialStatus;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentStatus;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.StatusEvent;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private EventService eventService;
    @Autowired
    private LotService lotService;
    @Autowired
    private EventActivityService eventActivityService;
    @Autowired
    private AttendanceService attendanceService;

    public List<Enrollment> getAll() {
        return enrollmentRepository.findAll();
    }

    public Enrollment save(EnrollmentDTO enrollmentDTO) throws IOException {
        Enrollment enrollment = new Enrollment();
        enrollment.setPerson(personService.findById(enrollmentDTO.getIdPerson()));
        enrollment.setEvent(eventService.findById(enrollmentDTO.getIdEvent()));
        enrollment.setEventActivities(eventActivityService.findByEvent(enrollmentDTO.getIdEvent()));
        enrollment.setLot(lotService.findById(enrollmentDTO.getIdLot()));

        Enrollment oldEnrollment = findByEventAndPersonAndEnrollmentStatus(enrollment.getEvent(), enrollment.getPerson());

        if(oldEnrollment != null){
            throw new DataIntegrityViolationException("Você já está inscrito neste evento!");
        }

        Enrollment newEnrollment = enrollmentRepository.saveAndFlush(enrollment);
        newEnrollment.setBarCode(calculateBarCode(newEnrollment.getIdEnrollment()));

        //Add quantidade presenca
        lotService.addRegistrations(newEnrollment.getLot());
        for (EventActivity eventActivity : newEnrollment.getEventActivities()) {
            eventActivityService.addRegistrations(eventActivity);
        }
        //

        for (EventActivity eventActivity : newEnrollment.getEventActivities()) {
            Attendance attendance = new Attendance(newEnrollment, eventActivity);
            attendanceService.save(attendance);
        }

        return enrollmentRepository.saveAndFlush(newEnrollment);
    }

    public Enrollment findById(Long idEnrollment) {
        Optional<Enrollment> obj = enrollmentRepository.findById(idEnrollment);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Inscrição não encontrada!"));
    }

    public Enrollment findByBarCode(String barCode) {
        Optional<Enrollment> obj = enrollmentRepository.findByBarCode(barCode);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Inscrição não encontrada!"));
    }

    public List<Enrollment> findByEvent(Long idEvent) {
        Event event = eventService.findById(idEvent);
        return enrollmentRepository.findByEventOrderByPersonNamePerson(event);
    }

    public List<Enrollment> findByPerson(Long idPerson) {
        Person person = personService.findById(idPerson);
        return enrollmentRepository.findByPersonOrderByEventEventStartDate(person);
    }

    public List<Enrollment> findByPersonAndStatusEvent(Long idPerson, Integer status) {
        Person person = personService.findById(idPerson);
        StatusEvent statusEvent = StatusEvent.parse(status);
        return enrollmentRepository.findByPersonAndEnrollmentStatusAndEventStatusEventOrderByEventEventStartDate(person, EnrollmentStatus.ATIVO, statusEvent);
    }

    public List<Enrollment> findByPersonAndStatusEnrollment(Long idPerson, Integer status) {
        Person person = personService.findById(idPerson);
        EnrollmentStatus enrollmentStatus = EnrollmentStatus.parse(status);
        return enrollmentRepository.findByPersonAndEnrollmentStatusOrderByEventEventStartDate(person, enrollmentStatus);
    }


    public List<Enrollment> findByEventActivity(Long idEventActivity) {
        EventActivity eventActivity = eventActivityService.findById(idEventActivity);
        return enrollmentRepository.findByEventActivitiesOrderByPersonNamePerson(eventActivity);
    }

    public Enrollment findByEventAndPersonAndEnrollmentStatus(Event event, Person person) {
        return enrollmentRepository.findByEventAndPersonAndEnrollmentStatus(event, person, EnrollmentStatus.ATIVO);
    }

    public HashMap<String, Object> updateStatusFinancial(Long idEnrollment) {
        String status = "";

        Optional<Enrollment> enrollment =
                Optional.ofNullable(enrollmentRepository.findById(idEnrollment).
                        orElseThrow(() -> new ObjectnotFoundException("Inscrição não encontrada!")));

        if(enrollment.get().getEnrollmentStatus() == EnrollmentStatus.CANCELADA){
            status = "Inscrição cancelada, não é possível realizar o recebimento!";
        }else if (enrollment.get().getEnrollmentFinancialStatus() == EnrollmentFinancialStatus.ABERTO) {
            enrollment.get().setEnrollmentFinancialStatus(EnrollmentFinancialStatus.PAGO);
            status = "Inscrição recebida com sucesso!";
        } else {
            enrollment.get().setEnrollmentFinancialStatus(EnrollmentFinancialStatus.ABERTO);
            status = "Inscrição estornada com sucesso!";
        }

        enrollmentRepository.saveAndFlush(enrollment.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", status);
        return result;
    }

    public HashMap<String, Object> delete(Long idEnrollment) {
        String status = "";

        Optional<Enrollment> enrollment =
                Optional.ofNullable(enrollmentRepository.findById(idEnrollment).
                        orElseThrow(() -> new ObjectnotFoundException("Inscrição não encontrada!")));

        if(enrollment.get().getEnrollmentFinancialStatus() == EnrollmentFinancialStatus.PAGO){
            status = "Incrição já foi paga, não é possível cancelar!";
        }else if (enrollment.get().getEnrollmentStatus() == EnrollmentStatus.ATIVO) {
            enrollment.get().setEnrollmentStatus(EnrollmentStatus.CANCELADA);

            //subtrair quantidade presenca
            lotService.subtractRegistrations(enrollment.get().getLot());

            List<EventActivity> eventActivityList = new ArrayList<>();
            eventActivityList = enrollment.get().getEventActivities();
            for (EventActivity eventActivity : eventActivityList) {
                eventActivityService.subtractRegistrations(eventActivity);
            }

            status = "Inscrição cancelada com sucesso!";
        } else {
            status = "Inscrição já cancelada!";
        }

        enrollmentRepository.saveAndFlush(enrollment.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", status);
        return result;
    }

    public String calculateBarCode(Long idEnrollment){
        Long code = 100000000000L;
        code = code + idEnrollment;
        String codeString = Long.toString(code);

        Integer caractere;
        char caractereChar = '0';

        Integer somatoria = 0;
        Integer dv = 0;

        String codeBar;

        for(int i=0; i < 12; i++){
            caractereChar = codeString.charAt(i);
            caractere = Integer.parseInt(String.valueOf(caractereChar));

            if (i % 2 == 0) {
                somatoria = somatoria + (caractere * 1);
            } else {
                somatoria = somatoria + (caractere * 3);
            }
        };

        for(int i=10; i <= 100; i=i+10){
            if(dv == 0){
                if(somatoria < i){
                    dv = i - somatoria;
                }
            }
        }

        codeBar = codeString + Integer.toString(dv);

        return codeBar;
    }
}
