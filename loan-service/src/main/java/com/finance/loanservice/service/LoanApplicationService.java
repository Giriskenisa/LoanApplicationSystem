package com.finance.loanservice.service;

import com.finance.loanservice.dto.LoanApplicationRequestDto;
import com.finance.loanservice.dto.LoanApplicationResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoanApplicationService {
        LoanApplicationResponseDto save(@RequestBody LoanApplicationRequestDto creditApplicationRequest) throws Exception;
    }
