package com.fudn.customerservice.service;

import com.fudn.customerservice.dto.*;
import com.fudn.customerservice.exception.ApiException;
import com.fudn.customerservice.model.Customer;
import com.fudn.customerservice.model.CustomerStatus;
import com.fudn.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    // ===== F2: Customer profile =====

    public CustomerResponse register(RegisterRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new ApiException(HttpStatus.CONFLICT, "Email already in use");
        }
        Customer customer = Customer.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .phone(request.phone())
                .role("CUSTOMER")
                .status(CustomerStatus.ACTIVE)
                .build();
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public CustomerResponse getProfile(Long userId) {
        return CustomerResponse.from(findById(userId));
    }

    public CustomerResponse updateProfile(Long userId, ProfileUpdateRequest request) {
        Customer customer = findById(userId);
        customer.setFullName(request.fullName());
        customer.setPhone(request.phone());
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public void changePassword(Long userId, ChangePasswordRequest request) {
        Customer customer = findById(userId);
        if (!passwordEncoder.matches(request.currentPassword(), customer.getPassword())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }
        customer.setPassword(passwordEncoder.encode(request.newPassword()));
        customerRepository.save(customer);
    }

    // ===== F3: Admin CRUD =====

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::from).toList();
    }

    public CustomerResponse adminCreate(AdminCustomerRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new ApiException(HttpStatus.CONFLICT, "Email already in use");
        }
        Customer customer = Customer.builder()
                .email(request.email())
                .password(passwordEncoder.encode("changeme"))
                .fullName(request.fullName())
                .phone(request.phone())
                .role("CUSTOMER")
                .status(request.status() != null ? request.status() : CustomerStatus.ACTIVE)
                .build();
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public CustomerResponse adminUpdate(Long id, AdminCustomerRequest request) {
        Customer customer = findById(id);
        customer.setFullName(request.fullName());
        customer.setPhone(request.phone());
        if (request.status() != null) customer.setStatus(request.status());
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public void adminDelete(Long id) {
        Customer customer = findById(id);
        customer.setStatus(CustomerStatus.INACTIVE); // soft delete
        customerRepository.save(customer);
    }

    private Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Customer not found"));
    }
}
