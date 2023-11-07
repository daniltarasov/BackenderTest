package ru.aston.backend_developer_practical_test.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class DepositDTO {

    private UUID accountNumber;

    private BigDecimal amount;
}
