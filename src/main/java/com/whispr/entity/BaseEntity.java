package com.whispr.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Returns the unique identifier of the entity.
     *
     * @return the ID of the entity
     */
    // TODO: Consider using a annotation scanning to automatically detect ID fields along with proper caching to avoid reflection overhead.
    public abstract UUID getId();

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;

        BaseEntity that = (BaseEntity) obj;
        return this.getId() != null && Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        Class<?> persistentClass = Hibernate.getClass(this);
        return Objects.hash(persistentClass, getId());
    }

    @Override
    public String toString() {
        Class<?> persistentClass = Hibernate.getClass(this);
        return persistentClass.getSimpleName() + " [id=" + getId() + "]";
    }
}
