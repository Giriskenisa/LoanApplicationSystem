package com.finance.loanservice.repository;

import com.finance.loanservice.entity.LoanApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanApplicationRepository extends MongoRepository<LoanApplication,String> {
}
