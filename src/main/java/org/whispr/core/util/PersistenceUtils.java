package org.whispr.core.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;
import org.whispr.core.dao.entity.BaseEntity;

@UtilityClass
public class PersistenceUtils {

    /**
     * Returns the effective persistence class of the given entity.
     *
     * @param entity the entity whose persistent class is to be determined
     * @return the persistent class of the entity, or the class itself if it is not a proxy
     */
    public Class<?> getPersistentClass(@NonNull BaseEntity<?> entity) {
        // If the entity is a Hibernate proxy, retrieve the actual persistent class
        if (entity instanceof HibernateProxy) {
            return ((HibernateProxy) entity).getHibernateLazyInitializer().getPersistentClass();
        }

        // If the entity is not a proxy, return its class directly
        return entity.getClass();
    }
}
