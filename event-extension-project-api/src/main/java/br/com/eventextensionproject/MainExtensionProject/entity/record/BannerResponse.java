package br.com.eventextensionproject.MainExtensionProject.entity.record;

import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import java.time.LocalDate;

public record BannerResponse(
        Long idBanner,
        String nameBanner,
        String messageBanner,
        LocalDate initialDateBanner,
        LocalDate finalDateBanner,
        String nameImage,
        String urlImage,
        String typeImage,
        Event event,
        Situation situation) {

}
