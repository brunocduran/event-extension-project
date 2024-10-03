package br.com.eventextensionproject.MainExtensionProject.dto;

import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LotDTO {
    private Long idLot;
    private String nameLot;
    private LocalDate startDateLot;
    private LocalDate endDateLot;
    private Situation situation;
    private BigDecimal amountLot;
    private Integer quantityTicketsLot;
    private Long idEvent;
}
