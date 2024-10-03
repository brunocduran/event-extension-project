package br.com.eventextensionproject.MainExtensionProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
import br.com.eventextensionproject.MainExtensionProject.entity.Function;
import br.com.eventextensionproject.MainExtensionProject.service.FunctionService;

@RestController
@RequestMapping(value = "/api/v1/function")
@CrossOrigin(value = "*")
public class FunctionController {
    
    @Autowired
    private FunctionService service;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<Function> result = service.getAll();
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody Function function) {
        Function result = service.save(function);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idFunction}")
    public ResponseEntity<Object> delete(@PathVariable Long idFunction) {
        HashMap<String, Object> result = service.delete(idFunction);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idFunction}")
    public ResponseEntity<Object> findById(@PathVariable Long idFunction) {
        Optional<Function> result = service.findById(idFunction);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody Function function) {
        Function result = service.update(function);
        return ResponseEntity.ok().body(result);
    }
}
