package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.entity.Account;
import me.noctambulist.aasweb.repository.IAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/21 16:15
 */
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTest {

    private static final List<Account> ACCOUNTS = Arrays.asList(
            new Account(1, 1L, "test1@example.com", "password1"),
            new Account(2, 2L, "test2@example.com", "password2"),
            new Account(3, 3L, "test3@example.com", "password3")
    );

    @Autowired
    private AccountService accountService;

    @MockBean
    private IAccount iAccount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Order(1)
    public void should_create_account() {
        Account account = new Account(null, 4L, "test4@example.com", "password4");
        when(iAccount.save(account)).thenReturn(account);

        Account result = accountService.create(account);

        assertEquals(result.getUniqueId(), account.getUniqueId());
        assertEquals(result.getEmail(), account.getEmail());
        assertEquals(result.getPassword(), account.getPassword());
    }

    @Test
    @Order(2)
    public void should_find_all_accounts() {
        when(iAccount.findAll()).thenReturn(ACCOUNTS);

        List<Account> result = accountService.findAll();

        assertEquals(result.size(), ACCOUNTS.size());
        assertTrue(result.containsAll(ACCOUNTS));
    }

    @Test
    @Order(3)
    public void should_find_by_id() {
        int id = 1;
        when(iAccount.findById(id)).thenReturn(Optional.ofNullable(ACCOUNTS.get(id - 1)));

        Account result = accountService.findById(id);

        assertEquals(result, ACCOUNTS.get(id - 1));
    }

    @Test
    @Order(4)
    public void should_find_by_unique_id() {
        long uniqueId = 2L;
        when(iAccount.findByUniqueId(uniqueId)).thenReturn(Optional.ofNullable(ACCOUNTS.get(1)));

        Account result = accountService.findByUniqueId(uniqueId);

        assertEquals(result, ACCOUNTS.get(1));
    }

    @Test
    @Order(5)
    public void should_delete_account() {
        int id = 3;

        accountService.delete(id);

        verify(iAccount, times(1)).deleteById(id);
    }

}
