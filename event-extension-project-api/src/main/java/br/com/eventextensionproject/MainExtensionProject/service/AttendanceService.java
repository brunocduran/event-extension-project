package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.entity.Attendance;
import br.com.eventextensionproject.MainExtensionProject.entity.Enrollment;
import br.com.eventextensionproject.MainExtensionProject.entity.EventActivity;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AttendanceStatus;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentFinancialStatus;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.StatusEvent;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.AttendanceRepository;
import br.com.eventextensionproject.MainExtensionProject.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EventActivityService eventActivityService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Attendance> getAll() {
        return attendanceRepository.findAll();
    }

    public Attendance save(Attendance attendance) {
        return attendanceRepository.saveAndFlush(attendance);
    }

    public List<Attendance> findByEventActivity(Long idEventActivity) {
        EventActivity eventActivity = eventActivityService.findById(idEventActivity);
        return attendanceRepository.findByEventActivityOrderByEnrollmentPersonNamePerson(eventActivity);
    }

    public List<Attendance> findByEnrollment(Long idEnrollment) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(idEnrollment);
        return attendanceRepository.findByEnrollment(enrollment.get());
    }

    public Attendance findByEventActivityAndEnrollment(Long idEventActivity, String barCode) {
        EventActivity eventActivity = eventActivityService.findById(idEventActivity);
        Enrollment enrollment = findByBarCode(barCode);
        Optional<Attendance> obj = attendanceRepository.findByEventActivityAndEnrollment(eventActivity, enrollment);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Inscrição não pertence a esta atividade!"));
    }

    public Enrollment findByBarCode(String barCode) {
        Optional<Enrollment> obj = enrollmentRepository.findByBarCode(barCode);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Inscrição não encontrada!"));
    }

    public HashMap<String, Object> updateStatus(Long idEventActivity, String barCode) {
        String status = "";

        EventActivity eventActivity = eventActivityService.findById(idEventActivity);
        Enrollment enrollment = findByBarCode(barCode);

        if(enrollment.getEnrollmentFinancialStatus() == EnrollmentFinancialStatus.ABERTO){
            throw new DataIntegrityViolationException("Inscrição ainda não foi paga!");
        }

        if(enrollment.getEvent().getStatusEvent() == StatusEvent.FINALIZADO){
            throw new DataIntegrityViolationException("O evento já foi finalizado, não é possível atribuir presença");
        }else if(enrollment.getEvent().getStatusEvent() != StatusEvent.ENCERRADO){
            throw new DataIntegrityViolationException("Inscrições deste evento ainda não foram encerradas, não é possível atribuir presença");
        }

        Optional<Attendance> attendance =
                Optional.ofNullable(attendanceRepository.findByEventActivityAndEnrollment(eventActivity, enrollment).
                        orElseThrow(() -> new ObjectnotFoundException("Inscrição não pertence a está atividade!")));

        if (attendance.get().getAttendanceStatus() == AttendanceStatus.AUSENTE) {
            attendance.get().setAttendanceStatus(AttendanceStatus.PRESENTE);
            status = "Presença registrada com sucesso!";
        } else {
            attendance.get().setAttendanceStatus(AttendanceStatus.AUSENTE);
            status = "Presença retirada com sucesso!";
        }

        attendanceRepository.saveAndFlush(attendance.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("message", status);
        return result;
    }

}
