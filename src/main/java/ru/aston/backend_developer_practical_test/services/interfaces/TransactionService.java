package ru.aston.backend_developer_practical_test.services.interfaces;

import ru.aston.backend_developer_practical_test.dto.DepositDTO;
import ru.aston.backend_developer_practical_test.dto.TransactionDTO;
import ru.aston.backend_developer_practical_test.dto.TransferDTO;
import ru.aston.backend_developer_practical_test.dto.WithdrawDTO;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionDTO deposit(DepositDTO depositDTO);

    TransactionDTO withdraw(WithdrawDTO withdrawDTO);

    TransactionDTO  transfer(TransferDTO transferDTO);

    List<TransactionDTO> retrieveTransactionsForAccount(UUID accountNumber);
}
