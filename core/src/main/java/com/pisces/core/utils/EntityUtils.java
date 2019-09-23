package com.pisces.core.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.annotation.PropertyMeta;
import com.pisces.core.converter.DurationDeserializer;
import com.pisces.core.converter.DurationSerializer;
import com.pisces.core.converter.DateTimeDeserializer;
import com.pisces.core.converter.DateTimeSerializer;
import com.pisces.core.converter.EntitySerializerModifier;
import com.pisces.core.converter.NullValueSerializer;
import com.pisces.core.converter.SignFieldHandler;
import com.pisces.core.converter.SqlDateJsonDeserializer;
import com.pisces.core.converter.SqlDateJsonSerializer;
import com.pisces.core.entity.DateDur;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.MultiEnum;
import com.pisces.core.entity.Property;
import com.pisces.core.enums.PROPERTY_TYPE;
import com.pisces.core.exception.ConfigurationException;
import com.pisces.core.relation.RelationKind;
import com.pisces.core.relation.Sign;
import com.pisces.core.service.EntityService;
import com.pisces.core.service.ServiceManager;

public class EntityUtils {
	
	public static void init() {
		Primary.get().init();
		try {
			checkProperty();
			checkEntity();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<Class<? extends EntityObject>> getEntityClasses() {
		return Primary.get().getEntityClasses();
	}
	
	public static Class<? extends EntityObject> getEntityClass(String name) {
		return Primary.get().getEntityClass(name);
	}
	
	public static Class<? extends EntityObject> getSuperClass(Class<? extends EntityObject> clazz) {
		return Primary.get().getSuperClass(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends EntityObject> T getInherit(Class<T> clazz, long id) {
		EntityService<T> service = ServiceManager.getService(clazz);
		T entity = null;
		if (service != null) {
			entity = service.selectById(id);
		}
		
		if (entity == null) {
			List<Class<? extends EntityObject>> childClasses = Primary.get().getChildClasses(clazz);
			for (Class<? extends EntityObject> childClass : childClasses) {
				entity = (T) getInherit(childClass, id);
				if (entity != null) {
					return entity;
				}
			}
		}
		
		return entity;
	}
	
	public static <T extends EntityObject> List<T> getInherit(Class<T> clazz, List<Long> ids) {
		List<T> result = new ArrayList<>();
		for (Long id : ids) {
			T entity = getInherit(clazz, id);
			if (entity != null) {
				result.add(entity);
			}
		}
		return result;
	}
	
	public static List<Property> getDefaultProperties() {
		List<Property> result = new ArrayList<>();
		for (Class<? extends EntityObject> clazz : getEntityClasses()) {
			getDefaultPropertiesImpl(result, clazz, clazz);
		}
		
		return result;
	}

	public static List<Property> getDefaultProperties(Class<? extends EntityObject> clazz) {
		List<Property> result = new LinkedList<>();
		getDefaultPropertiesImpl(result, clazz, clazz);
		return result;
	}
	
	private static void getDefaultPropertiesImpl(List<Property> result, Class<? extends EntityObject> clazz, Class<? extends EntityObject> belongClass) {
		if (clazz == null) {
			return;
		}
		
		getDefaultPropertiesImpl(result, Primary.get().getSuperClass(clazz), belongClass);
		
		Map<String, Property> codeMapProperties = new HashMap<String, Property>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			try {
				Property property = createProperty(clazz, belongClass, field);
				if (property != null) {
					result.add(property);
					codeMapProperties.put(property.getCode(), property);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		PrimaryKey primaryKey = clazz.getAnnotation(PrimaryKey.class);
		if (primaryKey != null) {
			String[] primaryFields = primaryKey.fields();
			for (String primaryField : primaryFields) {
				Property property = codeMapProperties.get(primaryField);
				if (property == null) {
					throw new ConfigurationException(clazz.getName() + " config primary key has error field name: " + primaryField);
				}
				
				property.setPrimaryKey(true);
			}
		}
		
	}
	
	private static Property createProperty(Class<? extends EntityObject> clazz, Class<? extends EntityObject> belongClass, Field field) throws Exception {
		if (Modifier.isTransient(field.getModifiers())) {
			return null;
		}
		PropertyMeta meta = field.getAnnotation(PropertyMeta.class);
		if (meta != null && meta.internal()) {
			return null;
		}
		if (Modifier.isStatic(field.getModifiers()) && field.getType() != Sign.class) {
			return null;
		}
		Property property = new Property();
		property.init();
		property.setInherent(true);
		property.setBelongName(belongClass.getSimpleName());
		property.setCode(field.getName());
		property.setName(property.getCode());
		property.belongClazz = belongClass;
		if (field.getType() == Sign.class) {
			Sign sign = (Sign)field.get(null);
			RelationKind kind = Primary.get().getRelationKind(clazz, sign);
			if (kind == null) {
				throw new UnsupportedOperationException(clazz.getName() + "`s field " + field.getName() + " not set relation annotation");
			}
			switch (kind) {
			case Singleton:
				property.setType(PROPERTY_TYPE.ENTITY);
				break;
			default:
				property.setType(PROPERTY_TYPE.LIST);
				break;
			}
			property.sign = sign;
			property.clazz = Primary.get().getRelationClass(clazz, sign);
		} else {
			property.clazz = field.getType();
			property.setType(getPropertyType(field.getType()));
		}
		property.setTypeName(property.clazz.getName());
		
		try {
			property.getMethod = clazz.getMethod("get" + StringUtils.capitalize(property.getCode()));
		} catch (Exception ex) {
		}
		try {
			property.setMethod = clazz.getMethod("set" + StringUtils.capitalize(property.getCode()), property.clazz);
		} catch (Exception ex) {
		}
		
		if (meta != null) {
			property.setModifiable(meta.modifiable());
			property.setDisplay(meta.display());
			if (meta.type() != PROPERTY_TYPE.NONE) {
				property.setType(meta.type());
			}
		}
		if (property.getMethod == null) {
			throw new NoSuchMethodException(clazz.getName() + "`s Field " + property.getCode() + " has not get method!");
		}
		if (property.setMethod == null && property.getType() != PROPERTY_TYPE.LIST) {
			throw new NoSuchMethodException(clazz.getName() + "`s Field " + property.getCode() + " has not set method!");
		}
		
		return property;
	}
	
	public static void checkProperty() throws Exception {
		for (Class<? extends EntityObject> clazz : getEntityClasses()) {
			Field[] fields = clazz.getDeclaredFields();
			Map<String, Field> fieldCache = new HashMap<String, Field>();
			for (Field field : fields) {
				if (Modifier.isTransient(field.getModifiers())) {
					continue;
				}
				PropertyMeta meta = field.getAnnotation(PropertyMeta.class);
				if (meta != null && meta.internal()) {
					continue;
				}
				if (Modifier.isStatic(field.getModifiers()) && field.getType() != Sign.class) {
					continue;
				}
				
				PROPERTY_TYPE type = PROPERTY_TYPE.NONE;
				Class<?> fieldClass = null;
				if (field.getType() == Sign.class) {
					Sign sign = (Sign)field.get(null);
					RelationKind kind = Primary.get().getRelationKind(clazz, sign);
					if (kind == null) {
						throw new UnsupportedOperationException(clazz.getName() + "`s field " + field.getName() + " not set relation annotation");
					}
					switch (kind) {
					case Singleton:
						type = PROPERTY_TYPE.ENTITY;
						break;
					default:
						type = PROPERTY_TYPE.LIST;
						break;
					}
					fieldClass = Primary.get().getRelationClass(clazz, sign);
				} else {
					type = getPropertyType(field.getType());
					fieldClass = field.getType();
				}
				Method getMethod = null;
				Method setMethod = null;
				try {
					getMethod = clazz.getMethod("get" + StringUtils.capitalize(field.getName()));
				} catch (Exception ex) {
				}
				try {
					setMethod = clazz.getMethod("set" + StringUtils.capitalize(field.getName()), fieldClass);
				} catch (Exception ex) {
				}
				
				if (getMethod == null) {
					throw new NoSuchMethodException(clazz.getName() + "`s Field " + field.getName() + " has not get method!");
				}
				if (setMethod == null && type != PROPERTY_TYPE.LIST) {
					throw new NoSuchMethodException(clazz.getName() + "`s Field " + field.getName() + " has not set method!");
				}
				
				fieldCache.put(field.getName(), field);
			}
			
			PrimaryKey primaryKey = clazz.getAnnotation(PrimaryKey.class);
			if (primaryKey != null) {
				String[] primaryFields = primaryKey.fields();
				for (String primaryField : primaryFields) {
					Field field = fieldCache.get(primaryField);
					if (field == null) {
						throw new ConfigurationException(clazz.getName() + " config primary key has error field name: " + primaryField);
					}
				}
			}
		}
	}
	
	public static void checkEntity() throws Exception {
		List<Class<? extends EntityObject>> clazzs = getEntityClasses();
		for (Class<? extends EntityObject> clazz : clazzs) {
			if (clazz.getSimpleName().equals("Property") ||
				clazz.getSimpleName().equals("EntityObject")) {
				continue;
			}
			EntityObject entity = clazz.newInstance();
			entity.init();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				field.setAccessible(true);
				if (field.get(entity) == null) {
					throw new NullPointerException(clazz.getName() + "`s field " + field.getName() + " has not default value.");
				}
			}
		}
	}
	
	public static PROPERTY_TYPE getPropertyType(Class<?> clazz) {
		PROPERTY_TYPE type = PROPERTY_TYPE.NONE;
		if (clazz == Boolean.class || clazz == boolean.class) {
			type = PROPERTY_TYPE.BOOLEAN;
		} else if (clazz == Character.class || clazz == char.class) {
			type = PROPERTY_TYPE.CHAR;
		} else if (clazz == Short.class || clazz == short.class) {
			type = PROPERTY_TYPE.LONG;
		} else if (clazz == Integer.class || clazz == int.class) {
			type = PROPERTY_TYPE.LONG;
		} else if (clazz == Long.class || clazz == long.class) {
			type = PROPERTY_TYPE.LONG;
		} else if (clazz == Float.class || clazz == float.class) {
			type = PROPERTY_TYPE.DOUBLE;
		} else if (clazz == Double.class || clazz == double.class) {
			type = PROPERTY_TYPE.DOUBLE;
		} else if (clazz == String.class) {
			type = PROPERTY_TYPE.STRING;
		} else if (clazz == Date.class) {
			type = PROPERTY_TYPE.DATE;
		} else if (clazz == DateDur.class) {
			type = PROPERTY_TYPE.DURATION;
		} else if (Enum.class.isAssignableFrom(clazz)) {
			type = PROPERTY_TYPE.ENUM;
		} else if (MultiEnum.class.isAssignableFrom(clazz)) {
			type = PROPERTY_TYPE.MULTI_ENUM;
		} else if (EntityObject.class.isAssignableFrom(clazz)) {
			type = PROPERTY_TYPE.ENTITY;
		} else if (Collection.class.isAssignableFrom(clazz)) {
			type = PROPERTY_TYPE.LIST;
		} else {
			throw new UnsupportedOperationException("not support type: " + clazz.getName());
		}
		return type;
	}
	
	public static Object getValue(EntityObject entity, Property property) {
		if (property.getMethod == null) {
			return null;
		}
		
		Object value = null;
		try {
			if (property.getInherent()) {
				value = property.getMethod.invoke(entity);
			} else if (StringUtils.isEmpty(property.getExpression())) {
				value = property.getMethod.invoke(entity, property.getCode());
			} else {
				IExpression expression = Primary.get().createExpression(property.getExpression());
				if (expression != null) {
					value = expression.getValue(entity);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	
	public static String getTextValue(EntityObject entity, Property property) {
		Object value = getValue(entity, property);
		if (value == null) {
			return "";
		}
		
		try {
			return defaultObjectMapper().writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void setValue(EntityObject entity, Property property, Object value) {
		if (property.setMethod == null || value == null || !property.getModifiable()) {
			return;
		}
		
		try {
			if (property.getInherent()) {
				property.setMethod.invoke(entity, value);
			} else if (StringUtils.isEmpty(property.getExpression())) {
				property.setMethod.invoke(entity, property.getCode(), value);
			}
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static Object convertTextValue(Property property, String str) {
		try {
			return defaultObjectMapper().readValue(str, property.clazz);
		} catch (IOException e) {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void setTextValue(EntityObject entity, Property property, String str) {
		setValue(entity, property, convertTextValue(property, str));
	}
	
	/**
	 * 获取默认的Jackson序列化Mapper
	 * @return
	 */
	public static ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateTimeSerializer());
        module.addDeserializer(Date.class, new DateTimeDeserializer());
        module.addSerializer(java.sql.Date.class, new SqlDateJsonSerializer());
        module.addDeserializer(java.sql.Date.class, new SqlDateJsonDeserializer());
        module.addSerializer(DateDur.class, new DurationSerializer());
        module.addDeserializer(DateDur.class, new DurationDeserializer());
        mapper.addHandler(new SignFieldHandler());
        mapper.getSerializerProvider().setNullValueSerializer(new NullValueSerializer());
        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new EntitySerializerModifier()));
        mapper.registerModule(module);
        mapper.setDefaultSetterInfo(JsonSetter.Value.construct(Nulls.SKIP, Nulls.SKIP));
        return mapper;
	}
	
	private static ObjectMapper defaultMapper;
	public static ObjectMapper defaultObjectMapper() {
		if (defaultMapper == null) {
			synchronized (EntityUtils.class) {
				if (defaultMapper == null) {
					defaultMapper = createObjectMapper();
				}
			}
		}
		return defaultMapper;
	}
	
	public static <T extends EntityObject> void copyIgnoreNull(T src, T target) {
		if (src.getClass() != target.getClass()) {
			return;
		}
		
		List<Property> properties = AppUtils.getPropertyService().get(src.getClass());
		for (Property property : properties) {
			Object srcValue = getValue(src, property);
			if (srcValue == null) {
				continue;
			}
			
			setValue(target, property, srcValue);
		}
	}
}
