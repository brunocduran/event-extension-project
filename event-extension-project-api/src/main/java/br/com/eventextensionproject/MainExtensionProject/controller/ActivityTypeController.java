package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.entity.ActivityType;
import br.com.eventextensionproject.MainExtensionProject.service.ActivityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/activityType")
@CrossOrigin(value = "*")
public class ActivityTypeController {

    @Autowired
    private ActivityTypeService service;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<ActivityType> result = service.getAll();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody ActivityType activityType) {
        ActivityType result = service.save(activityType);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idActivityType}")
    public ResponseEntity<Object> delete(@PathVariable Long idActivityType) {
        HashMap<String, Object> result = service.delete(idActivityType);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idActivityType}")
    public ResponseEntity<Object> findById(@PathVariable Long idActivityType) {
        Optional<ActivityType> result = service.findById(idActivityType);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody ActivityType activityType) {
        ActivityType result = service.update(activityType);
        return ResponseEntity.ok().body(result);
    }

}