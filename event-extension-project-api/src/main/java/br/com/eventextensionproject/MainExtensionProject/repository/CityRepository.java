package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.City;
import br.com.eventextensionproject.MainExtensionProject.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByOrderByNameCityAsc();
    List<City> findByStateOrderByNameCityAsc(State state);
    Optional<City> findByNameCity(String nameCity);
    Optional<City> findByCodIbge(Integer codIbge);
}
