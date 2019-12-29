package com.pisces.platform.rds.config;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.relation.RefBase;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.utils.IDGenerator;
import com.pisces.platform.core.utils.Primary;
import com.pisces.platform.core.utils.StringUtils;
import com.pisces.platform.rds.handler.UserFieldTypeHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;

import java.util.Collection;

public class EntityBeanWrapper extends BeanWrapper {
    private Class<? extends EntityObject> entityClazz;
    private EntityObject entity;

    @SuppressWarnings("unchecked")
    public EntityBeanWrapper(MetaObject metaObject, Object object) {
        super(metaObject, object);
        this.entityClazz = (Class<? extends EntityObject>) object.getClass();
        this.entity = (EntityObject) object;
        if (this.entity.getId() == null || this.entity.getId() == 0) {
            this.entity.setId(IDGenerator.instance.getID());
        }
        UserFieldTypeHandler.entityClazz.set(this.entityClazz);
    }

    @Override
    public Object get(PropertyTokenizer prop) {
        Sign sign = Primary.get().getRelationSign(this.entityClazz, prop.getName());
        if (sign != null) {
            Collection<EntityObject> relaEntities = this.entity.getList(sign);
            return StringUtils.join(relaEntities, ",", (EntityObject temp) -> {
                return temp.getId().toString();
            });
        }
        return super.get(prop);
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        if (value == null) {
            return;
        }
        Sign sign = Primary.get().getRelationSign(this.entityClazz, prop.getName());
        if (sign != null) {
            if (value.getClass() != String.class || ((String) value).isEmpty()) {
                return;
            }
            Class<? extends EntityObject> relaClazz = Primary.get().getRelationClass(entityClazz, sign);
            if (relaClazz == null) {
                return;
            }
            RefBase ref = this.entity.getRelations().get(sign);
            ref.bindSign(sign);
            for (String strId : ((String) value).split(",")) {
                if (strId.isEmpty()) {
                    continue;
                }
                try {
                    ref.bindId(Long.valueOf(strId));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.set(prop, value);
        }
    }
}
