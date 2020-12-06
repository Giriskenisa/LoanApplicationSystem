package com.finance.loanservice.service.impl;

import com.finance.loanservice.dto.LoanApplicationRequestDto;
import com.finance.loanservice.dto.LoanApplicationResponseDto;
import com.finance.loanservice.dto.LoanNotification;
import com.finance.loanservice.dto.enums.NotificationType;
import com.finance.loanservice.entity.LoanApplication;
import com.finance.loanservice.repository.LoanApplicationRepository;
import com.finance.loanservice.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final NotificationProducer notificationService;
    private final MockCreditScoreService mockCreditScoreService; //singleton design pattern runtime boyunca tek instance çalışır ve değiştirilemez.(final)

    @Override
    @Transactional
    public LoanApplicationResponseDto save(@RequestBody LoanApplicationRequestDto creditApplicationRequest) throws Exception {
        LoanApplicationResponseDto creditApplicationResponse = new LoanApplicationResponseDto();
            try {
                int creditScore = mockCreditScoreService.calculateCreditScore(creditApplicationRequest.getTckn());

                int stat = creditScoreStat(creditScore);
                switch (stat){
                    case 0:
                        creditApplicationResponse.setStatus(false);
                        creditApplicationResponse.setLimit(BigDecimal.ZERO);
                        break;
                    case 1:
                        if (creditApplicationRequest.getMonthlyIncome().compareTo(BigDecimal.valueOf(5000))<0){
                            creditApplicationResponse.setLimit(BigDecimal.valueOf(10000));
                            creditApplicationResponse.setStatus(true);
                        }else{
                            // Aylık geliri 5000 nin üzerindeyse ve skoru 500-1000 olduğu durumda veya 500 e eşit olduğu durum için kural belirtilmemiş.
                            // Bu durum için başvuru reddi dönüşü yapıldı.
                            creditApplicationResponse.setLimit(BigDecimal.ZERO);
                            creditApplicationResponse.setStatus(false);
                        }
                        break;
                    case 2:
                        creditApplicationResponse.setLimit(creditApplicationRequest.getMonthlyIncome().multiply(BigDecimal.valueOf(4)));
                        creditApplicationResponse.setStatus(true);
                        break;
                    default:
                        creditApplicationResponse.setLimit(BigDecimal.ZERO);
                        creditApplicationResponse.setStatus(false);
                        break;
                }
                loanApplicationResponseDtoMapper(creditApplicationRequest,creditApplicationResponse);
                LoanApplication save = loanApplicationRepository.save(loanApplicationMapper(creditApplicationResponse));
                notificationService.sendToQueue(prepareNotification(save));
                creditApplicationResponse.setMessage(getMessage(save));
                return creditApplicationResponse;

            } catch (Exception e) {
                throw new Exception(e);
            }


    }

    private LoanNotification prepareNotification(LoanApplication application) {
        LoanNotification loan = new LoanNotification();
        loan.setNotificationId(UUID.randomUUID().toString());
        loan.setNotificationType(NotificationType.SMS);
        loan.setPhoneNumber(application.getPhone());
        loan.setMessage(getMessage(application));
        return loan;
    }

    private int creditScoreStat(int creditScore){
        int status = 0;
       if(creditScore>=500 && creditScore<1000){
           status = 1;
       }else if(creditScore >=1000){
           status = 2;
       }
       return status;
    }


    private LoanApplicationResponseDto loanApplicationResponseDtoMapper(LoanApplicationRequestDto requestDto, LoanApplicationResponseDto responseDto){
        responseDto.setTckn(requestDto.getTckn());
        responseDto.setPhone(requestDto.getPhone());
        responseDto.setMonthlyIncome(requestDto.getMonthlyIncome());
        responseDto.setName(requestDto.getName());
        responseDto.setLastName(requestDto.getLastName());
        return responseDto;
    }

    private LoanApplication loanApplicationMapper(LoanApplicationResponseDto responseDto) {
        LoanApplication loanApp = new LoanApplication();
        loanApp.setLastName(responseDto.getLastName());
        loanApp.setMonthlyIncome(responseDto.getMonthlyIncome());
        loanApp.setName(responseDto.getName());
        loanApp.setPhone(responseDto.getPhone());
        loanApp.setLimit(responseDto.getLimit());
        loanApp.setTckn(responseDto.getTckn());
        loanApp.setStatus(responseDto.isStatus());
        return loanApp;
    }

    private String getMessage(LoanApplication application) {
        StringBuilder message = new StringBuilder("Sayın ").append(application.getName()).append(" ").append(application.getLastName());
        if (application.isStatus()) {
            message.append(" kredi başvurunuz onaylanmıştır. Kredi limitiniz: ").append(application.getLimit()).append(" TL' dir. İyi günler dileriz.");
        } else {
            message.append(" kredi başvurunuz reddedilmiştir. İyi günler dileriz.");
        }
        return message.toString();
    }

}
