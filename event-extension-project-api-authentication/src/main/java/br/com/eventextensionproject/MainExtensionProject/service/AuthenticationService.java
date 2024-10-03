package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import br.com.eventextensionproject.MainExtensionProject.entity.enumarator.Situation;
import br.com.eventextensionproject.MainExtensionProject.entity.record.UserLogin;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private PersonRepository repository;

    public void verifyUser(UserLogin data){
        Optional<Person> user = repository.findByEmailPerson(data.email());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!user.isPresent()){
            throw new ObjectnotFoundException("E-mail e/ou senha inválidos!");
        }else if(!encoder.matches(data.password(), user.get().getPasswordPerson())){
            throw new ObjectnotFoundException("E-mail e/ou senha inválidos!");
        }else if(user.get().getSituationPerson() == Situation.INATIVO){
            throw new DataIntegrityViolationException("Usuário inativo!");
        }
    }
}
