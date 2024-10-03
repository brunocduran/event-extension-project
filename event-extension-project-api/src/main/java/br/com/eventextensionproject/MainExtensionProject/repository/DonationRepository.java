package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Donation;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByEventOrderByDonationDateAsc(Event event);
    List<Donation> findByOrderByDonationDateAsc();
    @Query("select sum(d.donationAmount) from Donation d where d.accountStatus = ?1")
    BigDecimal findTotalDonation(AccountStatus accountStatus);
}
