package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.entity.State;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    @Autowired
    private StateRepository repository;

    public List<State> getAll() {
        return repository.findByOrderByNameStateAsc();
    }

    public State findById(Long idState) {
        Optional<State> obj = repository.findById(idState);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Estado não encontrada!"));
    }

    public State findByAcronym(String acronym) {
        Optional<State> obj = repository.findByStateAcronym(acronym);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Estado não encontrada!"));
    }

}
