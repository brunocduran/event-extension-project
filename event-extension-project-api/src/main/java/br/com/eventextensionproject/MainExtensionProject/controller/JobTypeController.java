package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.entity.JobType;
import br.com.eventextensionproject.MainExtensionProject.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/jobType")
@CrossOrigin(value = "*")
public class JobTypeController {

    @Autowired
    private JobTypeService service;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<JobType> result = service.getAll();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody JobType jobType) {
        JobType result = service.save(jobType);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idJobType}")
    public ResponseEntity<Object> delete(@PathVariable Long idJobType) {
        HashMap<String, Object> result = service.delete(idJobType);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idJobType}")
    public ResponseEntity<Object> findById(@PathVariable Long idJobType) {
        Optional<JobType> result = service.findById(idJobType);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody JobType jobType) {
        JobType result = service.update(jobType);
        return ResponseEntity.ok().body(result);
    }

}
