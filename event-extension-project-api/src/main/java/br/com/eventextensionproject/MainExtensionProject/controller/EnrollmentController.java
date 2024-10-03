package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.dto.EnrollmentDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.*;
import br.com.eventextensionproject.MainExtensionProject.entity.record.EnrollmentResponse;
import br.com.eventextensionproject.MainExtensionProject.entity.record.EventResponse;
import br.com.eventextensionproject.MainExtensionProject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/enrollment")
@CrossOrigin(value = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private EventActivityService eventActivityService;
    @Autowired
    private EventOrganizerService eventOrganizerService;
    @Autowired
    private LotService lotService;
    @Autowired
    private AttendanceService attendanceService;

    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<Enrollment> result = enrollmentService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idEnrollment}")
    public ResponseEntity<Object> findById(@PathVariable Long idEnrollment) {
        Enrollment enrollment = enrollmentService.findById(idEnrollment);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/event/image/")
                .path(Long.toString(enrollment.getEvent().getIdEvent()))
                .toUriString();

        List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(enrollment.getEvent().getIdEvent());
        List<EventActivity> listEventActivity = eventActivityService.findByEvent(enrollment.getEvent().getIdEvent());
        List<Lot> litLot = lotService.findByEvent(enrollment.getEvent().getIdEvent());
        List<Attendance> listAttendance = attendanceService.findByEnrollment(enrollment.getIdEnrollment());

        EventResponse eventResponse = new EventResponse(
                enrollment.getEvent().getIdEvent(),
                enrollment.getEvent().getNameEvent(),
                enrollment.getEvent().getEventStartDate(),
                enrollment.getEvent().getEventEndDate(),
                enrollment.getEvent().getDescriptionEvent(),
                enrollment.getEvent().getEventCategory(),
                enrollment.getEvent().getNameImage(),
                fileDownloadUri,
                enrollment.getEvent().getTypeImage(),
                enrollment.getEvent().getStatusEvent(),
                enrollment.getEvent().getCourses(),
                enrollment.getEvent().getInstitutions(),
                listEventOrganizer,
                listEventActivity,
                litLot);

        EnrollmentResponse result = new EnrollmentResponse(
                enrollment.getIdEnrollment(),
                enrollment.getBarCode(),
                enrollment.getPerson(),
                eventResponse,
                enrollment.getEventActivities(),
                enrollment.getLot(),
                enrollment.getEnrollmentStatus(),
                enrollment.getEnrollmentFinancialStatus(),
                listAttendance);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByBarCode/{barCode}")
    public ResponseEntity<Object> findByBarCode(@PathVariable String barCode) {
        Enrollment result = enrollmentService.findByBarCode(barCode);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEvent/{idEvent}")
    public ResponseEntity<Object> getByEvent(@PathVariable Long idEvent) {
        List<Enrollment> result = enrollmentService.findByEvent(idEvent);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByPerson/{idPerson}")
    public ResponseEntity<Object> getByPerson(@PathVariable Long idPerson) {
        List<EnrollmentResponse> result = enrollmentService.findByPerson(idPerson).stream().map(enrollment -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/event/image/")
                    .path(Long.toString(enrollment.getEvent().getIdEvent()))
                    .toUriString();

            List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(enrollment.getEvent().getIdEvent());
            List<EventActivity> listEventActivity = eventActivityService.findByEvent(enrollment.getEvent().getIdEvent());
            List<Lot> litLot = lotService.findByEvent(enrollment.getEvent().getIdEvent());
            List<Attendance> listAttendance = attendanceService.findByEnrollment(enrollment.getIdEnrollment());

            EventResponse eventResponse = new EventResponse(
                    enrollment.getEvent().getIdEvent(),
                    enrollment.getEvent().getNameEvent(),
                    enrollment.getEvent().getEventStartDate(),
                    enrollment.getEvent().getEventEndDate(),
                    enrollment.getEvent().getDescriptionEvent(),
                    enrollment.getEvent().getEventCategory(),
                    enrollment.getEvent().getNameImage(),
                    fileDownloadUri,
                    enrollment.getEvent().getTypeImage(),
                    enrollment.getEvent().getStatusEvent(),
                    enrollment.getEvent().getCourses(),
                    enrollment.getEvent().getInstitutions(),
                    listEventOrganizer,
                    listEventActivity,
                    litLot);

            return new EnrollmentResponse(
                    enrollment.getIdEnrollment(),
                    enrollment.getBarCode(),
                    enrollment.getPerson(),
                    eventResponse,
                    enrollment.getEventActivities(),
                    enrollment.getLot(),
                    enrollment.getEnrollmentStatus(),
                    enrollment.getEnrollmentFinancialStatus(),
                    listAttendance);
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByPerson/{idPerson}/statusEvent/{idStatus}")
    public ResponseEntity<Object> getByPersonAndStatusEvent(@PathVariable Long idPerson, @PathVariable Integer idStatus) {
        List<EnrollmentResponse> result = enrollmentService.findByPersonAndStatusEvent(idPerson, idStatus).stream().map(enrollment -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/event/image/")
                    .path(Long.toString(enrollment.getEvent().getIdEvent()))
                    .toUriString();

            List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(enrollment.getEvent().getIdEvent());
            List<EventActivity> listEventActivity = eventActivityService.findByEvent(enrollment.getEvent().getIdEvent());
            List<Lot> litLot = lotService.findByEvent(enrollment.getEvent().getIdEvent());
            List<Attendance> listAttendance = attendanceService.findByEnrollment(enrollment.getIdEnrollment());

            EventResponse eventResponse = new EventResponse(
                    enrollment.getEvent().getIdEvent(),
                    enrollment.getEvent().getNameEvent(),
                    enrollment.getEvent().getEventStartDate(),
                    enrollment.getEvent().getEventEndDate(),
                    enrollment.getEvent().getDescriptionEvent(),
                    enrollment.getEvent().getEventCategory(),
                    enrollment.getEvent().getNameImage(),
                    fileDownloadUri,
                    enrollment.getEvent().getTypeImage(),
                    enrollment.getEvent().getStatusEvent(),
                    enrollment.getEvent().getCourses(),
                    enrollment.getEvent().getInstitutions(),
                    listEventOrganizer,
                    listEventActivity,
                    litLot);

            return new EnrollmentResponse(
                    enrollment.getIdEnrollment(),
                    enrollment.getBarCode(),
                    enrollment.getPerson(),
                    eventResponse,
                    enrollment.getEventActivities(),
                    enrollment.getLot(),
                    enrollment.getEnrollmentStatus(),
                    enrollment.getEnrollmentFinancialStatus(),
                    listAttendance);
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByPerson/{idPerson}/statusEnrollment/{idStatus}")
    public ResponseEntity<Object> getByPersonAndStatusEnrollment(@PathVariable Long idPerson, @PathVariable Integer idStatus) {
        List<EnrollmentResponse> result = enrollmentService.findByPersonAndStatusEnrollment(idPerson, idStatus).stream().map(enrollment -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/event/image/")
                    .path(Long.toString(enrollment.getEvent().getIdEvent()))
                    .toUriString();

            List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(enrollment.getEvent().getIdEvent());
            List<EventActivity> listEventActivity = eventActivityService.findByEvent(enrollment.getEvent().getIdEvent());
            List<Lot> litLot = lotService.findByEvent(enrollment.getEvent().getIdEvent());
            List<Attendance> listAttendance = attendanceService.findByEnrollment(enrollment.getIdEnrollment());

            EventResponse eventResponse = new EventResponse(
                    enrollment.getEvent().getIdEvent(),
                    enrollment.getEvent().getNameEvent(),
                    enrollment.getEvent().getEventStartDate(),
                    enrollment.getEvent().getEventEndDate(),
                    enrollment.getEvent().getDescriptionEvent(),
                    enrollment.getEvent().getEventCategory(),
                    enrollment.getEvent().getNameImage(),
                    fileDownloadUri,
                    enrollment.getEvent().getTypeImage(),
                    enrollment.getEvent().getStatusEvent(),
                    enrollment.getEvent().getCourses(),
                    enrollment.getEvent().getInstitutions(),
                    listEventOrganizer,
                    listEventActivity,
                    litLot);

            return new EnrollmentResponse(
                    enrollment.getIdEnrollment(),
                    enrollment.getBarCode(),
                    enrollment.getPerson(),
                    eventResponse,
                    enrollment.getEventActivities(),
                    enrollment.getLot(),
                    enrollment.getEnrollmentStatus(),
                    enrollment.getEnrollmentFinancialStatus(),
                    listAttendance);
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEventActivity/{idEventActivity}")
    public ResponseEntity<Object> getByEventActivity(@PathVariable Long idEventActivity) {
        List<Enrollment> result = enrollmentService.findByEventActivity(idEventActivity);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody EnrollmentDTO enrollmentDTO) throws IOException {
        Enrollment result = enrollmentService.save(enrollmentDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/updateStatusFinancial/{idEnrollment}")
    public ResponseEntity<Object> updateStatusFinancial(@PathVariable Long idEnrollment) {
        HashMap<String, Object> result = enrollmentService.updateStatusFinancial(idEnrollment);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idEnrollment}")
    public ResponseEntity<Object> delete(@PathVariable Long idEnrollment) {
        HashMap<String, Object> result = enrollmentService.delete(idEnrollment);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByIdCertificate/{idEnrollment}")
    public ResponseEntity<Object> findByIdCertificate(@PathVariable Long idEnrollment) {
        Enrollment enrollment = enrollmentService.findById(idEnrollment);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/event/image/")
                .path(Long.toString(enrollment.getEvent().getIdEvent()))
                .toUriString();

        List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(enrollment.getEvent().getIdEvent());
        List<EventActivity> listEventActivity = eventActivityService.findByEvent(enrollment.getEvent().getIdEvent());
        List<Lot> litLot = lotService.findByEvent(enrollment.getEvent().getIdEvent());
        List<Attendance> listAttendance = attendanceService.findByEnrollment(enrollment.getIdEnrollment());
        enrollment.setEventActivities(eventActivityService.findEventActivityCertificate(enrollment));

        EventResponse eventResponse = new EventResponse(
                enrollment.getEvent().getIdEvent(),
                enrollment.getEvent().getNameEvent(),
                enrollment.getEvent().getEventStartDate(),
                enrollment.getEvent().getEventEndDate(),
                enrollment.getEvent().getDescriptionEvent(),
                enrollment.getEvent().getEventCategory(),
                enrollment.getEvent().getNameImage(),
                fileDownloadUri,
                enrollment.getEvent().getTypeImage(),
                enrollment.getEvent().getStatusEvent(),
                enrollment.getEvent().getCourses(),
                enrollment.getEvent().getInstitutions(),
                listEventOrganizer,
                listEventActivity,
                litLot);

        EnrollmentResponse result = new EnrollmentResponse(
                enrollment.getIdEnrollment(),
                enrollment.getBarCode(),
                enrollment.getPerson(),
                eventResponse,
                enrollment.getEventActivities(),
                enrollment.getLot(),
                enrollment.getEnrollmentStatus(),
                enrollment.getEnrollmentFinancialStatus(),
                listAttendance);

        return ResponseEntity.ok().body(result);
    }
}
