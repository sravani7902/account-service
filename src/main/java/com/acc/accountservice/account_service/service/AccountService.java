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
      
      public List<Account> getAll(){
    	  return repo.findAll();
      }
      
      public Account getByAccNum(Long accNum) {
    	  return repo.findById(accNum).orElse(null);
      }
      
      public Account save(Account acc) {
    	 return  repo.save(acc);
      }
      
      public void delete(Long accNum) {
    	  repo.deleteById(accNum);
      }
      
      public void deleteAccount(Account acc) {
    	  repo.delete(acc);
      }
}
