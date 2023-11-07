package ru.aston.backend_developer_practical_test.dto;

import lombok.Data;
import ru.aston.backend_developer_practical_test.entities.Transaction;
import ru.aston.backend_developer_practical_test.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class TransactionDTO {

    private UUID id;
    private UUID fromAccount;
    private UUID toAccount;
    private BigDecimal transactionAmount;
    private OperationType operationType;
    private LocalDateTime operationTime;

    public TransactionDTO (Transaction transaction) {
        this.id = transaction.getId();
        this.fromAccount = transaction.getFromAccount() != null ? transaction.getFromAccount().getId() : new UUID(0, 0);
        this.toAccount = transaction.getToAccount() != null ? transaction.getToAccount().getId() : new UUID(0, 0);
        this.transactionAmount = transaction.getTransactionAmount();
        this.operationType = transaction.getOperationType();
        this.operationTime = transaction.getOperationTime();
    }
}
