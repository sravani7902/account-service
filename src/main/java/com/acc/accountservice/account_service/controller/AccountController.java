package com.acc.accountservice.account_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
     public ResponseEntity<List<Account>> getAll(){
    	 return ResponseEntity.ok(service.getAll());
     }
     
     @GetMapping("/{accNum}")
     public ResponseEntity<Account> get(@PathVariable Long accNum) {
    	 Account acc = service.getByAccNum(accNum);
    	 return acc != null ? ResponseEntity.ok(acc) : ResponseEntity.notFound().build();
     }
     
     @PostMapping
     public ResponseEntity<Account> create(@RequestBody Account acc) {
    	 Account saved = service.save(acc);
    	 return ResponseEntity.ok(saved);
     }
     
     @PutMapping("/{accNum}")
     public ResponseEntity<Account> update(@PathVariable Long accNum, @RequestBody Account acc) {
         acc.setAccNum(accNum);
         Account updated = service.save(acc);
         return ResponseEntity.ok(updated);
     }
     
     @DeleteMapping("/{accNum}")
     public ResponseEntity<Void> delete(@PathVariable Long accNum) {
         service.delete(accNum);
         return ResponseEntity.noContent().build();
     }     
}
