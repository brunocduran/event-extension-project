package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmailPerson(String email);
}
