package ru.aston.backend_developer_practical_test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.backend_developer_practical_test.entities.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

}
