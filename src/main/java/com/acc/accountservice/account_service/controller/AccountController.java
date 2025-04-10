package com.acc.accountservice.account_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.acc.accountservice.account_service.model.Account;
import com.acc.accountservice.account_service.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
     private final AccountService service;
     
     public AccountController(AccountService service) {
    	 this.service = service;
     }
     
     @GetMapping
     public List<Account> getAll(){
    	 return service.getAll();
     }
     
     @GetMapping("/{id}")
     public Account get(@PathVariable Long id) {
    	 return service.getById(id);
     }
     
     @PostMapping
     public Account create(@RequestBody Account acc) {
    	 return service.save(acc);
     }
     
     @PutMapping("/{id}")
     public Account update(@PathVariable Long id, @RequestBody Account acc) {
    	 acc.setId(id);
    	 return service.save(acc);
     }
     
     @DeleteMapping("/{id}")
     public void delete(@PathVariable Long id) {
         service.delete(id);
     }
     
}
