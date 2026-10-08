package com.fudn.customerservice.service;

import com.fudn.customerservice.dto.LoginRequest;
import com.fudn.customerservice.dto.LoginResponse;
import com.fudn.customerservice.exception.ApiException;
import com.fudn.customerservice.model.Customer;
import com.fudn.customerservice.model.CustomerStatus;
import com.fudn.customerservice.repository.CustomerRepository;
import com.fudn.customerservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    public LoginResponse login(LoginRequest request) {
        // Check if admin login
        if (adminEmail.equals(request.email())) {
            if (!adminPassword.equals(request.password())) {
                throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid admin credentials");
            }
            String token = jwtService.generateToken(0L, adminEmail, "ADMIN");
            return new LoginResponse(token);
        }

        // Customer login
        Customer customer = customerRepository.findByEmail(request.email())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), customer.getPassword())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        if (customer.getStatus() == CustomerStatus.INACTIVE) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Account is inactive");
        }

        String token = jwtService.generateToken(customer.getId(), customer.getEmail(), customer.getRole());
        return new LoginResponse(token);
    }
}
