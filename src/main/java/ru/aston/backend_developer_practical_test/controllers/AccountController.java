package ru.aston.backend_developer_practical_test.controllers;


import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.backend_developer_practical_test.dto.*;
import ru.aston.backend_developer_practical_test.services.interfaces.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Api(tags = "Account REST")
@Tag(name = "Account REST", description = "API операции с созданием и получением счетов")
public class AccountController {

    private final AccountService accountService;

    @ApiOperation(value = "Создать новый счет")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Счет успешно создан"),
            @ApiResponse(code = 400, message = "Ошибка в исходных данных (неверный PIN-код)"),
            @ApiResponse(code = 404, message = "Владелец счета не найден"),
    })
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(
            @ApiParam(value = "Владелец счета.", required = true)
            @RequestBody BeneficiaryDTO beneficiaryDTO) {
        return new ResponseEntity<>(accountService.createAccount(beneficiaryDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Получить список всех счетов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список успешно получен"),
    })
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }
}