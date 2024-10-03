package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    List<State> findByOrderByNameStateAsc();
    Optional<State> findByStateAcronym(String acronym);
}
