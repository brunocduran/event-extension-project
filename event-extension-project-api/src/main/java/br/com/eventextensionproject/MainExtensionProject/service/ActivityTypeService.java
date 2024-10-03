package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.entity.ActivityType;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.ActivityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityTypeService {

    @Autowired
    private ActivityTypeRepository repository;

    public List<ActivityType> getAll() {
        return repository.findByOrderByDescriptionAsc();
    }

    public ActivityType save(ActivityType activityType) {
        if(validateActivityType(activityType)) {
            return repository.saveAndFlush(activityType);
        } else {
            throw new DataIntegrityViolationException("Campo Descrição não pode ser vazio!");
        }
    }

    public HashMap<String, Object> delete(Long idActivityType) {
        String status = "";

        Optional<ActivityType> activityType =
                Optional.ofNullable(repository.findById(idActivityType).
                        orElseThrow(() -> new ObjectnotFoundException("Tipo de Atividade não encontrada!")));

        //repository.delete(activityType.get());
        if (activityType.get().getSituation() == Situation.ATIVO) {
            activityType.get().setSituation(Situation.INATIVO);
            status = "inativada";
        } else {
            activityType.get().setSituation(Situation.ATIVO);
            status = "ativada";
        }

        repository.saveAndFlush(activityType.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Tipo de Atividade " + activityType.get().getDescription() + " " + status + " com sucesso!");
        return result;
    }

    public Optional<ActivityType> findById(Long idActivityType) {
        if (idActivityType != null) {
            return Optional.ofNullable(repository.findById(idActivityType)
                    .orElseThrow(() -> new ObjectnotFoundException("Tipo de Atividade não encontrada!")));
        } else {
            throw new ObjectnotFoundException("ID do Tipo de Atividade não pode ser nulo!");
        }
    }

    public ActivityType findBy(Long idActivityType) {
        Optional<ActivityType> obj = repository.findById(idActivityType);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Tipo de Atividade não encontrada!"));
    }

    public ActivityType update(ActivityType activityType) {
        if (validateActivityType(activityType)) {
            if (findById(activityType.getIdActivityType()) != null) {
                return repository.saveAndFlush(activityType);
            } else {
                return null;
            }
        } else {
            throw new ObjectnotFoundException("Campo Descrição não pode ser vazio!");
        }
    }

    private boolean validateActivityType(ActivityType activityType) {
        if(activityType.isValidDescription()) {
            return true;
        }
        return false;
    }
}
