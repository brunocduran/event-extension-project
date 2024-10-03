package br.com.eventextensionproject.MainExtensionProject.dto;

import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerDTO {
    private Long idBanner;
    private String nameBanner;
    private String messageBanner;
    private LocalDate initialDateBanner;
    private LocalDate finalDateBanner;
    private MultipartFile imageBanner;
    private Long idEvent;
    private Situation situation;
}
