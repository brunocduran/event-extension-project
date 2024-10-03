package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.dto.PersonDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.TypePerson;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.PersonRepository;
import br.com.eventextensionproject.MainExtensionProject.security.JWTService;
import br.com.eventextensionproject.MainExtensionProject.utils.ValidationCpfCnpj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;
    @Autowired
    private CityService cityService;
    @Autowired
    JWTService tokenService;
    @Autowired
    AuthorizationService authorizationService;

    public List<Person> getInfoPerson() {
        return repository.findByOrderByNamePersonAsc();
    }

    public List<Person> getPersonByPermission(Integer rolePermission) {
        return repository.findByRolePermission(rolePermission);
    }

    public Person savePerson(PersonDTO personDTO) {
        Person person = new Person(personDTO);
        person.setCityPerson(cityService.findById(personDTO.getIdCity()));

        person.setCpfCnpjPerson(ValidationCpfCnpj.limpar(person.getCpfCnpjPerson()));

        if (validatePerson(person) && verificarCpfCnpj(person.getCpfCnpjPerson(), person.getEmailPerson(), person)) {
            encryptPassword(person);
            return repository.saveAndFlush(person);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public Person savePersonParticipant(PersonDTO personDTO) {
        Person person = new Person(personDTO);
        person.setCityPerson(cityService.findById(personDTO.getIdCity()));
        person.setRolePermission(new HashSet<>(Arrays.asList(4)));

        person.setCpfCnpjPerson(ValidationCpfCnpj.limpar(person.getCpfCnpjPerson()));

        if (validatePerson(person) && verificarCpfCnpj(person.getCpfCnpjPerson(), person.getEmailPerson(), person)) {
            encryptPassword(person);
            return repository.saveAndFlush(person);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public HashMap<String, Object> deletePerson(Long idPerson) {
        String status = "";

        Optional<Person> person =
                Optional.ofNullable(repository.findById(idPerson).
                        orElseThrow(() -> new ObjectnotFoundException("Pessoa não encontrado!")));

        //repository.delete(person.get());
        if (person.get().getSituationPerson() == Situation.ATIVO) {
            person.get().setSituationPerson(Situation.INATIVO);
            status = "inativada";
        } else {
            person.get().setSituationPerson(Situation.ATIVO);
            status = "ativada";
        }

        repository.saveAndFlush(person.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Pessoa " + person.get().getNamePerson() + " " + status + " com sucesso!");
        return result;
    }

    public Optional<Person> findPersonById(Long idPerson) {
        if (idPerson != null) {
            return Optional.ofNullable(repository.findById(idPerson)
                    .orElseThrow(() -> new ObjectnotFoundException("Pessoa não encontrada!")));
        } else {
            throw new ObjectnotFoundException("ID da Pessoa não pode ser nulo!");
        }
    }

    public Person findPersonByToken(String token) {
        var login = tokenService.validateToken(token.substring(7));
        UserDetails user = authorizationService.loadUserByUsername(login);
        Optional<Person> obj = repository.findByEmailPerson(user.getUsername());
        return obj.orElseThrow(() -> new ObjectnotFoundException("Pessoa não encontrada!"));
    }

    public Person findById(Long idPerson) {
        Optional<Person> obj = repository.findById(idPerson);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Pessoa não encontrada!"));
    }

    public Boolean findPersonByCpfCnpj(String cpfCnpj) {
        return repository.existsByCpfCnpjPerson(cpfCnpj);
    }

    public Boolean findPersonByEmail(String email) {
        return repository.existsByEmailPerson(email);
    }


    public Person updatePerson(PersonDTO personDTO) {
        Person person = new Person(personDTO);
        person.setCityPerson(cityService.findById(personDTO.getIdCity()));
        person.setSituationPerson(Situation.ATIVO);

        person.setCpfCnpjPerson(ValidationCpfCnpj.limpar(person.getCpfCnpjPerson()));
        if (validatePerson(person) && verificarCpfCnpj(person.getCpfCnpjPerson(), person.getEmailPerson(), person)) {
            if (findPersonById(person.getIdPerson()) != null) {
                encryptPassword(person);
                return repository.saveAndFlush(person);
            } else {
                return null;
            }
        } else {
            throw new ObjectnotFoundException("Nenhum campo pode ser vazio!");
        }
    }

    public Person updatePersonParticipant(PersonDTO personDTO) {
        Person person = new Person(personDTO);
        person.setCityPerson(cityService.findById(personDTO.getIdCity()));
        person.setSituationPerson(Situation.ATIVO);

        Person oldPerson = findById(person.getIdPerson());
        person.setRolePermission(oldPerson.getRolePermission().stream().map(x -> x.getCodigo()).collect(Collectors.toSet()));

        person.setCpfCnpjPerson(ValidationCpfCnpj.limpar(person.getCpfCnpjPerson()));
        if (validatePerson(person) && verificarCpfCnpj(person.getCpfCnpjPerson(), person.getEmailPerson(), person)) {
            if (findPersonById(person.getIdPerson()) != null) {
                encryptPassword(person);
                return repository.saveAndFlush(person);
            } else {
                return null;
            }
        } else {
            throw new ObjectnotFoundException("Nenhum campo pode ser vazio!");
        }
    }

    private boolean validatePerson(Person person) {
        if (person.isValidNamePerson() && person.isValidEmailPerson()
                && person.isValidCpfCnpjPerson()) {
            return true;
        }
        return false;
    }

    private boolean verificarCpfCnpj(String cpfCnpj, String email, Person person) {
        if (!ValidationCpfCnpj.isValidCpfCnpj(cpfCnpj)) {
            throw new DataIntegrityViolationException("CPF ou CNPJ inválido");
        }
        if (ValidationCpfCnpj.isCNPJ(cpfCnpj) &&
                person.getType().equals(TypePerson.FISICO)) {
            throw new DataIntegrityViolationException("Tipo de pessoa incorreto(CNPJ)");
        }
        if (ValidationCpfCnpj.isCPF(cpfCnpj) &&
                person.getType().equals(TypePerson.JURIDICO)) {
            throw new DataIntegrityViolationException("Tipo de pessoa incorreto(CPF)");
        }
        if (repository.existsByCpfCnpjPerson(cpfCnpj)) {
            if(repository.findByCpfCnpjPerson(cpfCnpj).getIdPerson() != person.getIdPerson()){
                throw new DataIntegrityViolationException("CPF ou CNPJ já cadastrado");
            }
        }
        if (repository.existsByEmailPerson(email)) {
            if(repository.findByEmailPerson(email).get().getIdPerson() != person.getIdPerson()){
                throw new DataIntegrityViolationException("E-mail já cadastrado");
            }
        }
        return true;
    }

    public void encryptPassword(Person person) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = null;
        if (person.getIdPerson() == null) {
            encryptedPassword = encoder.encode(person.getPasswordPerson());
            person.setPasswordPerson(encryptedPassword);
        } else {
            if (!repository.findById(person.getIdPerson()).get().getPasswordPerson()
                    .equals(person.getPasswordPerson())) {
                encryptedPassword = encoder.encode(person.getPasswordPerson());
                person.setPasswordPerson(encryptedPassword);
            }
        }
    }

}



