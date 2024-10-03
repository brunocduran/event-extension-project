package br.com.eventextensionproject.MainExtensionProject.dto;

import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDTO {

    private Long idIntitution;
    private String nameInstitution;
    private String cnpjInstitution;
    private MultipartFile imageInstitution;
    private Situation situation;
}
