package br.com.eventextensionproject.MainExtensionProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {
    private Long idPerson;
    private Long idEvent;
    private Long idLot;
}
