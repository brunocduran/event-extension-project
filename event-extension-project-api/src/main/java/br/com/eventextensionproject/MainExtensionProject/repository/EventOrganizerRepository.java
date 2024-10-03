package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, Long> {
    List<EventOrganizer> findByEvent(Event event);
}
