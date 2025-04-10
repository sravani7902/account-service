package com.acc.accountservice.account_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acc.accountservice.account_service.model.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {
   
}
