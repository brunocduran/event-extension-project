package br.com.eventextensionproject.MainExtensionProject.entity.record;

import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;

public record InstitutionResponse(
        Long idIntitution,
        String nameInstitution,
        String cnpjInstitution,
        Situation situation,
        String nameImage,
        String urlImage,
        String typeImage) {
}
