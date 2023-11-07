package ru.aston.backend_developer_practical_test.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.backend_developer_practical_test.dto.BeneficiaryDTO;
import ru.aston.backend_developer_practical_test.entities.Beneficiary;
import ru.aston.backend_developer_practical_test.exceptions.NoSearchResultException;
import ru.aston.backend_developer_practical_test.repositories.BeneficiaryRepository;
import ru.aston.backend_developer_practical_test.services.interfaces.BeneficiaryService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;

    @Override
    public BeneficiaryDTO saveBeneficiary(Beneficiary beneficiary) {
        String pinCode = beneficiary.getPinCode();
        if (pinCode == null || !pinCode.matches("^\\d{4}$")) {
            throw new IllegalArgumentException("Pin-код должен состоять из 4 цифр");
        }
        return new BeneficiaryDTO(beneficiaryRepository.save((beneficiary)));
    }

    public List<BeneficiaryDTO> getAllBeneficiaries() {
        return beneficiaryRepository.findAll()
                .stream()
                .map(BeneficiaryDTO::new)
                .collect(Collectors.toList());
    }

    public Beneficiary findBeneficiary(String name){
        return beneficiaryRepository.findByName(name)
                .orElseThrow(() -> new NoSearchResultException("Пользователь не найден"));
    }
}