package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.dto.EventOrganizerDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.EventOrganizer;
import br.com.eventextensionproject.MainExtensionProject.service.EventOrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/eventOrganizer")
@CrossOrigin(value = "*")
public class EventOrganizerController {

    @Autowired
    private EventOrganizerService eventOrganizerService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<EventOrganizer> result = eventOrganizerService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idEventOrganizer}")
    public ResponseEntity<Object> findById(@PathVariable Long idEventOrganizer) {
        EventOrganizer result = eventOrganizerService.findById(idEventOrganizer);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEvent/{idEvent}")
    public ResponseEntity<Object> getByState(@PathVariable Long idEvent) {
        List<EventOrganizer> result = eventOrganizerService.findByEvent(idEvent);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody EventOrganizerDTO eventOrganizerDTO) {
        EventOrganizer result = eventOrganizerService.save(eventOrganizerDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idEventOrganizer}")
    public ResponseEntity<Object> delete(@PathVariable Long idEventOrganizer) {
        HashMap<String, Object> result = eventOrganizerService.delete(idEventOrganizer);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody EventOrganizerDTO eventOrganizerDTO) {
        EventOrganizer result = eventOrganizerService.update(eventOrganizerDTO);
        return ResponseEntity.ok().body(result);
    }
}
