package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    boolean existsByCnpjInstitution(String cnpj);
    Institution findByCnpjInstitution(String cnpj);
    List<Institution> findByOrderByNameInstitutionAsc();
}
