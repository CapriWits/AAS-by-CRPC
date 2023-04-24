package me.noctambulist.aasweb.repository;

import me.noctambulist.aasweb.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/22 22:56
 */
@Repository
public interface IRole extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    Optional<Role> findByUniqueId(Long id);

}