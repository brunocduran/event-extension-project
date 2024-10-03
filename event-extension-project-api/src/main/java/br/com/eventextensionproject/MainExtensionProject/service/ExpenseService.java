package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.dto.ExpenseDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.Expense;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AccountStatus;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private PersonService personService;

    public List<Expense> getAll() {
        return expenseRepository.findByOrderByExpenseDateAsc();
    }

    public Expense findById(Long idExpense) {
        Optional<Expense> obj = expenseRepository.findById(idExpense);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Despesa não encontrada!"));
    }

    public List<Expense> findByEvent(Long idEvent) {
        Event event = eventService.findById(idEvent);
        return expenseRepository.findByEventOrderByExpenseDateAsc(event);
    }

    public HashMap<String, Object> findTotalExpense() {
        BigDecimal valor = expenseRepository.findTotalExpense(AccountStatus.BAIXADO);

        if(valor == null){
            valor = BigDecimal.valueOf(0.00);
        }

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", valor);
        return result;
    }

    public Expense save(ExpenseDTO expenseDTO) throws IOException {
        Expense expense = new Expense(expenseDTO);
        expense.setEvent(eventService.findById(expenseDTO.getIdEvent()));
        expense.setPerson(personService.findById(expenseDTO.getIdPerson()));

        if (validateExpense(expense)) {
            return expenseRepository.saveAndFlush(expense);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public HashMap<String, Object> delete(Long idExpense) {

        Optional<Expense> expense =
                Optional.ofNullable(expenseRepository.findById(idExpense).
                        orElseThrow(() -> new ObjectnotFoundException("Despesa não encontrada!")));

        if(expense.get().getAccountStatus() == AccountStatus.BAIXADO){
            throw new DataIntegrityViolationException("Despesa já baixada, não é permitido excluir!");
        }

        expenseRepository.delete(expense.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Despesa " + expense.get().getDescriptionExpense() +  " excluída com sucesso!");
        return result;
    }

    public Expense update(ExpenseDTO expenseDTO) throws IOException {
        findById(expenseDTO.getIdExpense());

        Expense oldExpense = expenseRepository.findById(expenseDTO.getIdExpense()).orElse(null);

        if(oldExpense.getAccountStatus() == AccountStatus.BAIXADO){
            throw new DataIntegrityViolationException("Despesa já baixada, não é permitido alterar!");
        }

        Expense expense = new Expense(expenseDTO);
        expense.setEvent(eventService.findById(expenseDTO.getIdEvent()));
        expense.setPerson(personService.findById(expenseDTO.getIdPerson()));
        expense.setAccountStatus(AccountStatus.ABERTO);

        if (validateExpense(expense)) {
            return expenseRepository.saveAndFlush(expense);
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public HashMap<String, Object> updateStatus(Long idExpense) {
        String status = "";

        Optional<Expense> expense =
                Optional.ofNullable(expenseRepository.findById(idExpense).
                        orElseThrow(() -> new ObjectnotFoundException("Despesa não encontrada!")));

        if (expense.get().getAccountStatus() == AccountStatus.ABERTO) {
            expense.get().setAccountStatus(AccountStatus.BAIXADO);
            status = "baixada";
        } else {
            expense.get().setAccountStatus(AccountStatus.ABERTO);
            status = "estornada";
        }

        expenseRepository.saveAndFlush(expense.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Despesa " + status + " com sucesso!");
        return result;
    }

    private boolean validateExpense(Expense expense){
        if(expense.isValidDescriptionExpense() && expense.isValidDescriptionExpense()){
            return true;
        }
        return false;
    }

}