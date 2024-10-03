package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.entity.JobType;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.JobTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class JobTypeService {

    @Autowired
    private JobTypeRepository repository;

    public List<JobType> getAll() {
        return repository.findByOrderByDescriptionAsc();
    }

    public JobType save(JobType jobType) {
        if(validateJobType(jobType)) {
            return repository.saveAndFlush(jobType);
        } else {
            throw new DataIntegrityViolationException("Campo Descrição não pode ser vazio!");
        }
    }

    public HashMap<String, Object> delete(Long idJobType) {
        String status = "";

        Optional<JobType> jobType =
                Optional.ofNullable(repository.findById(idJobType).
                        orElseThrow(() -> new ObjectnotFoundException("Tipo de Trabalho não encontrado!")));

        //repository.delete(jobType.get());
        if (jobType.get().getSituation() == Situation.ATIVO) {
            jobType.get().setSituation(Situation.INATIVO);
            status = "inativado";
        } else {
            jobType.get().setSituation(Situation.ATIVO);
            status = "ativado";
        }

        repository.saveAndFlush(jobType.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Tipo de Trabalho " + jobType.get().getDescription() + " " + status + " com sucesso!");
        return result;
    }

    public Optional<JobType> findById(Long idJobType) {
        if (idJobType != null) {
            return Optional.ofNullable(repository.findById(idJobType)
                    .orElseThrow(() -> new ObjectnotFoundException("Tipo de Trabalho não encontrado!")));
        } else {
            throw new ObjectnotFoundException("ID do Tipo de Trabalho não pode ser nulo!");
        }
    }

    public JobType update(JobType jobType) {
        if (validateJobType(jobType)) {
            if (findById(jobType.getIdJobType()) != null) {
                return repository.saveAndFlush(jobType);
            } else {
                return null;
            }
        } else {
            throw new ObjectnotFoundException("Campo Descrição não pode ser vazio!");
        }
    }

    private boolean validateJobType(JobType jobType) {
        if(jobType.isValidDescription()) {
            return true;
        }
        return false;
    }
}
