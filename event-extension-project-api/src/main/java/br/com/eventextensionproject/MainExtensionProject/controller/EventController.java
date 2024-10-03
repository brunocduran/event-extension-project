package br.com.eventextensionproject.MainExtensionProject.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import br.com.eventextensionproject.MainExtensionProject.entity.EventActivity;
import br.com.eventextensionproject.MainExtensionProject.entity.EventOrganizer;
import br.com.eventextensionproject.MainExtensionProject.entity.Lot;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.StatusEvent;
import br.com.eventextensionproject.MainExtensionProject.service.EventActivityService;
import br.com.eventextensionproject.MainExtensionProject.service.EventOrganizerService;
import br.com.eventextensionproject.MainExtensionProject.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.eventextensionproject.MainExtensionProject.dto.EventDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.record.EventResponse;
import br.com.eventextensionproject.MainExtensionProject.service.EventService;

@RestController
@RequestMapping(value = "/api/v1/event")
@CrossOrigin(value = "*")
public class EventController {

    @Autowired
    private EventService service;
    @Autowired
    private EventActivityService eventActivityService;
    @Autowired
    private EventOrganizerService eventOrganizerService;
    @Autowired
    private LotService lotService;

    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<EventResponse> result = service.getAll().stream().map(event -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/event/image/")
                    .path(Long.toString(event.getIdEvent()))
                    .toUriString();

            List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(event.getIdEvent());
            List<EventActivity> listEventActivity = eventActivityService.findByEvent(event.getIdEvent());
            List<Lot> litLot = lotService.findByEvent(event.getIdEvent());

            return new EventResponse(
                    event.getIdEvent(),
                    event.getNameEvent(),
                    event.getEventStartDate(),
                    event.getEventEndDate(),
                    event.getDescriptionEvent(),
                    event.getEventCategory(),
                    event.getNameImage(),
                    fileDownloadUri,
                    event.getTypeImage(),
                    event.getStatusEvent(),
                    event.getCourses(),
                    event.getInstitutions(),
                    listEventOrganizer,
                    listEventActivity,
                    litLot);
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(result);

    }

