package me.noctambulist.aasweb.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.model.Role;

import java.io.Serializable;
import java.util.Objects;

/**
 * Same as {@link Role}, except for an {@link Role#getId()}
 *
 * @Author: Hypocrite30
 * @Date: 2023/4/24 21:29
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = -3037615114235729825L;

    @JsonProperty("unique_id")
    private Long uniqueId;

    @JsonProperty("role")
    private Byte role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(uniqueId, roleDTO.uniqueId) && Objects.equals(role, roleDTO.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, role);
    }

}
