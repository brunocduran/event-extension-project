package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.entity.enumarator.RolePermission;
import br.com.eventextensionproject.MainExtensionProject.entity.enumarator.Situation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Long idPerson;

    @Column(name = "name_person", nullable = false)
    private String namePerson;

    @Column(name = "email_person", nullable = false)
    private String emailPerson;

    @Column(name = "password_person", nullable = false)
    private String passwordPerson;

    @Column(name = "situation_person", nullable = false)
    private Situation situationPerson;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles_person")
    protected Set<Integer> rolePermission = new HashSet<>();

    public Set<RolePermission> getRolePermission() {
        return rolePermission.stream().map(x -> RolePermission.toEnum(x)).collect(Collectors.toSet());
    }
}
