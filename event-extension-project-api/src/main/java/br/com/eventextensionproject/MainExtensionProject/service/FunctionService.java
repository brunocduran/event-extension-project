package br.com.eventextensionproject.MainExtensionProject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.eventextensionproject.MainExtensionProject.entity.Function;
import br.com.eventextensionproject.MainExtensionProject.repository.FunctionRepository;

@Service
public class FunctionService {

    @Autowired
    private FunctionRepository repository;

    public List<Function> getAll() {
        return repository.findByOrderByDescriptionAsc();
    }

    public Function save(Function function) {
        if (validateFunction(function)) {
            return repository.saveAndFlush(function);
        } else {
            throw new DataIntegrityViolationException("Campo Descrição não pode ser vazio!");
        }
    }

    public HashMap<String, Object> delete(Long idFunction) {
        String status = "";

        Optional<Function> function =
                Optional.ofNullable(repository.findById(idFunction).
                        orElseThrow(() -> new ObjectnotFoundException("Função não encontrada!")));

        //repository.delete(function.get());
        if (function.get().getSituation() == Situation.ATIVO) {
            function.get().setSituation(Situation.INATIVO);
            status = "inativada";
        } else {
            function.get().setSituation(Situation.ATIVO);
            status = "ativada";
        }

        repository.saveAndFlush(function.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Função " + function.get().getDescription() + " " + status + " com sucesso!");
        return result;
    }

    public Optional<Function> findById(Long idFunction) {
        if (idFunction != null) {
            return Optional.ofNullable(repository.findById(idFunction)
                    .orElseThrow(() -> new ObjectnotFoundException("Função não encontrada!")));
        } else {
            throw new ObjectnotFoundException("ID da Função não pode ser nulo!");
        }
    }

    public Function findBy(Long idFunction) {
        Optional<Function> obj = repository.findById(idFunction);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Função não encontrada!"));
    }

    public Function update(Function function) {
        if (validateFunction(function)) {
            if (findById(function.getIdFunction()) != null) {
                return repository.saveAndFlush(function);
            } else {
                return null;
            }
        } else {
            throw new ObjectnotFoundException("Campo Descrição não pode ser vazio!");
        }
    }

    private boolean validateFunction(Function function) {
        if (function.isValidDescription()) {
            return true;
        }
        return false;
    }
}
