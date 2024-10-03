package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.dto.DonationDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Donation;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AccountStatus;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private PersonService personService;

    public List<Donation> getAll() {
        return donationRepository.findByOrderByDonationDateAsc();
    }

    public Donation findById(Long idDonation) {
        Optional<Donation> obj = donationRepository.findById(idDonation);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Doação não encontrada!"));
    }

    public List<Donation> findByEvent(Long idEvent) {
        Event event = eventService.findById(idEvent);
        return donationRepository.findByEventOrderByDonationDateAsc(event);
    }

    public HashMap<String, Object> findTotalDonation() {
        BigDecimal valor = donationRepository.findTotalDonation(AccountStatus.BAIXADO);

        if(valor == null){
            valor = BigDecimal.valueOf(0.00);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", valor);
        return result;
    }

    public Donation save(DonationDTO donationDTO) throws IOException {
        Donation donation = new Donation(donationDTO);
        donation.setEvent(eventService.findById(donationDTO.getIdEvent()));
        donation.setPerson(personService.findById(donationDTO.getIdPerson()));

        if (validateDonation(donation)) {
            return donationRepository.saveAndFlush(donation);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public HashMap<String, Object> delete(Long idDonation) {

        Optional<Donation> donation =
                Optional.ofNullable(donationRepository.findById(idDonation).
                        orElseThrow(() -> new ObjectnotFoundException("Doação não encontrada!")));

        if(donation.get().getAccountStatus() == AccountStatus.BAIXADO){
            throw new DataIntegrityViolationException("Doação já baixada, não é permitido excluir!");
        }

        donationRepository.delete(donation.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Doação " + donation.get().getDescriptionDonation() +  " excluída com sucesso!");
        return result;
    }

    public Donation update(DonationDTO donationDTO) throws IOException {
        findById(donationDTO.getIdDonation());

        Donation oldDonation = donationRepository.findById(donationDTO.getIdDonation()).orElse(null);

        if(oldDonation.getAccountStatus() == AccountStatus.BAIXADO){
            throw new DataIntegrityViolationException("Doação já baixada, não é permitido alterar!");
        }

        Donation donation = new Donation(donationDTO);
        donation.setEvent(eventService.findById(donationDTO.getIdEvent()));
        donation.setPerson(personService.findById(donationDTO.getIdPerson()));
        donation.setAccountStatus(AccountStatus.ABERTO);

        if (validateDonation(donation)) {
            return donationRepository.saveAndFlush(donation);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public HashMap<String, Object> updateStatus(Long idDonation) {
        String status = "";

        Optional<Donation> donation =
                Optional.ofNullable(donationRepository.findById(idDonation).
                        orElseThrow(() -> new ObjectnotFoundException("Doação não encontrada!")));

        if (donation.get().getAccountStatus() == AccountStatus.ABERTO) {
            donation.get().setAccountStatus(AccountStatus.BAIXADO);
            status = "baixada";
        } else {
            donation.get().setAccountStatus(AccountStatus.ABERTO);
            status = "estornada";
        }

        donationRepository.saveAndFlush(donation.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Doação " + status + " com sucesso!");
        return result;
    }

    private boolean validateDonation(Donation donation){
        if(donation.isValidDescriptionDonation() && donation.isValidDescriptionDonation()){
            return true;
        }
        return false;
    }

}
