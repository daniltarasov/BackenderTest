package ru.aston.backend_developer_practical_test.services.interfaces;

import ru.aston.backend_developer_practical_test.dto.BeneficiaryDTO;
import ru.aston.backend_developer_practical_test.entities.Beneficiary;

import java.util.List;

public interface BeneficiaryService {
    BeneficiaryDTO saveBeneficiary(Beneficiary beneficiary);

    Beneficiary findBeneficiary(String name);

    List<BeneficiaryDTO> getAllBeneficiaries();

}
