package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Person;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.StatusEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrderByEventStartDate();
    List<Event> findByStatusEventOrderByEventStartDate(StatusEvent statusEvent);
    @Query("SELECT e FROM Event e, EventOrganizer eo WHERE eo.event = e AND eo.person = ?1")
    List<Event> findEventByOrganizer(Person person);
}
