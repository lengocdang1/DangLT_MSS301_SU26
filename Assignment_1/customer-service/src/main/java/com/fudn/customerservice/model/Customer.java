package com.fudn.customerservice.model;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "telephone", length = 15)
    private String phone;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "customer_birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_status", nullable = false, length = 20)
    private CustomerStatus status;

    @Column(nullable = false, length = 100)
    private String password;

    public String getRole() {
        return "CUSTOMER";
    }
}
