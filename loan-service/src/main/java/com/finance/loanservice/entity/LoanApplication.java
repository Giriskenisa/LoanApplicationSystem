package com.finance.loanservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "loan-application")
public class LoanApplication {
    @Id
    private String id;
    @NotNull(message = "İsim Alanı Boş Geçilemez")
    @NotEmpty(message = "İsim Alanı Boş Geçilemez")
    private String name;
    @NotNull(message = "Soyisim Alanı Boş Geçilemez")
    @NotEmpty(message = "Soyisim Alanı Boş Geçilemez")
    private String lastName;
    @NotNull(message = "T.C No Alanı Boş Geçilemez")
    @NotEmpty(message = "T.C No Alanı Boş Geçilemez")
    private String tckn;
    private BigDecimal monthlyIncome;
    @NotNull(message = "Telefon Alanı Boş Geçilemez")
    @NotEmpty(message = "Telefon Alanı Boş Geçilemez")
    private String phone;
    private boolean status;
    private BigDecimal limit;
}
