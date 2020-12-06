package com.finance.loanservice.controller;

import com.finance.loanservice.dto.LoanApplicationRequestDto;
import com.finance.loanservice.dto.LoanApplicationResponseDto;
import com.finance.loanservice.service.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    @PostMapping("/create-application")
    public ResponseEntity<Object> save(
            @Valid @RequestBody LoanApplicationRequestDto creditApplication, BindingResult bindingResult) throws Exception {
        LoanApplicationResponseDto response = null;
        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors.get(0),HttpStatus.EXPECTATION_FAILED);
        }
        try{
            response = loanApplicationService.save(creditApplication);
            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            throw new Exception(e);
        }
    }

}
