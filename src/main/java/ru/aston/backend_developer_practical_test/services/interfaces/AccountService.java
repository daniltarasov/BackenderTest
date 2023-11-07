package ru.aston.backend_developer_practical_test.services.interfaces;

import ru.aston.backend_developer_practical_test.dto.*;
import ru.aston.backend_developer_practical_test.entities.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    AccountDTO createAccount(BeneficiaryDTO beneficiaryDTO);

    List<AccountDTO> getAllAccounts();

    Optional<Account> findAccount (UUID accountNumber);

    Account updateBalance(Account account);


}
