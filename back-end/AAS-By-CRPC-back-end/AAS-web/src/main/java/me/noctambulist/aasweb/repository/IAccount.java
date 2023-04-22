package me.noctambulist.aasweb.repository;

import me.noctambulist.aasweb.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/21 13:58
 */
public interface IAccount extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    Optional<Account> findByUniqueId(Long id);

}