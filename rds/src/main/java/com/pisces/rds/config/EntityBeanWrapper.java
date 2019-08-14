package com.pisces.rds.config;

import java.util.Collection;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Ioc;
import com.pisces.core.relation.RefBase;
import com.pisces.core.relation.Sign;
import com.pisces.core.utils.IDGenerator;
import com.pisces.core.utils.Primary;
import com.pisces.core.utils.StringUtils;
import com.pisces.rds.handler.UserFieldTypeHandler;

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
			Collection<EntityObject> relaEntities = Ioc.getList(this.entity, sign);
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
			if (value.getClass() != String.class || ((String)value).isEmpty()) {
				return;
			}
			Class<? extends EntityObject> relaClazz = Primary.get().getRelationClass(entityClazz, sign);
			if (relaClazz == null) {
				return;
			}
			RefBase ref = this.entity.getRelations().get(sign);
			ref.bindSign(sign);
			for (String strId : ((String)value).split(",")) {
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
