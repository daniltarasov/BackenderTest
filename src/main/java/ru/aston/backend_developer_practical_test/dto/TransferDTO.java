package ru.aston.backend_developer_practical_test.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TransferDTO {

    private UUID accountNumberFrom;
    private UUID accountNumberTo;
    private BigDecimal amount;
    private String pinCode;
}
