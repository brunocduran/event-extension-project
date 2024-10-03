package br.com.eventextensionproject.MainExtensionProject.controller;

import java.util.HashMap;
import java.util.List;
import br.com.eventextensionproject.MainExtensionProject.dto.LotDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Lot;
import br.com.eventextensionproject.MainExtensionProject.service.LotService;
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

@RestController
@RequestMapping(value = "/api/v1/lot")
@CrossOrigin(value = "*")
public class LotController {

    @Autowired
    private LotService lotService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<Lot> result = lotService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idLot}")
    public ResponseEntity<Object> findById(@PathVariable Long idLot) {
        Lot result = lotService.findById(idLot);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEvent/{idEvent}")
    public ResponseEntity<Object> getByState(@PathVariable Long idEvent) {
        List<Lot> result = lotService.findByEvent(idEvent);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody LotDTO lotDTO) {
        Lot result = lotService.save(lotDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idLot}")
    public ResponseEntity<Object> delete(@PathVariable Long idLot) {
        HashMap<String, Object> result = lotService.delete(idLot);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody LotDTO lotDTO) {
        Lot result = lotService.update(lotDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/updateStatus/{idLot}")
    public ResponseEntity<Object> updateStatus(@PathVariable Long idLot) {
        HashMap<String, Object> result = lotService.updateStatus(idLot);
        return ResponseEntity.ok().body(result);
    }
}
