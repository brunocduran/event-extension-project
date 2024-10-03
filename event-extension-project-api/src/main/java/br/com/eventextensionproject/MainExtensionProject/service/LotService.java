package br.com.eventextensionproject.MainExtensionProject.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import br.com.eventextensionproject.MainExtensionProject.dto.LotDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.eventextensionproject.MainExtensionProject.entity.Lot;
import br.com.eventextensionproject.MainExtensionProject.repository.LotRepository;
import org.springframework.stereotype.Service;

@Service
public class LotService {

    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private EventService eventService;

    public List<Lot> getAll() {
        return lotRepository.findByOrderByStartDateLot();
    }

    public Lot findById(Long idLot) {
        Optional<Lot> obj = lotRepository.findById(idLot);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Lote do Evento não encontrado!"));
    }

    public List<Lot> findByEvent(Long idEvent) {
        Event event = eventService.findById(idEvent);
        return lotRepository.findByEventOrderByStartDateLot(event);
    }

    public Lot findByEventAndActive(Event event, Situation situation) {
        return lotRepository.findByEventAndSituation(event, situation);
    }

    public Lot findByEventAndActiveHome(Event event, Situation situation) {
        Lot lot = lotRepository.findByEventAndSituation(event, situation);
        checkDateLot(lot);
        lot = lotRepository.findByEventAndSituation(event, situation);
        return lot;
    }

    public Lot save(LotDTO lotDTO) {
        Lot lot = new Lot(lotDTO);
        lot.setEvent(eventService.findById(lotDTO.getIdEvent()));

        Lot lotActive = findByEventAndActive(lot.getEvent(), Situation.ATIVO);

        if(lotActive == null) {
            lot.setSituation(Situation.ATIVO);
        }else {
            lot.setSituation(Situation.INATIVO);
        }

        if(validateLot(lot)) {
            return lotRepository.saveAndFlush(lot);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public HashMap<String, Object> delete(Long idLot) {

        Optional<Lot> lot =
                Optional.ofNullable(lotRepository.findById(idLot).
                        orElseThrow(() -> new ObjectnotFoundException("Lote do Evento não encontrado!")));

        lotRepository.delete(lot.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Lote do Evento " + lot.get().getNameLot() +  " excluído com sucesso!");
        return result;
    }

    public Lot update(LotDTO lotDTO) {
        Lot oldLot = findById(lotDTO.getIdLot());

        Lot lot = new Lot(lotDTO);
        lot.setSituation(oldLot.getSituation());
        lot.setEvent(eventService.findById(lotDTO.getIdEvent()));
        lot.setQuantityRegistrationsLot(oldLot.getQuantityRegistrationsLot());

        if(validateLot(lot)) {
            return lotRepository.saveAndFlush(lot);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public void addRegistrations(Lot lot){
        lot.setQuantityRegistrationsLot(lot.getQuantityRegistrationsLot()+1);

        if(lot.getQuantityRegistrationsLot().equals(lot.getQuantityTicketsLot())){
            lot.setSituation(Situation.INATIVO);
            lotRepository.saveAndFlush(lot);

            Lot newLot = lotRepository.findFirst1ByEventAndEndDateLotGreaterThanOrderByStartDateLotAsc(lot.getEvent(), LocalDate.now());
            if(newLot != null){
                newLot.setSituation(Situation.ATIVO);
                lotRepository.saveAndFlush(newLot);
            }
        }else{
            lotRepository.saveAndFlush(lot);
        }
    }

    public void subtractRegistrations(Lot lot){
        lot.setQuantityRegistrationsLot(lot.getQuantityRegistrationsLot()-1);
        lotRepository.saveAndFlush(lot);
    }

    public void checkDateLot(Lot lot){
        if(lot != null) {
            if (lot.getEndDateLot().isBefore(LocalDate.now())) {
                lot.setSituation(Situation.INATIVO);
                lotRepository.saveAndFlush(lot);

                Lot newLot = lotRepository.findFirst1ByEventAndEndDateLotGreaterThanOrderByStartDateLotAsc(lot.getEvent(), LocalDate.now());
                if(newLot != null){
                    newLot.setSituation(Situation.ATIVO);
                    lotRepository.saveAndFlush(newLot);
                }
            }
        }
    }

    public HashMap<String, Object> updateStatus(Long idLot) {
        String status = "";

        Optional<Lot> lot =
                Optional.ofNullable(lotRepository.findById(idLot).
                        orElseThrow(() -> new ObjectnotFoundException("Lote do Evento não encontrado!")));

        Lot lotActive = findByEventAndActive(lot.get().getEvent(), Situation.ATIVO);

        if(lotActive == null){
            lot.get().setSituation(Situation.ATIVO);
            status = "ativado";
        } else if(lotActive.getIdLot() == lot.get().getIdLot()){
            lot.get().setSituation(Situation.INATIVO);
            status = "inativado";
        } else {
            throw new DataIntegrityViolationException("Já existe outro lote ativo neste evento!");
        }

        lotRepository.saveAndFlush(lot.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("message", "Lote " + status + " com sucesso!");
        return result;
    }


    private boolean validateLot(Lot lot) {
        if (lot.isValidName()) {
            return true;
        }
        return false;
    }
}
