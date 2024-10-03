package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import br.com.eventextensionproject.MainExtensionProject.entity.record.LoginResponse;
import br.com.eventextensionproject.MainExtensionProject.entity.record.UserLogin;
import br.com.eventextensionproject.MainExtensionProject.repository.PersonRepository;
import br.com.eventextensionproject.MainExtensionProject.security.JWTService;
import br.com.eventextensionproject.MainExtensionProject.security.User;
import br.com.eventextensionproject.MainExtensionProject.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService tokenService;
    @Autowired
    private PersonRepository repository;
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated UserLogin data){
        authenticationService.verifyUser(data);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        Optional<Person> user = repository.findByEmailPerson(data.email());

        return ResponseEntity.ok().body(new LoginResponse(token, user.get().getNamePerson(), user.get().getRolePermission()));
    }
}
