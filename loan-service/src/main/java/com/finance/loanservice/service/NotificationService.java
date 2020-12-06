package com.finance.loanservice.service;

import com.finance.loanservice.dto.LoanNotification;
import com.finance.loanservice.entity.LoanApplication;

public interface NotificationService {
    void sendToQueue(LoanNotification notification) ;
}
