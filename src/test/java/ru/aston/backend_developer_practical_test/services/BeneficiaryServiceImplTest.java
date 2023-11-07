package ru.aston.backend_developer_practical_test.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.backend_developer_practical_test.dto.BeneficiaryDTO;
import ru.aston.backend_developer_practical_test.entities.Beneficiary;
import ru.aston.backend_developer_practical_test.exceptions.NoSearchResultException;
import ru.aston.backend_developer_practical_test.repositories.BeneficiaryRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeneficiaryServiceImplTest {

    @Mock
    private BeneficiaryRepository beneficiaryRepository;

    @InjectMocks
    private BeneficiaryServiceImpl beneficiaryService;

    private Beneficiary beneficiary;
    private BeneficiaryDTO beneficiaryDTO;

    @BeforeEach
    public void setUp() {
        beneficiary = new Beneficiary();
        beneficiary.setId(UUID.randomUUID());
        beneficiary.setName("Ivan Ivanov");
        beneficiary.setPinCode("1234");

        beneficiaryDTO = new BeneficiaryDTO(beneficiary);
    }

    @Test
    void testSaveBeneficiary_withValidValues_beneficiarySaved() {
        when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);

        BeneficiaryDTO result = beneficiaryService.saveBeneficiary(beneficiary);

        assertEquals(beneficiaryDTO, result);
        verify(beneficiaryRepository, times(1)).save(beneficiary);
    }

    @Test
    void testGetAllBeneficiaries_returnAllBeneficiaries() {
        List<Beneficiary> beneficiaries = Arrays.asList(beneficiary);
        when(beneficiaryRepository.findAll()).thenReturn(beneficiaries);

        List<BeneficiaryDTO> result = beneficiaryService.getAllBeneficiaries();

        assertEquals(1, result.size());
        assertEquals(beneficiaryDTO, result.get(0));
        verify(beneficiaryRepository, times(1)).findAll();
    }


    @Test
    void testGetAllBeneficiaries_whenNoBeneficiaries_returnEmptyList() {
        when(beneficiaryRepository.findAll()).thenReturn(Collections.emptyList());

        List<BeneficiaryDTO> result = beneficiaryService.getAllBeneficiaries();

        assertTrue(result.isEmpty());
        verify(beneficiaryRepository, times(1)).findAll();
    }

    @Test
    void testFindBeneficiary_whenBeneficiaryFound_thenNoExceptionThrown() {
        when(beneficiaryRepository.findByName(beneficiary.getName())).thenReturn(Optional.of(beneficiary));

        assertDoesNotThrow(() -> beneficiaryService.findBeneficiary(beneficiary.getName()));
        verify(beneficiaryRepository, times(1)).findByName(beneficiary.getName());

    }

    @Test
    void testSaveBeneficiary_withValidValues_returnBeneficiaryDTO() {
        when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);

        BeneficiaryDTO result = beneficiaryService.saveBeneficiary(beneficiary);

        assertEquals(beneficiaryDTO, result);
        verify(beneficiaryRepository, times(1)).save(beneficiary);
    }

    @Test
    void testFindBeneficiary_withInvalidBeneficiary_throwNoSearchResultException() {
        when(beneficiaryRepository.findByName(beneficiary.getName())).thenReturn(Optional.empty());

        assertThrows(NoSearchResultException.class, () -> beneficiaryService.findBeneficiary(beneficiary.getName()));
        verify(beneficiaryRepository, times(1)).findByName(beneficiary.getName());
    }
}
