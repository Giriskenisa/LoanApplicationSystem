package com.finance.loanservice.dto;


import com.finance.loanservice.dto.enums.NotificationType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoanNotification {

        private String notificationId;
        private String message;
        private String phoneNumber;
        private NotificationType notificationType;

    }