package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Banner;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findByOrderByNameBannerAsc();
    List<Banner> findBySituationAndInitialDateBannerLessThanEqualAndFinalDateBannerGreaterThanEqualOrderByNameBannerAsc(Situation situation, LocalDate initialDate, LocalDate finalDate);
}
