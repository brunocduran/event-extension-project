package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.entity.City;
import br.com.eventextensionproject.MainExtensionProject.entity.State;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;
    @Autowired
    private StateService stateService;

    public List<City> getAll() {
        return repository.findByOrderByNameCityAsc();
    }

    public City findById(Long idCity) {
        Optional<City> obj = repository.findById(idCity);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Cidade não encontrada!"));
    }

    public List<City> findByState(String acronym) {
        State state = stateService.findByAcronym(acronym);
        return repository.findByStateOrderByNameCityAsc(state);
    }

    public City findByNameCity(String nameCity) {
        Optional<City> obj = repository.findByNameCity(nameCity);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Cidade não encontrada!"));
    }

    public City findByCodIbge(Integer codIbge) {
        Optional<City> obj = repository.findByCodIbge(codIbge);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Cidade não encontrada!"));
    }

}
