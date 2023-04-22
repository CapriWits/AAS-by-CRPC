package me.noctambulist.aasweb.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/21 13:45
 */
@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

}
