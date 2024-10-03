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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_course")
    private Long idCourse;

    @Column(name = "name_course", nullable = false, length = 300)
    private String nameCourse;

    @Column(name = "situation", nullable = false)
    private Situation situation;

    @JsonIgnore
    public boolean isValidName() {
        return this.getNameCourse() != null && !this.getNameCourse().isBlank();
    }

    @PrePersist
    private void prePersist() {
        this.setSituation(Situation.ATIVO);
    }
}
