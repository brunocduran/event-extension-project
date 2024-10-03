package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.entity.State;
import br.com.eventextensionproject.MainExtensionProject.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/state")
@CrossOrigin(value = "*")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<State> result = stateService.getAll();
        return ResponseEntity.ok().body(result);
    }
}
