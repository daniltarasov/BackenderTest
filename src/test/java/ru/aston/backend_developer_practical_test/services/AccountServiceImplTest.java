package ru.aston.backend_developer_practical_test.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.backend_developer_practical_test.dto.AccountDTO;
import ru.aston.backend_developer_practical_test.dto.BeneficiaryDTO;
import ru.aston.backend_developer_practical_test.entities.Account;
import ru.aston.backend_developer_practical_test.entities.Beneficiary;
import ru.aston.backend_developer_practical_test.exceptions.InvalidPinException;
import ru.aston.backend_developer_practical_test.repositories.AccountRepository;
import ru.aston.backend_developer_practical_test.services.interfaces.BeneficiaryService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BeneficiaryService beneficiaryService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private BeneficiaryDTO beneficiaryDTO;
    private Beneficiary beneficiary;
    private Account account;

    @BeforeEach
    public void setUp() {
        beneficiaryDTO = new BeneficiaryDTO();
        beneficiaryDTO.setName("Ivan Ivanov");
        beneficiaryDTO.setPinCode("1234");

        beneficiary = new Beneficiary();
        beneficiary.setName("Ivan Ivanov");
        beneficiary.setPinCode("1234");

        account = new Account(beneficiary);
        account.setId(UUID.randomUUID());
        account.setBalance(BigDecimal.ZERO);
    }

    @Test
    void testCreateAccount_ValidBeneficiary_returnAccountDTO() {
        when(beneficiaryService.findBeneficiary(beneficiaryDTO.getName())).thenReturn(beneficiary);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDTO result = accountService.createAccount(beneficiaryDTO);

        assertEquals(account.getId(), result.getId());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testCreateAccount_invalidBeneficiary_throwInvalidPinException() {
        beneficiaryDTO.setPinCode("0000");
        when(beneficiaryService.findBeneficiary(beneficiaryDTO.getName())).thenReturn(beneficiary);

        assertThrows(InvalidPinException.class, () -> accountService.createAccount(beneficiaryDTO));
    }

    @Test
    void testGetAllAccounts() {
        when(accountRepository.findAll()).thenReturn(Arrays.asList(account));

        List<AccountDTO> result = accountService.getAllAccounts();

        assertEquals(1, result.size());
        assertEquals(account.getId(), result.get(0).getId());
    }

    @Test
    void testFindAccount_validAccountNumber_returnOptionalWithAccount() {

        UUID validAccountNumber = account.getId();
        when(accountRepository.findById(validAccountNumber)).thenReturn(Optional.of(account));

        Optional<Account> result = accountService.findAccount(validAccountNumber);

        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    void testFindAccount_invalidAccountNumber_returnEmptyOptional() {

        UUID invalidAccountNumber = UUID.randomUUID();
        when(accountRepository.findById(invalidAccountNumber)).thenReturn(Optional.empty());

        Optional<Account> result = accountService.findAccount(invalidAccountNumber);

        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateBalance() {

        account.setBalance(BigDecimal.TEN);
        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.updateBalance(account);

        assertEquals(account, result);
        assertEquals(BigDecimal.TEN, result.getBalance());
    }
}
