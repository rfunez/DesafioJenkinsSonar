package com.example.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    void testInitialBalance() {
        BankAccount account = new BankAccount(100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testDeposit() {
        BankAccount account = new BankAccount(60.0);
        account.deposit(25.0);
        assertEquals(75.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        BankAccount account = new BankAccount(100.0);
        account.withdraw(30.0);
        assertEquals(70.0, account.getBalance());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        BankAccount account = new BankAccount(20.0);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(30.0);
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void testHasSufficientFunds() {
        BankAccount account = new BankAccount(200.0);
        assertTrue(account.hasSufficientFunds(100.0));
        assertFalse(account.hasSufficientFunds(300.0));
    }
}
