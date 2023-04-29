package me.noctambulist.aasweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.model.dto.AccountDTO;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/21 13:52
 */
@Entity
@Table(name = "account", indexes = {
        @Index(name = "idx_password", columnList = "password"),
        @Index(name = "idx_unique_id", columnList = "unique_id", unique = true)
})
@DynamicInsert
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1297285431158272598L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "unique_id", nullable = false)
    private Long uniqueId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(uniqueId, account.uniqueId) && Objects.equals(email, account.email) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, uniqueId, email, password);
    }

    public static AccountDTO entity2DTO(Account account) {
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(account, dto);
        return dto;
    }

}
