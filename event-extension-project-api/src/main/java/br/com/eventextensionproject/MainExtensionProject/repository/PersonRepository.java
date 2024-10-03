package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByCpfCnpjPerson(String cpfCnpj);
    Person findByCpfCnpjPerson(String cpfCnpj);
    boolean existsByEmailPerson(String email);
    Optional<Person> findByEmailPerson(String email);
    List<Person> findByOrderByNamePersonAsc();
    List<Person> findByRolePermission(Integer rolePermission);
}
