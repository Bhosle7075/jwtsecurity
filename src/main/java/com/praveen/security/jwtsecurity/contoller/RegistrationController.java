package com.praveen.security.jwtsecurity.contoller;

import com.praveen.security.jwtsecurity.dto.RegisterationRequest;
import com.praveen.security.jwtsecurity.model.Customer;
import com.praveen.security.jwtsecurity.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@Slf4j
public class RegistrationController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/user")
    public ResponseEntity<String> register(@RequestBody RegisterationRequest request){
        log.info("Start : RegistrationController ---> register ");
        ResponseEntity response ;
        try {
            Customer customer = Customer.builder()
                    .name(request.name())
                    .email(request.email())
                    .mobileNumber(request.mobileNumber())
                    .pwd(request.password())
                    .build();
            String encodedPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(encodedPwd);
            customerRepository.save(customer);
            response = ResponseEntity.status(HttpStatus.CREATED).body("Registration is Successful!!");
            log.info("End : RegistrationController ---> register ----> passed ");
        } catch (Exception ex) {
            log.info("Exception occurred while processing");
            log.error("Exception:{} ", ex.getMessage());
            // ex.printStackTrace();
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception Occurred");
            log.info("End : RegistrationController ---> register ----> failed ");
        }
        return response;
    }
}
