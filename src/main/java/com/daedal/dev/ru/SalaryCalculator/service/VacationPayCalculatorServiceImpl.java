package com.daedal.dev.ru.SalaryCalculator.service;

import com.daedal.dev.ru.SalaryCalculator.dto.VacationPayResponse;
import com.daedal.dev.ru.SalaryCalculator.exception.InvalidSalaryAmountException;
import com.daedal.dev.ru.SalaryCalculator.exception.InvalidVacationDaysException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class VacationPayCalculatorServiceImpl implements VacationPayCalculatorService {

    @Override
    public VacationPayResponse getVacationPayCalculate(BigDecimal averageSalary, int vacationDays) {
        if (averageSalary == null) {
            throw new InvalidSalaryAmountException();
        }
        if (averageSalary.intValue() <= 0) {
            throw new InvalidSalaryAmountException();
        }
        if (vacationDays <= 0) {
            throw new InvalidVacationDaysException();
        }

        // Ежемесячная зарплата
        BigDecimal monthSalary = averageSalary.divide(BigDecimal.valueOf(12), RoundingMode.HALF_EVEN);
        log.info("Ежемесячная зарплата: " + monthSalary);

        // Средняя ежемесячная зарплата
        BigDecimal averageMonthSalary = monthSalary.divide(BigDecimal.valueOf(29.3), RoundingMode.HALF_EVEN);
        log.info("Средняя ежемесячная зарплата: " + averageMonthSalary);

        // Сумма отпускных
        BigDecimal vacationPay = averageMonthSalary.multiply(BigDecimal.valueOf(vacationDays));
        log.info("Сумма отпускных: " + vacationPay);

        return VacationPayResponse.builder()
                .message("Ваша сумма отпускных: " + vacationPay)
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
