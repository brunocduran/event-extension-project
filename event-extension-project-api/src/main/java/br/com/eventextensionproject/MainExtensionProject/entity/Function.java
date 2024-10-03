package br.com.eventextensionproject.MainExtensionProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "function")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Function {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_function")
    private Long idFunction;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "situation", nullable = false)
    private Situation situation;

    @JsonIgnore
    public boolean isValidDescription() {
        return this.getDescription() != null && !this.getDescription().isBlank();
    }

    @PrePersist
    private void prePersist() {
        this.setSituation(Situation.ATIVO);
    }
}
