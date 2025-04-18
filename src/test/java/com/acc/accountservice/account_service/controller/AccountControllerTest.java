package com.acc.accountservice.account_service.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.acc.accountservice.account_service.model.Account;
import com.acc.accountservice.account_service.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

    private Account acc1;
    private Account acc2;

    @BeforeEach
    void setUp() {
        acc1 = new Account(1001L, "John", "Savings", 1000.0, "Active");
        acc2 = new Account(1002L, "Alice", "Current", 2000.0, "Active");
    }

    @Test
    void testGetAll() throws Exception {
        List<Account> accounts = Arrays.asList(acc1, acc2);
        when(service.getAll()).thenReturn(accounts);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void testGetByAccNum_found() throws Exception {
        when(service.getByAccNum(1001L)).thenReturn(acc1);

        mockMvc.perform(get("/accounts/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    void testGetByAccNum_notFound() throws Exception {
        when(service.getByAccNum(99L)).thenReturn(null);

        mockMvc.perform(get("/accounts/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateAccount() throws Exception {
    	when(service.save((Account) any())).thenReturn(acc1);
        mockMvc.perform(post("/accounts")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(acc1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accNum", is(1001)));
    }

    @Test
    void testUpdateAccount() throws Exception {
        acc1.setBalance(1500.0);
        when(service.save((Account) any())).thenReturn(acc1);

        mockMvc.perform(put("/accounts/1001")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(acc1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is(1500.0)));
    }

    @Test
    void testDeleteAccount() throws Exception {
        doNothing().when(service).delete(1001L);

        mockMvc.perform(delete("/accounts/1001"))
                .andExpect(status().isNoContent());
    }
}
