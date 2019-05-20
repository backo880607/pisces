package com.pisces.rds.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.MultiEnum;
import com.pisces.core.relation.Sign;
import com.pisces.rds.handler.MultiEnumTypeHandler;
import com.pisces.rds.handler.SignTypeHandler;

import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityField;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.resolve.DefaultEntityResolve;

public class RelationEntityResolve extends DefaultEntityResolve {
	@Override
	public EntityTable resolveEntity(Class<?> entityClass, Config config) {
		EntityTable table = super.resolveEntity(entityClass, config);
		addRelationColumns(entityClass, table);
		return table;
	}
	
	protected void addRelationColumns(Class<?> entityClazz, EntityTable table) {
		Set<EntityColumn> columns = table.getEntityClassColumns();
		Field[] fields = entityClazz.getFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) && field.getType() == Sign.class) {
				EntityColumn column = new EntityColumn(table);
				column.setColumn(field.getName());
				column.setProperty(field.getName());
				column.setJavaType(String.class);
				column.setTypeHandler(SignTypeHandler.class);
				column.setJdbcType(JdbcType.LONGVARCHAR);
				EntityField entityField = new EntityField(null, null);
				try {
					modify(entityField, "name", field.getName());
					modify(entityField, "field", field);
					modify(entityField, "javaType", String.class);
					modify(entityField, "setter", null);
					modify(entityField, "getter", null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				column.setEntityField(entityField);
				columns.add(column);
			}
		}
		
		Class<?> clazz = entityClazz;
		while (clazz != EntityObject.class) {
			fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (!Modifier.isStatic(field.getModifiers()) && MultiEnum.class.isAssignableFrom(field.getType())) {
					Transient tran = field.getAnnotation(Transient.class);
					if (tran == null) {
						EntityColumn column = new EntityColumn(table);
						column.setColumn(field.getName());
						column.setProperty(field.getName());
						column.setJavaType(field.getType());
						column.setJdbcType(JdbcType.INTEGER);
						column.setTypeHandler(MultiEnumTypeHandler.class);
						EntityField entityField = new EntityField(null, null);
						try {
							modify(entityField, "name", field.getName());
							modify(entityField, "field", field);
							modify(entityField, "javaType", field.getType());
							modify(entityField, "setter", null);
							modify(entityField, "getter", null);
						} catch (Exception e) {
							e.printStackTrace();
						}
						column.setEntityField(entityField);
						columns.add(column);
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
	}
	
	private static void modify(EntityField entityField, String fieldName, Object object) throws Exception {
		Field field = EntityField.class.getDeclaredField(fieldName);
		field.setAccessible(true);
	    field.set(entityField, object);
	    field.setAccessible(false);
	}
}
