package br.com.eventextensionproject.MainExtensionProject.entity;

import br.com.eventextensionproject.MainExtensionProject.dto.ExpenseDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AccountStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expense")
    private Long idExpense;

    @Column(name = "description_expense", nullable = false)
    private String descriptionExpense;

    @Column(name = "expense_amount", nullable = false)
    private BigDecimal expenseAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Person person;

    @PrePersist
    private void prePersist() {
        this.setAccountStatus(AccountStatus.ABERTO);
    }

    public Expense(ExpenseDTO obj) throws IOException {
        this.idExpense = obj.getIdExpense();
        this.descriptionExpense = obj.getDescriptionExpense();
        this.expenseAmount = obj.getExpenseAmount();
        this.expenseDate = obj.getExpenseDate();
        this.accountStatus = obj.getAccountStatus();
    }

    @JsonIgnore
    public boolean isValidDescriptionExpense(){
        return this.getDescriptionExpense() != null && !this.getDescriptionExpense().isBlank();
    }

    @JsonIgnore
    public boolean isValidExpenseAmount(){
        return this.getExpenseAmount() != null && this.getExpenseAmount().compareTo(BigDecimal.ZERO) != 0;
    }
}
