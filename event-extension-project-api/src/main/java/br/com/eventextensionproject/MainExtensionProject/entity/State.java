package br.com.eventextensionproject.MainExtensionProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_state")
    private Long idState;

    @Column(name = "name_state", nullable = false, length = 50)
    private String nameState;

    @Column(name = "state_acronym", nullable = false, length = 2)
    private String stateAcronym;

}
