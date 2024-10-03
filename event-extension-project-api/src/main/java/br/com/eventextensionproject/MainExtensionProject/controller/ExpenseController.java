package br.com.eventextensionproject.MainExtensionProject.controller;

import br.com.eventextensionproject.MainExtensionProject.dto.ExpenseDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Expense;
import br.com.eventextensionproject.MainExtensionProject.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/expense")
@CrossOrigin(value = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping(value = "/list")
    public ResponseEntity<Object> getAll() {
        List<Expense> result = expenseService.getAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/findTotalExpense")
    public ResponseEntity<Object> findTotalExpense() {
        HashMap<String, Object> result = expenseService.findTotalExpense();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listById/{idExpense}")
    public ResponseEntity<Object> findById(@PathVariable Long idExpense) {
        Expense result = expenseService.findById(idExpense);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/listByEvent/{idEvent}")
    public ResponseEntity<Object> getByState(@PathVariable Long idEvent) {
        List<Expense> result = expenseService.findByEvent(idEvent);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Object> save(@RequestBody ExpenseDTO expenseDTO) throws IOException {
        Expense result = expenseService.save(expenseDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(value = "/delete/{idExpense}")
    public ResponseEntity<Object> delete(@PathVariable Long idExpense) {
        HashMap<String, Object> result = expenseService.delete(idExpense);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody ExpenseDTO expenseDTO) throws IOException {
        Expense result = expenseService.update(expenseDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/updateStatus/{idExpense}")
    public ResponseEntity<Object> updateStatus(@PathVariable Long idExpense) {
        HashMap<String, Object> result = expenseService.updateStatus(idExpense);
        return ResponseEntity.ok().body(result);
    }
}