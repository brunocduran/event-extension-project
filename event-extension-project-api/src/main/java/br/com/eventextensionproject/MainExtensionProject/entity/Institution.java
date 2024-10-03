package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.dto.InstitutionDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_institution")
    private Long idIntitution;

    @Column(name = "name_institution", nullable = false)
    private String nameInstitution;

    @Column(name = "cnpj_institution", nullable = false)
    private String cnpjInstitution;

    @Column(name = "name_image")
    private String nameImage;

    @Column(name = "type_image")
    private String typeImage;

    @Column(name = "situation", nullable = false)
    private Situation situation;

    @JsonIgnore
    public boolean isValidNameInstitution() {
        return this.getNameInstitution() != null && !this.getNameInstitution().isBlank();
    }

    @PrePersist
    private void prePersist() {
        this.setSituation(Situation.ATIVO);
    }

    public Institution(InstitutionDTO obj) throws IOException {
        this.idIntitution = obj.getIdIntitution();
        this.nameInstitution = obj.getNameInstitution();
        this.cnpjInstitution = obj.getCnpjInstitution();
        if(!obj.getImageInstitution().isEmpty()){
            this.typeImage = obj.getImageInstitution().getContentType();
            this.nameImage = StringUtils.cleanPath(obj.getImageInstitution().getOriginalFilename());
        }
        this.situation = obj.getSituation();
    }
}
