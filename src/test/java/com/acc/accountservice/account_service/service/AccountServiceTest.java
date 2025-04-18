package com.acc.accountservice.account_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acc.accountservice.account_service.model.Account;
import com.acc.accountservice.account_service.repository.AccountRepository;

public class AccountServiceTest {
	
	@Mock
	private AccountRepository repo;
	
	@InjectMocks
	private AccountService service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testGetAll() {
		Account acc1 = new Account(10001L, "John", "savings", 1000, "Active");
		Account acc2 = new Account(10002L, "John", "savings", 1000, "Active");
		when(repo.findAll()).thenReturn(Arrays.asList(acc1,acc2));
		List<Account> list = service.getAll();
		assertEquals(2, list.size());
	}
	
	@Test
	void testGetByAccNum_Success() {
		Account acc1 = new Account(10001L, "John", "savings", 1000, "Active");
		when(repo.findById(10001L)).thenReturn(Optional.of(acc1));
		Account result = service.getByAccNum(10001L);
		assertEquals("John", result.getName());
		
	}
	
	@Test
	void testGetByAccNum_Null() {
		Account acc = new Account(10001L, "John", "savings", 1000, "Active");
		acc.setAccNum(null);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.getByAccNum(acc.getAccNum()));
		assertTrue(ex.getMessage().contains("Account number cannot be null"));
	}
	
	@Test
	void testGetByAccNum_NotFound() {
		when(repo.findById(99L)).thenReturn(Optional.empty());
		RuntimeException ex = assertThrows(RuntimeException.class, 
				() -> service.getByAccNum(99L));
		assertTrue(ex.getMessage().contains("Account not found"));	
	}
	
	@Test
	void testSave_ValidAccount() {
		Account acc = new Account(10001L, "John", "savings", 1000, "Active");
		when(repo.save(acc)).thenReturn(acc);
		Account result = service.save(acc);
		assertEquals(1000, result.getBalance());
	}
	
	@Test
	void testSave_NullAccount() {
		Account acc = null;
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.save(acc));
		assertTrue(ex.getMessage().contains("Account cannot be null"));
	}
	
	@Test
	void testSave_NullAccountNum() {
		Account acc = new Account(10001L, "John", "savings", 1000, "Active");
		acc.setAccNum(null);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.save(acc));
		assertTrue(ex.getMessage().contains("Account number cannot be null"));
	}
	
	@Test
	void testSave_GetNegativeBalance() {
		Account acc = new Account(10001L, "John", "savings", 1000, "Active");
		acc.setBalance((double) -1000L);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.save(acc));
		assertTrue(ex.getMessage().contains("Balance cannot be negative"));
	}
	
	@Test
	void testDelete_NullAccNum() {
		Account acc = new Account(10001L, "John", "savings", 1000, "Active");
		acc.setAccNum(null);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.delete(acc.getAccNum()));
		assertTrue(ex.getMessage().contains("Account number cannot be null"));
		
	}
	
	@Test
	void testDelete_Success() {
		when(repo.existsById(10001L)).thenReturn(true);
		doNothing().when(repo).deleteById(10001L);
		service.delete(10001L);
		verify(repo, times(1)).deleteById(10001L);
	}
	
	@Test
	void testDelete_NotFound() {
		when(repo.existsById(10001L)).thenReturn(false);
		assertThrows(RuntimeException.class,
				() -> service.delete(10001L));
	}
	
	@Test
	void testDeleteAccountNull() {
		Account acc = null;
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.deleteAccount(acc));
		assertTrue(ex.getMessage().contains("Account or account number cannot be null"));
	}
	
	@Test
	void testDeleteAccountNumNull() {
		Account acc = new Account(10001L, "John", "savings", 1000, "Active");
		acc.setAccNum(null);
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> service.deleteAccount(acc));
		assertTrue(ex.getMessage().contains("Account or account number cannot be null"));
	}
	
	@Test
	void test_DeleteAccount() {
		Account acc = new Account(10001L, "John", "savings", 1000, "Active");
		doNothing().when(repo).delete(acc);
		service.deleteAccount(acc);
		verify(repo, times(1)).delete(acc);
	}

}
