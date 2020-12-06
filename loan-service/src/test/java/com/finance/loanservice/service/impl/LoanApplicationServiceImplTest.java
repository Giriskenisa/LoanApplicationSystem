package com.finance.loanservice.service.impl;

import com.finance.loanservice.dto.LoanApplicationRequestDto;
import com.finance.loanservice.dto.LoanApplicationResponseDto;
import com.finance.loanservice.entity.LoanApplication;
import com.finance.loanservice.repository.LoanApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanApplicationServiceImplTest {

    @InjectMocks
    private LoanApplicationServiceImpl loanApplicationService;
    @Mock
    private  LoanApplicationRepository loanApplicationRepository;
    @Mock
    private NotificationProducer notificationService;
    @Mock
    private MockCreditScoreService mockCreditScoreService;

    @Nested
    class runWithSameDto {
        LoanApplicationRequestDto dto;
        @BeforeEach
        public void loanApplicationRequestDtoBuilder() {
            dto = new LoanApplicationRequestDto();
            dto.setName("test-user-name");
            dto.setLastName("test-user-last-name");
            dto.setMonthlyIncome(new BigDecimal(Math.random()*25000));
            dto.setPhone("5422222323");
            dto.setTckn("12345678912");
        }

        @Test
        public void testSave() throws Exception {
            LoanApplication loanMock = mock(LoanApplication.class);
            int mockCreditSkore = ThreadLocalRandom.current().nextInt(0, 1900 + 1);
            when(loanApplicationRepository.save(ArgumentMatchers.any(LoanApplication.class))).thenReturn(loanMock);
            when(mockCreditScoreService.calculateCreditScore(ArgumentMatchers.any(String.class))).thenReturn(mockCreditSkore);

            LoanApplicationResponseDto save = loanApplicationService.save(dto);

            assertEquals(save.getName(),dto.getName());
            if(save.getLimit().compareTo(new BigDecimal(0)) > 0) {
                assertEquals(save.isStatus(),true);
            } else {
                assertEquals(save.isStatus(),false);
            }
        }

        @Test
        public void testSaveWhenCreditScoreLessThen500() throws Exception{
            LoanApplication loanMock = mock(LoanApplication.class);
            int mockCreditSkore = 250;
            when(loanApplicationRepository.save(ArgumentMatchers.any(LoanApplication.class))).thenReturn(loanMock);
            when(mockCreditScoreService.calculateCreditScore(ArgumentMatchers.any(String.class))).thenReturn(mockCreditSkore);

            LoanApplicationResponseDto save = loanApplicationService.save(dto);

            assertEquals(save.getName(),dto.getName());
            assertEquals(save.getLimit(),new BigDecimal(0));
            assertEquals(save.isStatus(), false);
        }
    }

    @Test
   public void when_Credit_Score_Between_500and1000_And_Monthly_Income_Less_Than_5000() throws Exception{
       LoanApplicationRequestDto dto = new LoanApplicationRequestDto();
       dto.setName("test-user-name");
       dto.setLastName("test-user-last-name");
       dto.setMonthlyIncome(new BigDecimal(2500));
       dto.setPhone("5422222323");
       dto.setTckn("12345678912");
       LoanApplication loanMock = mock(LoanApplication.class);
       int mockCreditSkore = 750;
       when(loanApplicationRepository.save(ArgumentMatchers.any(LoanApplication.class))).thenReturn(loanMock);
       when(mockCreditScoreService.calculateCreditScore(ArgumentMatchers.any(String.class))).thenReturn(mockCreditSkore);

       LoanApplicationResponseDto save = loanApplicationService.save(dto);

       assertEquals(save.getName(),dto.getName());
       assertEquals(save.getLimit(),new BigDecimal(10000));
       assertEquals(save.isStatus(), true);
   }

    @Test
    public void when_Credit_Score_Between_500and1000_And_Monthly_Income_Greater_Than_5000() throws Exception{
        LoanApplicationRequestDto dto = new LoanApplicationRequestDto();
        dto.setName("test-user-name");
        dto.setLastName("test-user-last-name");
        dto.setMonthlyIncome(new BigDecimal(7500));
        dto.setPhone("5422222323");
        dto.setTckn("12345678912");
        LoanApplication loanMock = mock(LoanApplication.class);
        int mockCreditSkore = 750;
        when(loanApplicationRepository.save(ArgumentMatchers.any(LoanApplication.class))).thenReturn(loanMock);
        when(mockCreditScoreService.calculateCreditScore(ArgumentMatchers.any(String.class))).thenReturn(mockCreditSkore);

        LoanApplicationResponseDto save = loanApplicationService.save(dto);

        assertEquals(save.getName(),dto.getName());
        assertEquals(save.getLimit(),new BigDecimal(0));
        assertEquals(save.isStatus(), false);
    }

    @Test
    public void when_Credit_Score_Greate_Equal_1000() throws Exception{
        LoanApplicationRequestDto dto = new LoanApplicationRequestDto();
        dto.setName("test-user-name");
        dto.setLastName("test-user-last-name");
        dto.setMonthlyIncome(new BigDecimal(7500));
        dto.setPhone("5422222323");
        dto.setTckn("12345678912");
        LoanApplication loanMock = mock(LoanApplication.class);
        int mockCreditSkore = 1250;
        when(loanApplicationRepository.save(ArgumentMatchers.any(LoanApplication.class))).thenReturn(loanMock);
        when(mockCreditScoreService.calculateCreditScore(ArgumentMatchers.any(String.class))).thenReturn(mockCreditSkore);

        LoanApplicationResponseDto save = loanApplicationService.save(dto);

        assertEquals(save.getName(),dto.getName());
        assertEquals(save.getLimit(),new BigDecimal(30000));
        assertEquals(save.isStatus(), true);
    }

}
