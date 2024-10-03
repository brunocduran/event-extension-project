package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.dto.PersonDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.RolePermission;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.TypePerson;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Long idPerson;

    @Column(name = "name_person", nullable = false)
    @NotBlank(message = "O campo nome é obrigatório!")
    @Length(min = 2, max = 300, message = "O nome deve ter ao menos dois caracteres!")
    private String namePerson;

    @Column(name = "rg_ie_person", nullable = false)
    private String rgIePerson;

    @Column(name = "cpf_cnpj_person", nullable = false)
    private String cpfCnpjPerson;

    @Column(name = "type_person", nullable = false)
    private TypePerson type;

    @Column(name = "situation_person", nullable = false)
    private Situation situationPerson;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    @Column(name = "date_born_foundation", nullable = false)
    private LocalDate dateBornFoundation;

    @Column(name = "phone_person", nullable = false)
    private String phonePerson;

    @Column(name = "address_person", nullable = false)
    private String addressPerson;

    @Column(name = "number_address_person", nullable = false)
    private String numberAddressPerson;

    @Column(name = "neighborhood_person", nullable = false)
    private String neighborhoodPerson;

    @Column(name = "zip_code_person", nullable = false)
    private String zipCodePerson;

    @Column(name = "email_person", nullable = false)
    @Email(message = "O Email informado é inválido!")
    private String emailPerson;

    @Column(name = "accept_terms", nullable = false)
    private Boolean acceptTerms;

    @JoinColumn(name = "id_city", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private City cityPerson;

    @Column(name = "password_person", nullable = false)
    private String passwordPerson;

    @JsonIgnore
    public boolean isValidNamePerson() {
        return this.getNamePerson() != null && !this.getNamePerson().isBlank();
    }

    @JsonIgnore
    public boolean isValidEmailPerson() {
        return this.getEmailPerson() != null && !this.getEmailPerson().isBlank();
    }

    @JsonIgnore
    public boolean isValidCpfCnpjPerson() {
        return this.getCpfCnpjPerson() != null && !this.getCpfCnpjPerson().isBlank();
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles_person")
    protected Set<Integer> rolePermission = new HashSet<>();

    public Set<RolePermission> getRolePermission() {
        return rolePermission.stream().map(x -> RolePermission.toEnum(x)).collect(Collectors.toSet());
    }

    public void addRolePermission(RolePermission rolePermission) {
        this.rolePermission.add(rolePermission.getCodigo());}

    @PrePersist
    private void prePersist() {
        this.setSituationPerson(Situation.ATIVO);
    }

    public Person(PersonDTO obj){
        this.idPerson = obj.getIdPerson();
        this.namePerson = obj.getNamePerson();
        this.rgIePerson = obj.getRgIePerson();
        this.cpfCnpjPerson = obj.getCpfCnpjPerson();
        this.type = obj.getType();
        this.dateBornFoundation = obj.getDateBornFoundation();
        this.phonePerson = obj.getPhonePerson();
        this.addressPerson = obj.getAddressPerson();
        this.numberAddressPerson = obj.getNumberAddressPerson();
        this.neighborhoodPerson = obj.getNeighborhoodPerson();
        this.zipCodePerson = obj.getZipCodePerson();
        this.emailPerson = obj.getEmailPerson();
        this.passwordPerson = obj.getPasswordPerson();
        this.acceptTerms = obj.getAcceptTerms();
        this.rolePermission = obj.getRolePermission();
    }
}
