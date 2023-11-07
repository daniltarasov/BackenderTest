package ru.aston.backend_developer_practical_test.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.backend_developer_practical_test.dto.*;
import ru.aston.backend_developer_practical_test.entities.Account;
import ru.aston.backend_developer_practical_test.entities.Beneficiary;
import ru.aston.backend_developer_practical_test.entities.Transaction;
import ru.aston.backend_developer_practical_test.enums.OperationType;
import ru.aston.backend_developer_practical_test.repositories.TransactionRepository;
import ru.aston.backend_developer_practical_test.services.interfaces.AccountService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private DepositDTO depositDTO;
    private WithdrawDTO withdrawDTO;
    private TransferDTO transferDTO;
    private Account accountFrom;
    private Account accountTo;
    private Beneficiary beneficiary;

    @BeforeEach
    public void setUp() {

        UUID accountNumberFrom = UUID.randomUUID();
        UUID accountNumberTo = UUID.randomUUID();

        depositDTO = new DepositDTO();
        depositDTO.setAccountNumber(accountNumberFrom);
        depositDTO.setAmount(BigDecimal.valueOf(100));

        withdrawDTO = new WithdrawDTO();
        withdrawDTO.setAccountNumber(accountNumberFrom);
        withdrawDTO.setAmount(BigDecimal.valueOf(100));
        withdrawDTO.setPinCode("1234");

        transferDTO = new TransferDTO();
        transferDTO.setAccountNumberFrom(accountNumberFrom);
        transferDTO.setAccountNumberTo(accountNumberTo);
        transferDTO.setAmount(BigDecimal.valueOf(100));
        transferDTO.setPinCode("1234");

        beneficiary = new Beneficiary();
        beneficiary.setPinCode("1234");

        accountFrom = new Account();
        accountFrom.setId(accountNumberFrom);
        accountFrom.setBalance(BigDecimal.valueOf(200));
        accountFrom.setBeneficiary(beneficiary);

        accountTo = new Account();
        accountTo.setId(accountNumberTo);
        accountTo.setBalance(BigDecimal.valueOf(100));
        accountTo.setBeneficiary(beneficiary);



    }

    @Test
    void testDeposit_validDepositDTO_depositAmountAndSaveTransaction() {

        when(accountService.findAccount(depositDTO.getAccountNumber())).thenReturn(java.util.Optional.of(accountFrom));
        when(accountService.updateBalance(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        TransactionDTO result = transactionService.deposit(depositDTO);

        assertEquals(depositDTO.getAccountNumber(), result.getToAccount());
        assertEquals(depositDTO.getAmount(), result.getTransactionAmount());
        assertEquals(OperationType.DEPOSIT, result.getOperationType());
    }

    @Test
    void testWithdraw_validWithdrawDTO_withdrawAmountAndSaveTransaction() {

        when(accountService.findAccount(withdrawDTO.getAccountNumber())).thenReturn(java.util.Optional.of(accountFrom));
        when(accountService.updateBalance(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        TransactionDTO result = transactionService.withdraw(withdrawDTO);

        assertEquals(withdrawDTO.getAccountNumber(), result.getFromAccount());
        assertEquals(withdrawDTO.getAmount(), result.getTransactionAmount());
        assertEquals(OperationType.WITHDRAW, result.getOperationType());
    }

    @Test
    void testTransfer_validTransferDTO_transferAmountAndSaveTransaction() {

        when(accountService.findAccount(transferDTO.getAccountNumberFrom())).thenReturn(java.util.Optional.of(accountFrom));
        when(accountService.findAccount(transferDTO.getAccountNumberTo())).thenReturn(java.util.Optional.of(accountTo));
        when(accountService.updateBalance(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        TransactionDTO result = transactionService.transfer(transferDTO);

        assertEquals(transferDTO.getAccountNumberFrom(), result.getFromAccount());
        assertEquals(transferDTO.getAccountNumberTo(), result.getToAccount());
        assertEquals(transferDTO.getAmount(), result.getTransactionAmount());
        assertEquals(OperationType.TRANSFER, result.getOperationType());
    }

    @Test
    void testRetrieveTransactionsForAccount_validAccountNumber_retrieveTransactions() {

        when(accountService.findAccount(accountFrom.getId())).thenReturn(java.util.Optional.of(accountFrom));
        when(transactionRepository.findByFromAccountOrToAccount(accountFrom, accountFrom)).thenReturn(java.util.Collections.singletonList(new Transaction()));

        var result = transactionService.retrieveTransactionsForAccount(accountFrom.getId());

        assertEquals(1, result.size());
    }
}
