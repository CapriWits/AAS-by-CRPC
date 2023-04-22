package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.entity.Role;
import me.noctambulist.aasweb.repository.IRole;
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
 * @Date: 2023/4/22 23:00
 */
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleServiceTest {

    private static final List<Role> ROLES = Arrays.asList(
            new Role(1, 1L, (byte) 1),
            new Role(2, 2L, (byte) 2),
            new Role(3, 3L, (byte) 3)
    );

    @Autowired
    private RoleService roleService;

    @MockBean
    private IRole iRole;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Order(1)
    public void should_create_role() {
        Role role = new Role(null, 4L, (byte) 4);
        when(iRole.save(role)).thenReturn(role);

        Role result = roleService.create(role);

        assertEquals(result.getUniqueId(), role.getUniqueId());
        assertEquals(result.getRole(), role.getRole());
    }

    @Test
    @Order(2)
    public void should_find_all_roles() {
        when(iRole.findAll()).thenReturn(ROLES);

        List<Role> result = roleService.findAll();

        assertEquals(result.size(), ROLES.size());
        assertTrue(result.containsAll(ROLES));
    }

    @Test
    @Order(3)
    public void should_find_by_id() {
        int id = 1;
        when(iRole.findById(id)).thenReturn(Optional.ofNullable(ROLES.get(id - 1)));

        Role result = roleService.findById(id);

        assertEquals(result, ROLES.get(id - 1));
    }

    @Test
    @Order(4)
    public void should_find_by_unique_id() {
        long uniqueId = 2L;
        when(iRole.findByUniqueId(uniqueId)).thenReturn(Optional.ofNullable(ROLES.get(1)));

        Role result = roleService.findByUniqueId(uniqueId);

        assertEquals(result, ROLES.get(1));
    }

    @Test
    @Order(5)
    public void should_delete_role() {
        int id = 3;

        roleService.delete(id);

        verify(iRole, times(1)).deleteById(id);
    }

}