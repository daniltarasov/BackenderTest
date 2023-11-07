package ru.aston.backend_developer_practical_test.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aston.backend_developer_practical_test.entities.Beneficiary;

import java.util.UUID;

@Data
@NoArgsConstructor
public class BeneficiaryDTO {

    private UUID id;
    private String name;
    private String pinCode;

    public BeneficiaryDTO(Beneficiary beneficiary) {
        this.id = beneficiary.getId();
        this.name = beneficiary.getName();
        this.pinCode = beneficiary.getPinCode();
    }
}
