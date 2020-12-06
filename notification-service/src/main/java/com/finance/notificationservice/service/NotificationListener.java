package com.finance.notificationservice.service;

import com.finance.notificationservice.model.LoanNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationListener {

    private final SendSMS sendSMS;

    @RabbitListener(queues = "finance-queue")
    public void handleMessage(LoanNotification loanNotification) {
        System.out.println("Message received..");
        System.out.println(loanNotification.toString());
        sendSMS.sendSMS((loanNotification));
    }
}