package com.finance.notificationservice.model;

import com.finance.notificationservice.model.enums.NotificationType;
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