package com.fudn.paymentservice.repository;

import com.fudn.paymentservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {
}
