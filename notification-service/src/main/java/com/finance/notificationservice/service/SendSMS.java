package com.finance.notificationservice.service;

import com.finance.notificationservice.model.LoanNotification;
import org.springframework.stereotype.Service;

@Service
public class SendSMS {

    public boolean sendSMS(LoanNotification loanNotification) {
        // Sending sms
        System.out.println("SMS has been sent");
        return true;
    }
}
