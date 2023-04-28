package me.noctambulist.aasweb.entity;

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
 * @Date: 2023/4/28 10:56
 */
@Entity
@Table(name = "score")
@DynamicInsert
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2842613312990983278L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "score")
    private Double score = 0d;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Score score1 = (Score) o;
        return Objects.equals(id, score1.id) && Objects.equals(score, score1.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, score);
    }

}
