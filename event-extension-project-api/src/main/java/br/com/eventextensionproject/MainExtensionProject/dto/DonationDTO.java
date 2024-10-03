package br.com.eventextensionproject.MainExtensionProject.dto;

import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationDTO {
    private Long idDonation;
    private String descriptionDonation;
    private BigDecimal donationAmount;
    private LocalDate donationDate;
    private AccountStatus accountStatus;
    private Long idEvent;
    private Long idPerson;
}
