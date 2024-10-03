package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "eventCategory")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event_category")
    private Long idEventCategory;

    @Column(name = "name_event_category", nullable = false, length = 300)
    private String nameEventCategory;

    @Column(name = "situation", nullable = false)
    private Situation situation;

    @JsonIgnore
    public boolean isValidName() {
        return this.getNameEventCategory() != null && !this.getNameEventCategory().isBlank();
    }

    @PrePersist
    private void prePersist() {
        this.setSituation(Situation.ATIVO);
    }
}
