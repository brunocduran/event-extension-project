package br.com.eventextensionproject.MainExtensionProject.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import br.com.eventextensionproject.MainExtensionProject.entity.*;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentFinancialStatus;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentStatus;
import br.com.eventextensionproject.MainExtensionProject.repository.EnrollmentRepository;
import br.com.eventextensionproject.MainExtensionProject.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.eventextensionproject.MainExtensionProject.dto.EventDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.StatusEvent;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private InstitutionService institutionService;
    @Autowired
    private EventCategoryService eventCategoryService;
    @Autowired
    private PersonService personService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;

    public List<Event> getAll() {
        return eventRepository.findByOrderByEventStartDate();
    }

    public List<Event> getAllHome(StatusEvent statusEvent) {
        return eventRepository.findByStatusEventOrderByEventStartDate(statusEvent);
    }

    public List<Event> listEventByOrganizer(Long idPerson) {
        Person person = personService.findById(idPerson);
        return eventRepository.findEventByOrganizer(person);
    }

    public Event save(EventDTO eventDTO) throws IOException {
        Event event = new Event(eventDTO);

        if (eventDTO.getIdEvent() != null) {
            Event oldEvent = eventRepository.findById(eventDTO.getIdEvent()).orElse(null);
            event.setStatusEvent(oldEvent.getStatusEvent());
        }

        if (eventDTO.getPictureEvent().isEmpty()) {
            throw new DataIntegrityViolationException("Campo Imagem não pode ser vazio!");
        }

        event.setEventCategory(eventCategoryService.findBy(eventDTO.getIdEventCategory()));

        List<Course> courseList = new ArrayList<>();
        for (Long courseId : eventDTO.getCourses()) {
            courseList.add(courseService.findBy(courseId));
        }
        event.setCourses(courseList);

        List<Institution> institutionList = new ArrayList<>();
        for (Long institutionId : eventDTO.getInstitutions()) {
            institutionList.add(institutionService.findById(institutionId));
        }
        event.setInstitutions(institutionList);

        if (validateEvent(event)) {
            event = eventRepository.saveAndFlush(event);
            saveImage(event.getIdEvent(), event.getNameEvent(), eventDTO.getPictureEvent());
            return event;
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public HashMap<String, Object> alterarStatus(long eventId, Integer status) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            event.setStatusEvent(StatusEvent.parse(status));
            eventRepository.saveAndFlush(event);

            if (event.getStatusEvent() == StatusEvent.FINALIZADO) {
                unsubscribeUnpaid(event);
            } else if (event.getStatusEvent() == StatusEvent.CANCELADO) {
                unsubscribeAll(event);
            }

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("result", "Evento " + event.getNameEvent() + " alterou o status para " + event.getStatusEvent().getDescription() + " com sucesso!");
            return result;
        } else {
            throw new DataIntegrityViolationException("Evento não encontrado");
        }
    }

    public Event findById(Long idEvent) {
        Optional<Event> obj = eventRepository.findById(idEvent);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Evento não encontrado!"));
    }

    public void saveImage(Long id, String name, MultipartFile image) throws IOException {
        String type = image.getContentType();
        String[] parts = type.split("/");
        String extension = "";
        if (parts.length > 1) {
            extension = parts[1];
        }

        File convertFile = new File(FILE_DIRECTORY + id + '-' + name + '.' + extension);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(image.getBytes());
        fout.close();
    }

    private boolean validateEvent(Event event) {
        if (event.getNameEvent().equals("")) {
            throw new DataIntegrityViolationException("Todos os campos são obrigatórios");
        }
        return true;
    }

    public void unsubscribeUnpaid(Event event) {
        List<Enrollment> enrollments = findByEvent(event.getIdEvent());

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getEnrollmentFinancialStatus() == EnrollmentFinancialStatus.ABERTO) {
                enrollment.setEnrollmentStatus(EnrollmentStatus.CANCELADA);
                enrollmentRepository.saveAndFlush(enrollment);
            }
        }
    }

    public void unsubscribeAll(Event event) {
        List<Enrollment> enrollments = findByEvent(event.getIdEvent());

        for (Enrollment enrollment : enrollments) {
            enrollment.setEnrollmentStatus(EnrollmentStatus.CANCELADA);
            enrollmentRepository.saveAndFlush(enrollment);
        }
    }

    public List<Enrollment> findByEvent(Long idEvent) {
        Event event = findById(idEvent);
        return enrollmentRepository.findByEventOrderByPersonNamePerson(event);
    }
}
