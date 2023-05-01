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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 22:56
 */
@Entity
@Table(name = "class_schedule",
        indexes = {@Index(name = "courseId_courseNum", columnList = "course_id, course_num"),
                @Index(name = "studentId_tutorId", columnList = "student_id, tutor_id"),
                @Index(name = "semesterId_scoreId", columnList = "semester_id, score_id")
        })
@DynamicInsert
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSchedule extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2963941274144650698L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "course_num")
    private String courseNum;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "tutor_id")
    private Long tutorId;

    @Column(name = "semester_id")
    private Integer semesterId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id", referencedColumnName = "id")
    private Score score;

    @Column(name = "status")
    private String status = "选中";

    @Column(name = "class_info", columnDefinition = "json")
    private String classInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClassSchedule that = (ClassSchedule) o;
        return Objects.equals(id, that.id) && Objects.equals(courseId, that.courseId) && Objects.equals(courseNum, that.courseNum) && Objects.equals(studentId, that.studentId) && Objects.equals(tutorId, that.tutorId) && Objects.equals(semesterId, that.semesterId) && Objects.equals(score, that.score) && Objects.equals(status, that.status) && Objects.equals(classInfo, that.classInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, courseId, courseNum, studentId, tutorId, semesterId, score, status, classInfo);
    }

}
