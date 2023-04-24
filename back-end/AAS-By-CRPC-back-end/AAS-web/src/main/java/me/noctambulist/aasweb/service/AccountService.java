package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.entity.Account;
import me.noctambulist.aasweb.repository.IAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/21 14:00
 */
@Service
public class AccountService {

    private final IAccount iAccount;

    @Autowired
    public AccountService(IAccount iAccount) {
        this.iAccount = iAccount;
    }

    @Transactional
    public Account create(Account account) {
        return iAccount.saveAndFlush(account);
    }

    public List<Account> findAll() {
        return iAccount.findAll();
    }

    public Account findById(Integer id) {
        return iAccount.findById(id).orElse(null);
    }

    public Account findByUniqueId(Long id) {
        return iAccount.findByUniqueId(id).orElse(null);
    }

    public void delete(Integer id) {
        iAccount.deleteById(id);
    }

    public void deleteByUniqueId(Long uniqueId) {
        iAccount.deleteByUniqueId(uniqueId);
    }

}
