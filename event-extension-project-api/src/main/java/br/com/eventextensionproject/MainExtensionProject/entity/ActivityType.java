package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_activity_type")
    private Long idActivityType;

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
