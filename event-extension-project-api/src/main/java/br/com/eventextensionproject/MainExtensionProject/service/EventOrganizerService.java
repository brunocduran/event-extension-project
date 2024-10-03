package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.dto.EventOrganizerDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.EventOrganizer;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.EventOrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EventOrganizerService {

    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private PersonService personService;
    @Autowired
    private FunctionService functionService;

    public List<EventOrganizer> getAll() {
        return eventOrganizerRepository.findAll();
    }

    public EventOrganizer findById(Long idEventOrganizer) {
        Optional<EventOrganizer> obj = eventOrganizerRepository.findById(idEventOrganizer);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Organizador do Evento não encontrado!"));
    }

    public List<EventOrganizer> findByEvent(Long idEvent) {
        Event event = eventService.findById(idEvent);
        return eventOrganizerRepository.findByEvent(event);
    }

    public EventOrganizer save(EventOrganizerDTO eventOrganizerDTO) {
        EventOrganizer eventOrganizer = new EventOrganizer();
        eventOrganizer.setEvent(eventService.findById(eventOrganizerDTO.getIdEvent()));
        eventOrganizer.setPerson(personService.findById(eventOrganizerDTO.getIdPerson()));
        eventOrganizer.setFunction(functionService.findBy(eventOrganizerDTO.getIdFunction()));

        return eventOrganizerRepository.saveAndFlush(eventOrganizer);
    }

    public HashMap<String, Object> delete(Long idEventOrganizer) {

        Optional<EventOrganizer> eventOrganizer =
                Optional.ofNullable(eventOrganizerRepository.findById(idEventOrganizer).
                        orElseThrow(() -> new ObjectnotFoundException("Organizador do Evento não encontrado!")));

        eventOrganizerRepository.delete(eventOrganizer.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Organizador " + eventOrganizer.get().getPerson().getNamePerson() +  " excluído do Evento!");
        return result;
    }

    public EventOrganizer update(EventOrganizerDTO eventOrganizerDTO) {
        findById(eventOrganizerDTO.getIdEventOrganizer());

        EventOrganizer eventOrganizer = new EventOrganizer();
        eventOrganizer.setIdEventOrganizer(eventOrganizerDTO.getIdEventOrganizer());
        eventOrganizer.setEvent(eventService.findById(eventOrganizerDTO.getIdEvent()));
        eventOrganizer.setPerson(personService.findById(eventOrganizerDTO.getIdPerson()));
        eventOrganizer.setFunction(functionService.findBy(eventOrganizerDTO.getIdFunction()));

        return eventOrganizerRepository.saveAndFlush(eventOrganizer);
    }

}
