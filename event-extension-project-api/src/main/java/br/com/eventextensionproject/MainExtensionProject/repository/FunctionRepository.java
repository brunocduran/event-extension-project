package br.com.eventextensionproject.MainExtensionProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.eventextensionproject.MainExtensionProject.entity.Function;
import java.util.List;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Long>{
    List<Function> findByOrderByDescriptionAsc();
}
