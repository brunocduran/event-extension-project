package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.dto.EnrollmentDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentFinancialStatus;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import java.io.IOException;
import java.util.List;

@Entity
@Table(name = "enrollment")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_enrollment")
    private Long idEnrollment;

    @Column(name = "bar_code")
    @Length(min = 13, max = 13)
    private String barCode;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER)
    private Event event;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<EventActivity> eventActivities;

    @ManyToOne(fetch = FetchType.EAGER)
    private Lot lot;

    @Column(name = "enrollment_status", nullable = false)
    private EnrollmentStatus enrollmentStatus;

    @Column(name = "enrollment_financial_status", nullable = false)
    private EnrollmentFinancialStatus enrollmentFinancialStatus;

    @PrePersist
    private void prePersist() {
        this.setEnrollmentStatus(EnrollmentStatus.ATIVO);
        this.setEnrollmentFinancialStatus(EnrollmentFinancialStatus.ABERTO);
    }
}
