package br.com.eventextensionproject.MainExtensionProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import br.com.eventextensionproject.MainExtensionProject.entity.Course;
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
import br.com.eventextensionproject.MainExtensionProject.service.CourseService;

@RestController
@RequestMapping(value = "/api/v1/course")
@CrossOrigin(value = "*")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<Course> result = courseService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody Course course) {
        Course result = courseService.save(course);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idCourse}")
    public ResponseEntity<Object> delete(@PathVariable Long idCourse) {
        HashMap<String, Object> result = courseService.delete(idCourse);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idCourse}")
    public ResponseEntity<Object> findById(@PathVariable Long idCourse) {
        Optional<Course> result = courseService.findById(idCourse);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Course course) {
        Course result = courseService.update(course);
        return ResponseEntity.ok().body(result);
    }
}

