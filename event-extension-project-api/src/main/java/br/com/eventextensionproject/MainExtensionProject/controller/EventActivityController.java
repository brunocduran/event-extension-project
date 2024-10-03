package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.dto.EventActivityDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.EventActivity;
import br.com.eventextensionproject.MainExtensionProject.service.EventActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/eventActivity")
@CrossOrigin(value = "*")
public class EventActivityController {

    @Autowired
    private EventActivityService eventActivityService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<EventActivity> result = eventActivityService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idEventActivity}")
    public ResponseEntity<Object> findById(@PathVariable Long idEventActivity) {
        EventActivity result = eventActivityService.findById(idEventActivity);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEvent/{idEvent}")
    public ResponseEntity<Object> getByState(@PathVariable Long idEvent) {
        List<EventActivity> result = eventActivityService.findByEvent(idEvent);
        return ResponseEntity.ok().body(result);
    }

   @GetMapping(value = "/listByEventDate/{idEvent}")
    public ResponseEntity<Object> listActivityEventDate(@PathVariable Long idEvent,@RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
       List<EventActivity> result = eventActivityService.findByActivityEventDate(idEvent,date);
        return ResponseEntity.ok().body(result);
   }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody EventActivityDTO eventActivityDTO) {
        EventActivity result = eventActivityService.save(eventActivityDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idEventActivity}")
    public ResponseEntity<Object> delete(@PathVariable Long idEventActivity) {
        HashMap<String, Object> result = eventActivityService.delete(idEventActivity);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody EventActivityDTO eventActivityDTO) {
        EventActivity result = eventActivityService.update(eventActivityDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listDates/{idEvent}")
    public ResponseEntity<Object> getDates(@PathVariable Long idEvent) {
        List<LocalDate> result = eventActivityService.getDistinctRecentActivityDates(idEvent);
        return ResponseEntity.ok().body(result);
    }


}
