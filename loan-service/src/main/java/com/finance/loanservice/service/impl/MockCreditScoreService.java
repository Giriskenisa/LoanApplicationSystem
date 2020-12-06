package com.finance.loanservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class MockCreditScoreService {

    public int calculateCreditScore(String TCKimlikNo){
        return ThreadLocalRandom.current().nextInt(0, 1900 + 1);
    }

}
