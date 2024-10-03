package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.dto.DonationDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Donation;
import br.com.eventextensionproject.MainExtensionProject.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/donation")
@CrossOrigin(value = "*")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<Donation> result = donationService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/findTotalDonation")
    public ResponseEntity<Object> findTotalDonation() {
        HashMap<String, Object> result = donationService.findTotalDonation();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idDonation}")
    public ResponseEntity<Object> findById(@PathVariable Long idDonation) {
        Donation result = donationService.findById(idDonation);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEvent/{idEvent}")
    public ResponseEntity<Object> getByState(@PathVariable Long idEvent) {
        List<Donation> result = donationService.findByEvent(idEvent);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody DonationDTO donationDTO) throws IOException {
        Donation result = donationService.save(donationDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idDonation}")
    public ResponseEntity<Object> delete(@PathVariable Long idDonation) {
        HashMap<String, Object> result = donationService.delete(idDonation);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody DonationDTO donationDTO) throws IOException {
        Donation result = donationService.update(donationDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/updateStatus/{idDonation}")
    public ResponseEntity<Object> updateStatus(@PathVariable Long idDonation) {
        HashMap<String, Object> result = donationService.updateStatus(idDonation);
        return ResponseEntity.ok().body(result);
    }
}
