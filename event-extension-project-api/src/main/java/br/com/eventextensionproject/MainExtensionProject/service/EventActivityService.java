package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.dto.EventActivityDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Enrollment;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.EventActivity;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AttendanceStatus;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.EventActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EventActivityService {

    @Autowired
    private EventActivityRepository eventActivityRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private ActivityTypeService activityTypeService;
    @Autowired
    private CityService cityService;

    public List<EventActivity> getAll() {
        return eventActivityRepository.findByOrderByActivityNameAsc();
    }

    public EventActivity findById(Long idEventActivity) {
        Optional<EventActivity> obj = eventActivityRepository.findById(idEventActivity);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Atividade do Evento não encontrada!"));
    }

    public List<EventActivity> findEventActivityCertificate(Enrollment enrollment) {
        List<EventActivity> eventActivityList = eventActivityRepository.findEventActivityCertificate(enrollment, AttendanceStatus.PRESENTE);

        if(eventActivityList.isEmpty()){
            throw new DataIntegrityViolationException("Você não tem presença em nenhuma atividade, não é possível gerar o certificado!");
        }

        return eventActivityList;
    }

    public List<EventActivity> findByEvent(Long idEvent) {
        Event event = eventService.findById(idEvent);
        return eventActivityRepository.findByEventOrderByActivityName(event);
    }

    public EventActivity save(EventActivityDTO eventActivityDTO) {
        EventActivity eventActivity = new EventActivity(eventActivityDTO);
        eventActivity.setActivityType(activityTypeService.findBy(eventActivityDTO.getIdActivityType()));
        eventActivity.setEvent(eventService.findById(eventActivityDTO.getIdEvent()));
        eventActivity.setCity(cityService.findById(eventActivityDTO.getIdCity()));

        if (validateEventActivity(eventActivity)) {
            return eventActivityRepository.saveAndFlush(eventActivity);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    
    public List<LocalDate> getDistinctRecentActivityDates(Long idEvent) {
        Event event = eventService.findById(idEvent);
        return eventActivityRepository.findDistinctRecentActivityDates(event);
    }

    public List<EventActivity> findByActivityEventDate(Long idEvent,LocalDate date){
        Event event = eventService.findById(idEvent);
       return eventActivityRepository.findAllByActivityEventDate(event,date);
   }

    public HashMap<String, Object> delete(Long idEventActivity) {

        Optional<EventActivity> eventActivity =
                Optional.ofNullable(eventActivityRepository.findById(idEventActivity).
                        orElseThrow(() -> new ObjectnotFoundException("Atividade do Evento não encontrada!")));

        eventActivityRepository.delete(eventActivity.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Atividade do Evento " + eventActivity.get().getActivityName() + " excluída com sucesso!");
        return result;
    }

    public EventActivity update(EventActivityDTO eventActivityDTO) {
        EventActivity oldEventActivity = findById(eventActivityDTO.getIdEventActivity());

        EventActivity eventActivity = new EventActivity(eventActivityDTO);
        eventActivity.setActivityType(activityTypeService.findBy(eventActivityDTO.getIdActivityType()));
        eventActivity.setEvent(eventService.findById(eventActivityDTO.getIdEvent()));
        eventActivity.setCity(cityService.findById(eventActivityDTO.getIdCity()));
        eventActivity.setQuantityRegistrationsEventActivity(oldEventActivity.getQuantityRegistrationsEventActivity());

        if (validateEventActivity(eventActivity)) {
            return eventActivityRepository.saveAndFlush(eventActivity);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public void addRegistrations(EventActivity eventActivity) {
        eventActivity.setQuantityRegistrationsEventActivity(eventActivity.getQuantityRegistrationsEventActivity() + 1);
        eventActivityRepository.saveAndFlush(eventActivity);
    }

    public void subtractRegistrations(EventActivity eventActivity) {
        eventActivity.setQuantityRegistrationsEventActivity(eventActivity.getQuantityRegistrationsEventActivity() - 1);
        eventActivityRepository.saveAndFlush(eventActivity);
    }

    private boolean validateEventActivity(EventActivity eventActivity) {
        if (eventActivity.isValidTimeActivity() && eventActivity.isValidSummary() &&
                eventActivity.isValidHourlyLoad() && eventActivity.isValidActivityName() &&
                eventActivity.isValidAddressEventActivity() && eventActivity.isValidNumberAddressEventActivity() &&
                eventActivity.isValidNeighborhoodEventActivity() && eventActivity.isValidZipCodeEventActivity()) {
            return true;
        }
        return false;
    }

}
