package me.noctambulist.aasweb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.entity.dto.RoleDTO;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/22 22:53
 */
@Entity
@Table(name = "role", indexes = {
        @Index(name = "idx_role", columnList = "role"),
        @Index(name = "idx_unique_id", columnList = "unique_id", unique = true)
})
@DynamicInsert
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "unique_id", nullable = false)
    private Long uniqueId;

    @Column(name = "role", nullable = false)
    private Byte role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) && Objects.equals(uniqueId, role1.uniqueId) && Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uniqueId, role);
    }

    public static RoleDTO entityToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(role, dto);
        return dto;
    }

}