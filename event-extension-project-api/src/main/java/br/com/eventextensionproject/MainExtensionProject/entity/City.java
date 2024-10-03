package br.com.eventextensionproject.MainExtensionProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "city")
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_city")
    private Long idCity;

    @Column(name = "name_city", nullable = false, length = 50)
    private String nameCity;

    @Column(name = "cod_ibge")
    private Integer codIbge;

    @ManyToOne
    @JoinColumn(name = "id_state")
    private State state;

}