    @GetMapping(value = "/listHome")
    public ResponseEntity<Object> getAllHome() {
        List<EventResponse> result = service.getAllHome(StatusEvent.INICIADO).stream().map(event -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/event/image/")
                    .path(Long.toString(event.getIdEvent()))
                    .toUriString();

            List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(event.getIdEvent());
            List<EventActivity> listEventActivity = eventActivityService.findByEvent(event.getIdEvent());
            List<Lot> litLot = new ArrayList<>();
            Lot lot = lotService.findByEventAndActive(event, Situation.ATIVO);
            litLot.add(lot);

            return new EventResponse(
                    event.getIdEvent(),
                    event.getNameEvent(),
                    event.getEventStartDate(),
                    event.getEventEndDate(),
                    event.getDescriptionEvent(),
                    event.getEventCategory(),
                    event.getNameImage(),
                    fileDownloadUri,
                    event.getTypeImage(),
                    event.getStatusEvent(),
                    event.getCourses(),
                    event.getInstitutions(),
                    listEventOrganizer,
                    listEventActivity,
                    litLot);
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(result);

    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(
            @RequestParam("nameEvent") String nameEvent,
            @RequestParam("eventStartDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime eventStartDate,
            @RequestParam("eventEndDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime eventEndDate,
            @RequestParam("descriptionEvent") String descriptionEvent,
            @RequestParam("idEventCategory") Long idEventCategory,
            @RequestParam("pictureEvent") MultipartFile pictureEvent,
            @RequestParam("courses") Set<Long> courses,
            @RequestParam("institutions") Set<Long> institutions) throws IOException {

        EventDTO eventDTO = new EventDTO();
        eventDTO.setNameEvent(nameEvent);
        eventDTO.setEventStartDate(eventStartDate);
        eventDTO.setEventEndDate(eventEndDate);
        eventDTO.setDescriptionEvent(descriptionEvent);
        eventDTO.setIdEventCategory(idEventCategory);
        eventDTO.setPictureEvent(pictureEvent);
        eventDTO.setCourses(courses);
        eventDTO.setInstitutions(institutions);

        Event event = service.save(eventDTO);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/event/image/")
                .path(Long.toString(event.getIdEvent()))
                .toUriString();

        List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(event.getIdEvent());
        List<EventActivity> listEventActivity = eventActivityService.findByEvent(event.getIdEvent());
        List<Lot> litLot = lotService.findByEvent(event.getIdEvent());

        EventResponse result = new EventResponse(
                event.getIdEvent(),
                event.getNameEvent(),
                event.getEventStartDate(),
                event.getEventEndDate(),
                event.getDescriptionEvent(),
                event.getEventCategory(),
                event.getNameImage(),
                fileDownloadUri,
                event.getTypeImage(),
                event.getStatusEvent(),
                event.getCourses(),
                event.getInstitutions(),
                listEventOrganizer,
                listEventActivity,
                litLot);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Event event = service.findById(id);
        String type = event.getTypeImage();
        String[] parts = type.split("/");
        String extension = "";
        if (parts.length > 1) {
            extension = parts[1];
        }

        File file = new File(FILE_DIRECTORY + id + '-' + event.getNameEvent() + '.' + extension);
        byte[] imagemBytes = new byte[0];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            imagemBytes = new byte[(int) file.length()];
            inputStream.read(imagemBytes);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + event.getNameImage() + "\"")
                .body(imagemBytes);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(
            @RequestParam("idEvent") Long idEvent,
            @RequestParam("nameEvent") String nameEvent,
            @RequestParam("eventStartDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime eventStartDate,
            @RequestParam("eventEndDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime eventEndDate,
            @RequestParam("descriptionEvent") String descriptionEvent,
            @RequestParam("idEventCategory") Long idEventCategory,
            @RequestParam("pictureEvent") MultipartFile pictureEvent,
            @RequestParam("courses") Set<Long> courses,
            @RequestParam("institutions") Set<Long> institutions) throws IOException {

        EventDTO eventDTO = new EventDTO();
        eventDTO.setIdEvent(idEvent);
        eventDTO.setNameEvent(nameEvent);
        eventDTO.setEventStartDate(eventStartDate);
        eventDTO.setEventEndDate(eventEndDate);
        eventDTO.setDescriptionEvent(descriptionEvent);
        eventDTO.setIdEventCategory(idEventCategory);
        eventDTO.setPictureEvent(pictureEvent);
        eventDTO.setCourses(courses);
        eventDTO.setInstitutions(institutions);

        Event event = service.save(eventDTO);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/event/image/")
                .path(Long.toString(event.getIdEvent()))
                .toUriString();

        List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(event.getIdEvent());
        List<EventActivity> listEventActivity = eventActivityService.findByEvent(event.getIdEvent());
        List<Lot> litLot = lotService.findByEvent(event.getIdEvent());

        EventResponse result = new EventResponse(
                event.getIdEvent(),
                event.getNameEvent(),
                event.getEventStartDate(),
                event.getEventEndDate(),
                event.getDescriptionEvent(),
                event.getEventCategory(),
                event.getNameImage(),
                fileDownloadUri,
                event.getTypeImage(),
                event.getStatusEvent(),
                event.getCourses(),
                event.getInstitutions(),
                listEventOrganizer,
                listEventActivity,
                litLot);

        return ResponseEntity.ok().body(result);
    }


    @PutMapping(value = "/{id}/updateStatus/{status}")
    public ResponseEntity<Object> alterarStatus(@PathVariable Long id, @PathVariable Integer status) {
        HashMap<String, Object> result = service.alterarStatus(id, status);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idEvent}")
    public ResponseEntity<Object> findById(@PathVariable Long idEvent) {
        Event event = service.findById(idEvent);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/event/image/")
                .path(Long.toString(event.getIdEvent()))
                .toUriString();

        List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(event.getIdEvent());
        List<EventActivity> listEventActivity = eventActivityService.findByEvent(event.getIdEvent());
        List<Lot> litLot = lotService.findByEvent(event.getIdEvent());

        EventResponse result = new EventResponse(
                event.getIdEvent(),
                event.getNameEvent(),
                event.getEventStartDate(),
                event.getEventEndDate(),
                event.getDescriptionEvent(),
                event.getEventCategory(),
                event.getNameImage(),
                fileDownloadUri,
                event.getTypeImage(),
                event.getStatusEvent(),
                event.getCourses(),
                event.getInstitutions(),
                listEventOrganizer,
                listEventActivity,
                litLot);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByIdHome/{idEvent}")
    public ResponseEntity<Object> findByIdHome(@PathVariable Long idEvent) {
        Event event = service.findById(idEvent);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/event/image/")
                .path(Long.toString(event.getIdEvent()))
                .toUriString();

        List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(event.getIdEvent());
        List<EventActivity> listEventActivity = eventActivityService.findByEvent(event.getIdEvent());
        List<Lot> litLot = new ArrayList<>();
        Lot lot = lotService.findByEventAndActiveHome(event, Situation.ATIVO);
        litLot.add(lot);

        EventResponse result = new EventResponse(
                event.getIdEvent(),
                event.getNameEvent(),
                event.getEventStartDate(),
                event.getEventEndDate(),
                event.getDescriptionEvent(),
                event.getEventCategory(),
                event.getNameImage(),
                fileDownloadUri,
                event.getTypeImage(),
                event.getStatusEvent(),
                event.getCourses(),
                event.getInstitutions(),
                listEventOrganizer,
                listEventActivity,
                litLot);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listEventByOrganizer/{idPerson}")
    public ResponseEntity<Object> listEventByOrganizer(@PathVariable Long idPerson) {
        List<EventResponse> result = service.listEventByOrganizer(idPerson).stream().map(event -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/event/image/")
                    .path(Long.toString(event.getIdEvent()))
                    .toUriString();

            List<EventOrganizer> listEventOrganizer = eventOrganizerService.findByEvent(event.getIdEvent());
            List<EventActivity> listEventActivity = eventActivityService.findByEvent(event.getIdEvent());
            List<Lot> litLot = lotService.findByEvent(event.getIdEvent());

            return new EventResponse(
                    event.getIdEvent(),
                    event.getNameEvent(),
                    event.getEventStartDate(),
                    event.getEventEndDate(),
                    event.getDescriptionEvent(),
                    event.getEventCategory(),
                    event.getNameImage(),
                    fileDownloadUri,
                    event.getTypeImage(),
                    event.getStatusEvent(),
                    event.getCourses(),
                    event.getInstitutions(),
                    listEventOrganizer,
                    listEventActivity,
                    litLot);
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(result);

    }

}
