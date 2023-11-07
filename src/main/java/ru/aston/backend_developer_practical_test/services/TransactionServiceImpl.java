package ru.aston.backend_developer_practical_test.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.backend_developer_practical_test.dto.DepositDTO;
import ru.aston.backend_developer_practical_test.dto.TransactionDTO;
import ru.aston.backend_developer_practical_test.dto.TransferDTO;
import ru.aston.backend_developer_practical_test.dto.WithdrawDTO;
import ru.aston.backend_developer_practical_test.entities.Account;
import ru.aston.backend_developer_practical_test.entities.Transaction;
import ru.aston.backend_developer_practical_test.enums.OperationType;
import ru.aston.backend_developer_practical_test.exceptions.InsufficientFundsException;
import ru.aston.backend_developer_practical_test.exceptions.InvalidPinException;
import ru.aston.backend_developer_practical_test.exceptions.NoSearchResultException;
import ru.aston.backend_developer_practical_test.repositories.TransactionRepository;
import ru.aston.backend_developer_practical_test.services.interfaces.AccountService;
import ru.aston.backend_developer_practical_test.services.interfaces.TransactionService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public TransactionDTO deposit(DepositDTO depositDTO) {

        Account accountTo = getAccount(depositDTO.getAccountNumber(), "Аккаунт не найден");

        accountTo.setBalance(accountTo.getBalance().add(depositDTO.getAmount()));
        Account updatedAccountTo = accountService.updateBalance(accountTo);

        Transaction depositTransaction = Transaction.builder()
                .toAccount(updatedAccountTo)
                .transactionAmount(depositDTO.getAmount())
                .operationType(OperationType.DEPOSIT)
                .operationTime(LocalDateTime.now())
                .build();

        return new TransactionDTO(transactionRepository.save(depositTransaction));
    }

    @Override
    @Transactional
    public TransactionDTO withdraw(WithdrawDTO withdrawDTO) {

        Account accountFrom = getAccount(withdrawDTO.getAccountNumber(), "Аккаунт не найден");

        checkBalance(accountFrom, withdrawDTO.getAmount());
        checkPinCode(accountFrom, withdrawDTO.getPinCode());

        accountFrom.setBalance(accountFrom.getBalance().subtract(withdrawDTO.getAmount()));
        Account updatedAccountFrom = accountService.updateBalance(accountFrom);

        Transaction withdrawTransaction = Transaction.builder()
                .fromAccount(updatedAccountFrom)
                .transactionAmount(withdrawDTO.getAmount())
                .operationType(OperationType.WITHDRAW)
                .operationTime(LocalDateTime.now())
                .build();

        return new TransactionDTO(transactionRepository.save(withdrawTransaction));
    }

    @Override
    @Transactional
    public TransactionDTO  transfer(TransferDTO transferDTO) {

        Account accountFrom = getAccount(transferDTO.getAccountNumberFrom(), "Аккаунт отправителя не найден");
        Account accountTo = getAccount(transferDTO.getAccountNumberTo(), "Аккаунт получателя не найден");

        checkBalance(accountFrom, transferDTO.getAmount());
        checkPinCode(accountFrom, transferDTO.getPinCode());

        accountFrom.setBalance(accountFrom.getBalance().subtract(transferDTO.getAmount()));
        accountTo.setBalance(accountTo.getBalance().add(transferDTO.getAmount()));
        Account updatedAccountFrom = accountService.updateBalance(accountFrom);
        Account updatedAccountTo = accountService.updateBalance(accountTo);

        Transaction transferTransaction = Transaction.builder()
                .fromAccount(updatedAccountFrom)
                .toAccount(updatedAccountTo)
                .transactionAmount(transferDTO.getAmount())
                .operationType(OperationType.TRANSFER)
                .operationTime(LocalDateTime.now())
                .build();

        return new TransactionDTO(transactionRepository.save(transferTransaction));
    }

    @Override
    public List<TransactionDTO> retrieveTransactionsForAccount(UUID accountNumber) {
        Account account = getAccount(accountNumber, "Аккаунт не найден");

        return transactionRepository.findByFromAccountOrToAccount(account, account)
                .stream().map(TransactionDTO::new).collect(Collectors.toList());
    }

    private Account getAccount(UUID accountID, String message) {
        return accountService.findAccount(accountID)
                .orElseThrow(() -> new NoSearchResultException(message));
    }

    private void checkBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете");
        }
    }

    private void checkPinCode(Account account, String pinCode) {
        if (!(account.getBeneficiary().getPinCode().equals(pinCode))) {
            throw new InvalidPinException("Неверный пин код");
        }
    }
}
