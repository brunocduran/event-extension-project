package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.dto.PersonDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import br.com.eventextensionproject.MainExtensionProject.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/person")
@CrossOrigin(value = "*")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping(value = "/list")
    @Operation(summary = "List all the persons")
    public ResponseEntity<Object> getInfoPerson() {
        List<Person> result = service.getInfoPerson();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listAdministrator")
    public ResponseEntity<Object> getPersonAdministrator() {
        List<Person> result = service.getPersonByPermission(0);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listOrganizer")
    public ResponseEntity<Object> getPersonOrganizer() {
        List<Person> result = service.getPersonByPermission(1);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listSponsor")
    public ResponseEntity<Object> getPersonSponsor() {
        List<Person> result = service.getPersonByPermission(2);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listSupplier")
    public ResponseEntity<Object> getPersonSupplier() {
        List<Person> result = service.getPersonByPermission(3);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listParticipant")
    public ResponseEntity<Object> getPersonParticipant() {
        List<Person> result = service.getPersonByPermission(4);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    @Operation(summary = "Saves a new person")
    public ResponseEntity<Object> save(@RequestBody PersonDTO person) {
        Person result = service.savePerson(person);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insertParticipant")
    @Operation(summary = "Saves a new Participant")
    public ResponseEntity<Object> saveParticipant(@RequestBody PersonDTO person) {
        Person result = service.savePersonParticipant(person);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idPerson}")
    @Operation(summary = "Delete an existing person")
    public ResponseEntity<Object> delete(@PathVariable Long idPerson) {
        HashMap<String, Object> result = service.deletePerson(idPerson);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idPerson}")
    @Operation(summary = "Search for a person by ID")
    public ResponseEntity<Object> findById(@PathVariable Long idPerson) {
        Optional<Person> result = service.findPersonById(idPerson);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByCpfCnpj/{cpfCnpj}")
    public ResponseEntity<Object> findByCpfCnpj(@PathVariable String cpfCnpj) {
        Boolean result = service.findPersonByCpfCnpj(cpfCnpj);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEmail/{email}")
    public ResponseEntity<Object> findByEmail(@PathVariable String email) {
        Boolean result = service.findPersonByEmail(email);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    @Operation(summary = "Updates an existing person")
    public ResponseEntity<Object> update(@RequestBody PersonDTO person) {
        Person result = service.updatePerson(person);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/updateParticipant")
    @Operation(summary = "Updates an existing person")
    public ResponseEntity<Object> updateParticipant(@RequestBody PersonDTO person) {
        Person result = service.updatePersonParticipant(person);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/personToken")
    public ResponseEntity<Object> getPersonToken(@RequestHeader("Authorization") String token) {
        Person result = service.findPersonByToken(token);
        return ResponseEntity.ok().body(result);
    }
}
