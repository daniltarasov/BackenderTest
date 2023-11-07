package ru.aston.backend_developer_practical_test.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class WithdrawDTO {

    private UUID accountNumber;
    private BigDecimal amount;
    private String pinCode;
}
