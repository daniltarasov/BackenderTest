package ru.aston.backend_developer_practical_test.controllers;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.backend_developer_practical_test.dto.BeneficiaryDTO;
import ru.aston.backend_developer_practical_test.entities.Beneficiary;
import ru.aston.backend_developer_practical_test.services.interfaces.BeneficiaryService;

import java.util.List;


@RestController
@RequestMapping("/api/beneficiaries")
@RequiredArgsConstructor
@Api(tags = "Beneficiary REST")
@Tag(name = "Beneficiary REST", description = "API операции с созданием и получением клиента")
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;


    @ApiOperation(value = "Создать нового клиента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Клиент успешно создан"),
            @ApiResponse(code = 400, message = "Неправильный формат PIN-кода"),

    })
    @PostMapping
    public ResponseEntity<BeneficiaryDTO> create(
            @ApiParam(value = "Создать владельца. Заполнять только name и pin, остальное удалить", required = true)
            @RequestBody Beneficiary beneficiary) {
        beneficiaryService.saveBeneficiary(beneficiary);
        return new ResponseEntity<>(beneficiaryService.saveBeneficiary(beneficiary), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Получить список всех коиентов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список клиентов успешно получен"),
    })
    @GetMapping
    public ResponseEntity<List<BeneficiaryDTO>> getAllBeneficiaries() {

        return new ResponseEntity<>(beneficiaryService.getAllBeneficiaries(), HttpStatus.OK);
    }
}
