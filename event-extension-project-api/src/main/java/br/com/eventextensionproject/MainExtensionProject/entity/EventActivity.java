package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.dto.EventActivityDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "event_activity")
@AllArgsConstructor
@NoArgsConstructor
public class EventActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event_activity")
    private Long idEventActivity;

    @Column(name = "hourly_load", nullable = false)
    private BigDecimal hourlyLoad;

    @Column(name = "activity_name", nullable = false)
    private String activityName;

    @Column(name = "summary", nullable = false)
    private String summary;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    @Column(name = "activity_date", nullable = false)
    private LocalDate activityDate;

    @Column(name = "time_activity", nullable = false)
    @Length(min = 5, max = 5)
    private String timeActivity;

    @Column(name = "quantity_registrations_event_activity", nullable = false)
    private Integer quantityRegistrationsEventActivity;

    @Column(name = "address_event_activity", nullable = false)
    private String addressEventActivity;

    @Column(name = "number_address_event_activity", nullable = false)
    private String numberAddressEventActivity;

    @Column(name = "neighborhood_event_activity", nullable = false)
    private String neighborhoodEventActivity;

    @Column(name = "zip_code_event_activity", nullable = false)
    private String zipCodeEventActivity;

    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    @ManyToOne
    private ActivityType activityType;

    @JsonIgnore
    @ManyToOne
    private Event event;

    public EventActivity(EventActivityDTO obj){
        this.idEventActivity = obj.getIdEventActivity();
        this.hourlyLoad = obj.getHourlyLoad();
        this.activityName = obj.getActivityName();
        this.summary = obj.getSummary();
        this.activityDate = obj.getActivityDate();
        this.timeActivity = obj.getTimeActivity();
        this.addressEventActivity = obj.getAddressEventActivity();
        this.numberAddressEventActivity = obj.getNumberAddressEventActivity();
        this.neighborhoodEventActivity = obj.getNeighborhoodEventActivity();
        this.zipCodeEventActivity = obj.getZipCodeEventActivity();
    }

    @PrePersist
    private void prePersist() {
        this.setQuantityRegistrationsEventActivity(0);
    }

    @JsonIgnore
    public boolean isValidHourlyLoad(){
        return this.getHourlyLoad() != null && this.getHourlyLoad().compareTo(BigDecimal.ZERO) != 0;
    }

    @JsonIgnore
    public boolean isValidActivityName(){
        return this.getActivityName() != null && !this.getActivityName().isBlank();
    }

    @JsonIgnore
    public boolean isValidSummary(){
        return this.getSummary() != null && !this.getSummary().isBlank();
    }

    @JsonIgnore
    public boolean isValidTimeActivity(){
        return this.getTimeActivity() != null && !this.getTimeActivity().isBlank();
    }

    @JsonIgnore
    public boolean isValidAddressEventActivity(){
        return this.getAddressEventActivity() != null && !this.getAddressEventActivity().isBlank();
    }

    @JsonIgnore
    public boolean isValidNumberAddressEventActivity(){
        return this.getNumberAddressEventActivity() != null && !this.getNumberAddressEventActivity().isBlank();
    }

    @JsonIgnore
    public boolean isValidNeighborhoodEventActivity(){
        return this.getNeighborhoodEventActivity() != null && !this.getNeighborhoodEventActivity().isBlank();
    }

    @JsonIgnore
    public boolean isValidZipCodeEventActivity(){
        return this.getZipCodeEventActivity() != null && !this.getZipCodeEventActivity().isBlank();
    }

}
