package com.finance.loanservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationResponseDto {
    private String name;
    private String lastName;
    private String tckn;
    private BigDecimal monthlyIncome;
    private String phone;
    private boolean status;
    private BigDecimal limit;
    private String message;
    private String errorMessage;
}
