package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.dto.DonationDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donation")
    private Long idDonation;

    @Column(name = "description_donation", nullable = false)
    private String descriptionDonation;

    @Column(name = "donation_amount", nullable = false)
    private BigDecimal donationAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    @Column(name = "donation_date", nullable = false)
    private LocalDate donationDate;

    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Person person;

    @PrePersist
    private void prePersist() {
        this.setAccountStatus(AccountStatus.ABERTO);
    }

    public Donation(DonationDTO obj) throws IOException{
        this.idDonation = obj.getIdDonation();
        this.descriptionDonation = obj.getDescriptionDonation();
        this.donationAmount = obj.getDonationAmount();
        this.donationDate = obj.getDonationDate();
        this.accountStatus = obj.getAccountStatus();
    }

    @JsonIgnore
    public boolean isValidDescriptionDonation(){
        return this.getDescriptionDonation() != null && !this.getDescriptionDonation().isBlank();
    }

    @JsonIgnore
    public boolean isValidDonationAmount(){
        return this.getDonationAmount() != null && this.getDonationAmount().compareTo(BigDecimal.ZERO) != 0;
    }
}
