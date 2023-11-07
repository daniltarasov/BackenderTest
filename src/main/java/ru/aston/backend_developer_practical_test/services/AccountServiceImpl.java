package ru.aston.backend_developer_practical_test.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.backend_developer_practical_test.dto.*;
import ru.aston.backend_developer_practical_test.entities.Account;
import ru.aston.backend_developer_practical_test.entities.Beneficiary;
import ru.aston.backend_developer_practical_test.exceptions.InvalidPinException;
import ru.aston.backend_developer_practical_test.repositories.AccountRepository;
import ru.aston.backend_developer_practical_test.services.interfaces.AccountService;
import ru.aston.backend_developer_practical_test.services.interfaces.BeneficiaryService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final BeneficiaryService beneficiaryService;

    @Override
    @Transactional
    public AccountDTO createAccount(BeneficiaryDTO beneficiaryDTO) {

        Beneficiary owner = beneficiaryService.findBeneficiary(beneficiaryDTO.getName());
        if (!beneficiaryDTO.getPinCode().equals(owner.getPinCode())) {
            throw new InvalidPinException("PIN-код не совпадает");
        }
        return new AccountDTO(accountRepository.save(new Account(owner)));
    }

    public List<AccountDTO> getAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<Account> findAccount (UUID accountNumber) {

        return accountRepository.findById(accountNumber);
    }

    @Transactional
    public Account updateBalance(Account account){

        return accountRepository.save(account);
    }
}
