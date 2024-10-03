package br.com.eventextensionproject.MainExtensionProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import br.com.eventextensionproject.MainExtensionProject.entity.EventCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.eventextensionproject.MainExtensionProject.service.EventCategoryService;

@RestController
@RequestMapping(value = "/api/v1/eventCategory")
@CrossOrigin(value = "*")
public class EventCategoryController {

    @Autowired
    private EventCategoryService eventCategoryService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<EventCategory> result = eventCategoryService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody EventCategory eventCategory) {
        EventCategory result = eventCategoryService.save(eventCategory);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idEventCategory}")
    public ResponseEntity<Object> delete(@PathVariable Long idEventCategory) {
        HashMap<String, Object> result = eventCategoryService.delete(idEventCategory);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idEventCategory}")
    public ResponseEntity<Object> findById(@PathVariable Long idEventCategory) {
        Optional<EventCategory> result = eventCategoryService.findById(idEventCategory);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody EventCategory eventCategory) {
        EventCategory result = eventCategoryService.update(eventCategory);
        return ResponseEntity.ok().body(result);
    }
}
