package ru.aston.backend_developer_practical_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.backend_developer_practical_test.entities.Account;
import ru.aston.backend_developer_practical_test.entities.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByFromAccountOrToAccount(Account fromAccount, Account toAccount);
}
