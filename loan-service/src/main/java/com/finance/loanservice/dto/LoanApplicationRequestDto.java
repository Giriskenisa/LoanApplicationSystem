package com.finance.loanservice.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoanApplicationRequestDto {
    @NotNull(message = "İsim Boş Geçilemez")
    @NotEmpty(message = "İsim Boş Geçilemez")
    private String name;
    @NotNull(message = "Soyisim Boş Geçilemez")
    @NotEmpty(message = "Soyisim Boş Geçilemez")
    private String lastName;
    @NotNull(message = "TC No Boş Geçilemez")
    @NotEmpty(message = "TC No Boş Geçilemez")
    private String tckn;
    @NotNull(message = "Aylık Gelir Zorunludur.")
    private BigDecimal monthlyIncome;
    @NotNull(message = "Telefon No Boş Geçilemez")
    @NotEmpty(message = "Telefon No Boş Geçilemez")
    private String phone;
}
