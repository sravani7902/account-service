package com.acc.accountservice.account_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.acc.accountservice.account_service.model.Account;
import com.acc.accountservice.account_service.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public List<Account> getAll() {
        return repo.findAll();
    }

    public Account getByAccNum(Long accNum) {
        if (accNum == null) {
            throw new IllegalArgumentException("Account number cannot be null");
        }
        return repo.findById(accNum)
                   .orElseThrow(() -> new RuntimeException("Account not found with accNum: " + accNum));
    }

    public Account save(Account acc) {
        if (acc == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (acc.getAccNum() == null) {
            throw new IllegalArgumentException("Account number cannot be null");
        }
        if (acc.getBalance() < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        return repo.save(acc);
    }

    public void delete(Long accNum) {
        if (accNum == null) {
            throw new IllegalArgumentException("Account number cannot be null");
        }
        if (!repo.existsById(accNum)) {
            throw new RuntimeException("Account not found with accNum: " + accNum);
        }
        repo.deleteById(accNum);
    }

    public void deleteAccount(Account acc) {
        if (acc == null || acc.getAccNum() == null) {
            throw new IllegalArgumentException("Account or account number cannot be null");
        }
        repo.delete(acc);
    }
}
