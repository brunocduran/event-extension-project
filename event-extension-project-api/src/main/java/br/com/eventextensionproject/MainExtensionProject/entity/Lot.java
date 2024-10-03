package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.dto.LotDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lot")
    private Long idLot;

    @Column(name = "name_lot", nullable = false, length = 300)
    private String nameLot;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    @Column(name = "start_date_lot", nullable = false)
    private LocalDate startDateLot;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    @Column(name = "end_date_lot", nullable = false)
    private LocalDate endDateLot;

    @Column(name = "situation", nullable = false)
    private Situation situation;

    @Column(name = "amount_lot", nullable = false)
    private BigDecimal amountLot;

    @Column(name = "quantity_tickets_lot", nullable = false)
    private Integer quantityTicketsLot;

    @Column(name = "quantity_registrations_lot", nullable = false)
    private Integer quantityRegistrationsLot;

    @JsonIgnore
    @ManyToOne
    private Event event;

    @JsonIgnore
    public boolean isValidName() {
        return this.getNameLot() != null && !this.getNameLot().isBlank();
    }

    @PrePersist
    private void prePersist() {
        //this.setSituation(Situation.INATIVO);
        this.setQuantityRegistrationsLot(0);
    }

    public Lot(LotDTO obj){
        this.idLot = obj.getIdLot();
        this.nameLot = obj.getNameLot();
        this.startDateLot = obj.getStartDateLot();
        this.endDateLot = obj.getEndDateLot();
        this.situation = obj.getSituation();
        this.amountLot = obj.getAmountLot();
        this.quantityTicketsLot = obj.getQuantityTicketsLot();
    }

}
