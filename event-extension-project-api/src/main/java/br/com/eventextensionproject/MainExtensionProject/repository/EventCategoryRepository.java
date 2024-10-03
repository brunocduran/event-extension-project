package br.com.eventextensionproject.MainExtensionProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.eventextensionproject.MainExtensionProject.entity.EventCategory;
import java.util.List;

@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory, Long>{
    List<EventCategory> findByOrderByNameEventCategoryAsc();
}