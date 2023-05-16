package com.daedal.dev.ru.SalaryCalculator.controller;

import com.daedal.dev.ru.SalaryCalculator.dto.VacationPayResponse;
import com.daedal.dev.ru.SalaryCalculator.exception.InvalidSalaryAmountException;
import com.daedal.dev.ru.SalaryCalculator.exception.InvalidVacationDaysException;
import com.daedal.dev.ru.SalaryCalculator.service.VacationPayCalculatorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
public class VacationPayController {
    @Autowired
    VacationPayCalculatorServiceImpl vacationPayCalculator;

    @GetMapping("/calculate")
    public ResponseEntity<String> getVacationRequest(
            @RequestParam("averageSalary") BigDecimal averageSalary,
            @RequestParam("vacationDays") int vacationDays
    ) {
        log.trace("Received request with parameters" +
                "averageSalary = " + averageSalary + " | vacationDays = " + vacationDays);
        try {
            VacationPayResponse vacationPayResponse = vacationPayCalculator.getVacationPayCalculate(averageSalary, vacationDays);
            return new ResponseEntity<>(vacationPayResponse.getMessage(),
                                        vacationPayResponse.getHttpStatus());
        } catch (InvalidSalaryAmountException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Средняя годовая зарплата указана в неправильном формате, или она меньше/равна нулю!", HttpStatus.BAD_REQUEST);
        } catch (InvalidVacationDaysException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Количество дней отпуска должно быть больше нуля.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Произошла непредвиденная ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
