package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.eventextensionproject.MainExtensionProject.entity.Lot;

import java.time.LocalDate;
import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findByOrderByStartDateLot();
    List<Lot> findByEventOrderByStartDateLot(Event event);
    Lot findByEventAndSituation(Event event, Situation situation);
    Lot findFirst1ByEventAndEndDateLotGreaterThanOrderByStartDateLotAsc(Event event, LocalDate initialDate);
}
