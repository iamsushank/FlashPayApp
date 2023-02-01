package com.masai.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.model.BankAccount;

@Repository
public interface BankAccountDao extends JpaRepository<BankAccount, String>{
	Optional<BankAccount> findByAccountNumber(String accountNumber);

	BankAccount findByWalletId(Integer walletId);

	List<BankAccount> findAllByWalletId(Integer walletId);
    
}
