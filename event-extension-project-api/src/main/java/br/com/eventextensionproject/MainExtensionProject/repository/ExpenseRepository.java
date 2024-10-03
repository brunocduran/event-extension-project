package br.com.eventextensionproject.MainExtensionProject.repository;

import br.com.eventextensionproject.MainExtensionProject.entity.Event;
import br.com.eventextensionproject.MainExtensionProject.entity.Expense;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByEventOrderByExpenseDateAsc(Event event);
    List<Expense> findByOrderByExpenseDateAsc();
    @Query("select sum(e.expenseAmount) from Expense e where e.accountStatus = ?1")
    BigDecimal findTotalExpense(AccountStatus accountStatus);
}
