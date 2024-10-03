package br.com.eventextensionproject.MainExtensionProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventActivityDTO {
    private Long idEventActivity;
    private BigDecimal hourlyLoad;
    private String activityName;
    private String summary;
    private LocalDate activityDate;
    private String timeActivity;
    private String addressEventActivity;
    private String numberAddressEventActivity;
    private String neighborhoodEventActivity;
    private String zipCodeEventActivity;
    private Long idCity;
    private Long idActivityType;
    private Long idEvent;
}
