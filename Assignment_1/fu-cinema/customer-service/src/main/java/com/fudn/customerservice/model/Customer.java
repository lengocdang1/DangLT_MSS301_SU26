package com.fudn.customerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, length = 100)
    private String customerName;

    private String telephone;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    private LocalDate customerBirthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CustomerStatus customerStatus;

    @Column(nullable = false)
    private String password;

    // Helper aliases for DTO and service compatibility
    public Long getId() {
        return customerId;
    }

    public void setId(Long id) {
        this.customerId = id;
    }

    public String getFullName() {
        return customerName;
    }

    public void setFullName(String fullName) {
        this.customerName = fullName;
    }

    public String getPhone() {
        return telephone;
    }

    public void setPhone(String phone) {
        this.telephone = phone;
    }

    public CustomerStatus getStatus() {
        return customerStatus;
    }

    public void setStatus(CustomerStatus status) {
        this.customerStatus = status;
    }

    public String getRole() {
        return "CUSTOMER";
    }
}
