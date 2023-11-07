package ru.aston.backend_developer_practical_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.backend_developer_practical_test.entities.Beneficiary;

import java.util.Optional;
import java.util.UUID;

public interface BeneficiaryRepository extends JpaRepository <Beneficiary, UUID> {

    Optional<Beneficiary> findByName(String name);
}
