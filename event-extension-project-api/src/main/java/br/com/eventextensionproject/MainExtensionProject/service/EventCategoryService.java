package br.com.eventextensionproject.MainExtensionProject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.eventextensionproject.MainExtensionProject.entity.EventCategory;
import br.com.eventextensionproject.MainExtensionProject.repository.EventCategoryRepository;

@Service
public class EventCategoryService {

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    public List<EventCategory> getAll() {
        return eventCategoryRepository.findByOrderByNameEventCategoryAsc();
    }

    public EventCategory save(EventCategory eventCategory) {
        if (validateEventCategory(eventCategory)) {
            return eventCategoryRepository.saveAndFlush(eventCategory);
        } else {
            throw new DataIntegrityViolationException("Campo Nome não pode ser vazio!");
        }
    }

    public HashMap<String, Object> delete(Long idEventCategory) {
        String status = "";

        Optional<EventCategory> eventCategory =
                Optional.ofNullable(eventCategoryRepository.findById(idEventCategory).
                        orElseThrow(() -> new ObjectnotFoundException("Categoria de Evento não encontrada!")));


        //eventCategoryRepository.delete(eventCategory.get());
        if (eventCategory.get().getSituation() == Situation.ATIVO) {
            eventCategory.get().setSituation(Situation.INATIVO);
            status = "inativada";
        } else {
            eventCategory.get().setSituation(Situation.ATIVO);
            status = "ativada";
        }

        eventCategoryRepository.saveAndFlush(eventCategory.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Categoria de Evento " + eventCategory.get().getNameEventCategory() + " " + status + " com sucesso!");
        return result;
    }

    public Optional<EventCategory> findById(Long idEventCategory) {
        if (idEventCategory != null) {
            return Optional.ofNullable(eventCategoryRepository.findById(idEventCategory)
                    .orElseThrow(() -> new ObjectnotFoundException("Categoria de Evento não encontrada!")));
        } else {
            throw new ObjectnotFoundException("ID da Categoria de Evento não pode ser nulo!");
        }
    }

    public EventCategory findBy(Long idEventCategory) {
        Optional<EventCategory> obj = eventCategoryRepository.findById(idEventCategory);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Categoria de Evento não encontrada!"));
    }

    public EventCategory update(EventCategory eventCategory) {
        if (validateEventCategory(eventCategory)) {
            if (findById(eventCategory.getIdEventCategory()) != null) {
                return eventCategoryRepository.saveAndFlush(eventCategory);
            } else {
                return null;
            }
        } else {
            throw new ObjectnotFoundException("Campo Nome não pode ser vazio!");
        }
    }

    private boolean validateEventCategory(EventCategory eventCategory) {
        if (eventCategory.isValidName()) {
            return true;
        }
        return false;
    }
}