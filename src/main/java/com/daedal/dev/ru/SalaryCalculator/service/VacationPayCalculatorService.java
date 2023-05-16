package com.daedal.dev.ru.SalaryCalculator.service;

import com.daedal.dev.ru.SalaryCalculator.dto.VacationPayResponse;

import java.math.BigDecimal;

public interface VacationPayCalculatorService {
    VacationPayResponse getVacationPayCalculate(BigDecimal averageSalary, int vacationDays);
}
