package me.noctambulist.aasweb.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.entity.Account;

import java.util.Objects;

/**
 * Same as {@link Account}, except for an {@link Account#getId()}
 *
 * @Author: Hypocrite30
 * @Date: 2023/4/24 22:38
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

    @JsonProperty("unique_id")
    private Long uniqueId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(uniqueId, that.uniqueId) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, email, password);
    }

}
