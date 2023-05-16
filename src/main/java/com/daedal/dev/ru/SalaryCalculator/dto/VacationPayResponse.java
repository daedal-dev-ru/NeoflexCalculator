package com.daedal.dev.ru.SalaryCalculator.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Data
@Builder
public class VacationPayResponse {
    private String message;
    private HttpStatus httpStatus;
}