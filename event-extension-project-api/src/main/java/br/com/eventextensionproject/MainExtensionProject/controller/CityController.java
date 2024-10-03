package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.entity.City;
import br.com.eventextensionproject.MainExtensionProject.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/city")
@CrossOrigin(value = "*")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<City> result = cityService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByState/{acronym}")
    public ResponseEntity<Object> getByState(@PathVariable String acronym) {
        List<City> result = cityService.findByState(acronym);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/findByNameCity/{nameCity}")
    public ResponseEntity<Object> findByNameCity(@PathVariable String nameCity) {
        City result = cityService.findByNameCity(nameCity);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/findByCodIbge/{codIbge}")
    public ResponseEntity<Object> findByCodIbge(@PathVariable Integer codIbge) {
        City result = cityService.findByCodIbge(codIbge);
        return ResponseEntity.ok().body(result);
    }
}
