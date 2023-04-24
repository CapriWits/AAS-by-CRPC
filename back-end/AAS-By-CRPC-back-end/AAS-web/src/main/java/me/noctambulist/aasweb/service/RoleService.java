package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.entity.Role;
import me.noctambulist.aasweb.repository.IRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/22 22:57
 */
@Service
public class RoleService {

    private final IRole iRole;

    @Autowired
    public RoleService(IRole iRole) {
        this.iRole = iRole;
    }

    @Transactional
    public Role create(Role role) {
        return iRole.saveAndFlush(role);
    }

    public List<Role> findAll() {
        return iRole.findAll();
    }

    public Role findById(Integer id) {
        return iRole.findById(id).orElse(null);
    }

    public Role findByUniqueId(Long id) {
        return iRole.findByUniqueId(id).orElse(null);
    }

    public void delete(Integer id) {
        iRole.deleteById(id);
    }
}
