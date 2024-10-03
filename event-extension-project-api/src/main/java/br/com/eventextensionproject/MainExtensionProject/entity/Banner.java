package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.dto.BannerDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banner")
    private Long idBanner;

    @Column(name = "name_banner", nullable = false)
    private String nameBanner;

    @Column(name = "message_banner", nullable = false)
    private String messageBanner;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    @Column(name = "initial_date_banner", nullable = false)
    private LocalDate initialDateBanner;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    @Column(name = "final_date_banner", nullable = false)
    private LocalDate finalDateBanner;

    @Column(name = "name_image")
    private String nameImage;

    @Column(name = "type_image")
    private String typeImage;

    @ManyToOne
    private Event event;

    @Column(name = "situation", nullable = false)
    private Situation situation;

    @JsonIgnore
    public boolean isValidName() {
        return this.getNameBanner() != null && !this.getNameBanner().isBlank();
    }

    @PrePersist
    private void prePersist() {
        this.setSituation(Situation.ATIVO);
    }

    public Banner(BannerDTO obj) throws IOException{
        this.idBanner = obj.getIdBanner();
        this.nameBanner = obj.getNameBanner();
        this.messageBanner = obj.getMessageBanner();
        this.initialDateBanner = obj.getInitialDateBanner();
        this.finalDateBanner = obj.getFinalDateBanner();
        if(!obj.getImageBanner().isEmpty()){
            this.typeImage = obj.getImageBanner().getContentType();
            this.nameImage = StringUtils.cleanPath(obj.getImageBanner().getOriginalFilename());
        }
        this.situation = obj.getSituation();
    }
}
