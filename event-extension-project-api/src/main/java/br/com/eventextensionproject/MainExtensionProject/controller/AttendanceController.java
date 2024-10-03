package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.entity.Attendance;
import br.com.eventextensionproject.MainExtensionProject.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/attendance")
@CrossOrigin(value = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<Attendance> result = attendanceService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEventActivity/{idEventActivity}")
    public ResponseEntity<Object> getByEventActivity(@PathVariable Long idEventActivity) {
        List<Attendance> result = attendanceService.findByEventActivity(idEventActivity);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEnrollment/{idEnrollment}")
    public ResponseEntity<Object> getByEnrollment(@PathVariable Long idEnrollment) {
        List<Attendance> result = attendanceService.findByEnrollment(idEnrollment);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEventActivity/{idEventActivity}/Enrollment/{codeBar}")
    public ResponseEntity<Object> getByEventActivityAndEnrollment(@PathVariable Long idEventActivity, @PathVariable String codeBar) {
        Attendance result = attendanceService.findByEventActivityAndEnrollment(idEventActivity, codeBar);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/updateStatus/{idEventActivity}/Enrollment/{codeBar}")
    public ResponseEntity<Object> updateStatus(@PathVariable Long idEventActivity, @PathVariable String codeBar) {
        HashMap<String, Object> result = attendanceService.updateStatus(idEventActivity, codeBar);
        return ResponseEntity.ok().body(result);
    }

}
