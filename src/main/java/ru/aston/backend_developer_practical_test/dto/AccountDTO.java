package ru.aston.backend_developer_practical_test.dto;

import lombok.*;
import ru.aston.backend_developer_practical_test.entities.Account;
import java.math.BigDecimal;
import java.util.UUID;


@Data
public class AccountDTO {

    private UUID id;
    private String beneficiary;
    private BigDecimal balance;


    public AccountDTO (Account account){
        this.id = account.getId();
        this.beneficiary = account.getBeneficiary().getName();
        this.balance = account.getBalance();
    }
}
