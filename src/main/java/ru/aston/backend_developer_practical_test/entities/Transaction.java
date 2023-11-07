package ru.aston.backend_developer_practical_test.entities;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.aston.backend_developer_practical_test.enums.OperationType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "from_account_id", referencedColumnName = "id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id", referencedColumnName = "id")
    private Account toAccount;

    private BigDecimal transactionAmount;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    private LocalDateTime operationTime;

}
