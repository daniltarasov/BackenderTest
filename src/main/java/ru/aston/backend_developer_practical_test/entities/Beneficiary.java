package ru.aston.backend_developer_practical_test.entities;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "beneficiaries")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Beneficiary {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Pattern(regexp = "^\\d{4}$", message = "Pin-код должен состоять из 4 цифр")
    private String pinCode;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.REMOVE)          //TODO Cascade
    private List<Account> accounts;

}