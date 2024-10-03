package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import br.com.eventextensionproject.MainExtensionProject.repository.PersonRepository;
import br.com.eventextensionproject.MainExtensionProject.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private PersonRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = repository.findByEmailPerson(username);
        return new User(user.get().getEmailPerson(), user.get().getPasswordPerson(), user.get().getRolePermission());
    }
}