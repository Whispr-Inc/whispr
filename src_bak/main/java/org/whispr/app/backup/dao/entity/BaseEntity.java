package org.whispr.app.backup.dao.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.whispr.app.backup.util.PersistenceUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<ID> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public abstract ID getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof BaseEntity<?> that)) return false;

        Class<?> thisClass = PersistenceUtils.getPersistentClass(this);
        Class<?> thatClass = PersistenceUtils.getPersistentClass(that);
        if (!thisClass.equals(thatClass)) return false;

        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return PersistenceUtils.getPersistentClass(this).hashCode();
    }

    @Override
    public String toString() {
        return PersistenceUtils.getPersistentClass(this).getSimpleName() + " [" + "id=" + getId() + ']';
    }
}
