package com.pisces.platform.rds.config;

import com.pisces.platform.core.entity.EntityObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

public class MybatisEntityFactory extends DefaultObjectFactory {

    /**
     *
     */
    private static final long serialVersionUID = -4783626710004724883L;

    @Override
    public <T> T create(Class<T> type) {
        T entity = super.create(type);
        if (EntityObject.class.isAssignableFrom(type)) {
            ((EntityObject) entity).init();
        }
        return entity;
    }

}
