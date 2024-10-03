package br.com.eventextensionproject.MainExtensionProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventOrganizerDTO {
    private Long idEventOrganizer;
    private Long idEvent;
    private Long idPerson;
    private Long idFunction;
}
