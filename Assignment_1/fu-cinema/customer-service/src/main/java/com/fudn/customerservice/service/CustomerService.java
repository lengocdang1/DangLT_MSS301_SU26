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
        if (customerRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ApiException(HttpStatus.CONFLICT, "Email already in use");
        }
        Customer customer = Customer.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .customerName(request.customerName())
                .telephone(request.telephone())
                .customerBirthday(request.customerBirthday())
                .customerStatus(CustomerStatus.ACTIVE)
                .build();
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public CustomerResponse getProfile(Long userId) {
        return CustomerResponse.from(findById(userId));
    }

    public CustomerResponse updateProfile(Long userId, ProfileUpdateRequest request) {
        Customer customer = findById(userId);
        customer.setCustomerName(request.customerName());
        customer.setTelephone(request.telephone());
        if (request.customerBirthday() != null) customer.setCustomerBirthday(request.customerBirthday());
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public void changePassword(Long userId, ChangePasswordRequest request) {
        Customer customer = findById(userId);
        if (!passwordEncoder.matches(request.oldPassword(), customer.getPassword())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        }
        customer.setPassword(passwordEncoder.encode(request.newPassword()));
        customerRepository.save(customer);
    }

    // ===== F3: Admin CRUD =====

    public List<CustomerResponse> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return customerRepository.findAll().stream()
                    .map(CustomerResponse::from).toList();
        }
        return customerRepository
                .findByCustomerNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByCustomerIdAsc(keyword, keyword)
                .stream().map(CustomerResponse::from).toList();
    }

    public CustomerResponse getById(Long id) {
        return CustomerResponse.from(findById(id));
    }

    public CustomerResponse create(AdminCustomerRequest request) {
        if (customerRepository.existsByEmailIgnoreCase(request.email())) {
            throw new ApiException(HttpStatus.CONFLICT, "Email already in use");
        }
        Customer customer = Customer.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password() != null ? request.password() : "changeme"))
                .customerName(request.customerName())
                .telephone(request.telephone())
                .customerBirthday(request.customerBirthday())
                .customerStatus(request.customerStatus() != null ? request.customerStatus() : CustomerStatus.ACTIVE)
                .build();
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public CustomerResponse update(Long id, AdminCustomerRequest request) {
        Customer customer = findById(id);
        customer.setCustomerName(request.customerName());
        customer.setTelephone(request.telephone());
        if (request.customerBirthday() != null) customer.setCustomerBirthday(request.customerBirthday());
        if (request.customerStatus() != null) customer.setCustomerStatus(request.customerStatus());
        return CustomerResponse.from(customerRepository.save(customer));
    }

    public void delete(Long id) {
        Customer customer = findById(id);
        customer.setCustomerStatus(CustomerStatus.INACTIVE); // soft delete
        customerRepository.save(customer);
    }

    private Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Customer not found"));
    }
}
