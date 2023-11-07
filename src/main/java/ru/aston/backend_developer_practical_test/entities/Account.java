package ru.aston.backend_developer_practical_test.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @EqualsAndHashCode.Exclude
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "beneficiaries_id", referencedColumnName = "id")
    private Beneficiary beneficiary;


    public Account(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
        this.balance = BigDecimal.valueOf(0);
    }
}
