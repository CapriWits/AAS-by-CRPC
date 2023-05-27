package me.noctambulist.aasweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/26 22:33
 */
@Entity
@Table(name = "semester")
@DynamicInsert
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Semester implements Serializable {

    private static final long serialVersionUID = -5101954679784531107L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "semester")
    private String semester;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester1 = (Semester) o;
        return Objects.equals(id, semester1.id) && Objects.equals(semester, semester1.semester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, semester);
    }

}
