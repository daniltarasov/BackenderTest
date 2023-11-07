package ru.aston.backend_developer_practical_test.controllers;


import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.backend_developer_practical_test.dto.DepositDTO;
import ru.aston.backend_developer_practical_test.dto.TransactionDTO;
import ru.aston.backend_developer_practical_test.dto.TransferDTO;
import ru.aston.backend_developer_practical_test.dto.WithdrawDTO;
import ru.aston.backend_developer_practical_test.services.interfaces.TransactionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Api(tags = "Transaction REST")
@Tag(name = "Transaction REST", description = "API операции с переводом средств")
public class TransactionController {

    public final TransactionService transactionService;

    @ApiOperation(value = "Получить список всех транзакций по счету.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список транзакций создан"),
            @ApiResponse(code = 404, message = "Счет не найден"),
    })
    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsForAccount(
            @ApiParam(value = "UUID номер счета", required = true)
            @PathVariable UUID accountNumber) {

        return new ResponseEntity<>(transactionService.retrieveTransactionsForAccount(accountNumber),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Внести средства на счет.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Операция успешно проведена"),
            @ApiResponse(code = 404, message = "Счет не найден"),
    })
    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> depositFunds(@RequestBody DepositDTO depositDTO) {

        return new ResponseEntity<>(transactionService.deposit(depositDTO),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Снять средства со счета.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Операция успешно проведена"),
            @ApiResponse(code = 400, message = "Недостаточно средств на счете или неверный PIN-код"),
            @ApiResponse(code = 404, message = "Счет не найден"),
    })
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdrawFunds(@RequestBody WithdrawDTO withdrawDTO) {

        return new ResponseEntity<>(transactionService.withdraw(withdrawDTO),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Перевести средства между счетами.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Операция успешно проведена"),
            @ApiResponse(code = 400, message = "Недостаточно средств на счете или неверный PIN-код"),
            @ApiResponse(code = 404, message = "Счет не найден"),
    })
    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transferFunds(@RequestBody TransferDTO transferDTO) {

        return new ResponseEntity<>(transactionService.transfer(transferDTO),
                HttpStatus.OK);
    }
}