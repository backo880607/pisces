package com.pisces.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.ConfigurationException;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.annotation.PropertyMeta;
import com.pisces.core.converter.DateJsonDeserializer;
import com.pisces.core.converter.DateJsonSerializer;
import com.pisces.core.converter.SignFieldHandler;
import com.pisces.core.converter.SqlDateJsonDeserializer;
import com.pisces.core.converter.SqlDateJsonSerializer;
import com.pisces.core.entity.DateDur;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.MultiEnum;
import com.pisces.core.entity.Property;
import com.pisces.core.enums.EditType;
import com.pisces.core.enums.PropertyType;
import com.pisces.core.exception.RegisteredException;
import com.pisces.core.exception.RelationException;
import com.pisces.core.relation.RelationKind;
import com.pisces.core.relation.Sign;
import com.pisces.core.service.EntityService;
import com.pisces.core.service.ServiceManager;

public class EntityUtils {
	private static final Map<String, Class<? extends EntityObject>> classes = new HashMap<>();
	private static final Map<Class<? extends EntityObject>, Map<String, Property>> properties = new HashMap<>();
	private static final Map<Class<? extends EntityObject>, List<Property>> primaries = new HashMap<>();
	
	public static void registerEntityClass(Class<? extends EntityObject> clazz) {
		if (classes.containsKey(clazz.getSimpleName())) {
			throw new RegisteredException(clazz.getName() + " has registered!");
		}
		classes.put(clazz.getSimpleName(), clazz);
		properties.put(clazz, new HashMap<>());
		primaries.put(clazz, new LinkedList<>());
	}
	
	public static void init() {
		for (Class<? extends EntityObject> clazz : getEntityClasses()) {
			initImpl(clazz);
		}
	}
	private static void initImpl(Class<? extends EntityObject> clazz) {
		if (clazz == EntityObject.class) {
			return;
		}
		if (!classes.containsKey(clazz.getSimpleName())) {
			classes.put(clazz.getSimpleName(), clazz);
			properties.put(clazz, new ConcurrentHashMap<>());
			primaries.put(clazz, new LinkedList<>());
		}
		@SuppressWarnings("unchecked")
		Class<? extends EntityObject> superClass = (Class<? extends EntityObject>) clazz.getSuperclass();
		initImpl(superClass);
	}
	
	public static List<Class<? extends EntityObject>> getEntityClasses() {
		List<Class<? extends EntityObject>> result = new ArrayList<>();
		for (Entry<String, Class<? extends EntityObject>> entry : classes.entrySet()) {
			result.add(entry.getValue());
		}
		return result;
	}
	
	public static Class<? extends EntityObject> getEntityClass(String name) {
		Class<? extends EntityObject> clazz = classes.get(name);
		if (clazz == null) {
			throw new RegisteredException(name + " has not registered!");
		}
		return clazz;
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
		
		return result;
	}

	public static List<Property> getProperties(Class<? extends EntityObject> clazz) {
		List<Property> result = new LinkedList<>();
		getPropertiesImpl(result, clazz);
		return result;
	}
	
	private static void getPropertiesImpl(List<Property> result, Class<? extends EntityObject> clazz) {
		if (clazz == null) {
			return;
		}
		
		getPropertiesImpl(result, Primary.get().getSuperClass(clazz));
		Map<String, Property> temp = properties.get(clazz);
		if (temp == null) {
			return;
		}
		
		for (Entry<String, Property> entry : properties.get(clazz).entrySet()) {
			result.add(entry.getValue());
		}
	}
	
	public static Property getProperty(Class<? extends EntityObject> clazz, String name) {
		if (clazz == null) {
			return null;
		}
		
		Map<String, Property> temp = properties.get(clazz);
		Property result = temp != null ? temp.get(name) : null;
		return result != null ? result : getProperty(Primary.get().getSuperClass(clazz), name);
	}
	
	public static boolean hasProperty(Class<? extends EntityObject> clazz, String name) {
		return getProperty(clazz, name) != null;
	}
	
	public static List<Property> getPrimaries(Class<? extends EntityObject> clazz) {
		List<Property> result = new LinkedList<>();
		getPrimariesImpl(result, clazz);
		if (result.isEmpty()) {
			result.add(getProperty(EntityObject.class, "id"));
		}
		return result;
	}
	
	private static void getPrimariesImpl(List<Property> result, Class<? extends EntityObject> clazz) {
		if (clazz == null) {
			return;
		}
		
		getPrimariesImpl(result, Primary.get().getSuperClass(clazz));
		List<Property> temp = primaries.get(clazz);
		if (temp == null) {
			return;
		}
		result.addAll(temp);
	}
	
	public static void checkProperty() throws Exception {
		for (Entry<String, Class<? extends EntityObject>> entry : classes.entrySet()) {
			Class<? extends EntityObject> clazz = entry.getValue();
			Map<String, Property> temp = properties.get(clazz);
			if (temp == null) {
				throw new RegisteredException(clazz.getName() + " has not registered!");
			}
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isTransient(field.getModifiers())) {
					continue;
				}
				PropertyMeta meta = field.getAnnotation(PropertyMeta.class);
				if (meta != null && meta.internal()) {
					continue;
				}
				Property property = new Property();
				property.setInherent(true);
				property.setBelongName(clazz.getSimpleName());
				property.setCode(field.getName());
				property.setName(property.getCode());
				property.belongClazz = clazz;
				if (field.getType() == Sign.class) {
					Sign sign = (Sign)field.get(null);
					RelationKind kind = Primary.get().getRelationKind(clazz, sign);
					if (kind == null) {
						throw new RelationException(clazz.getName() + "`s field " + field.getName() + " not set relation annotation");
					}
					switch (kind) {
					case Singleton:
						property.setType(PropertyType.Object);
						break;
					default:
						property.setType(PropertyType.List);
						break;
					}
					property.sign = sign;
				} else {
					property.setType(getPropertyType(field.getType()));
				}
				try {
					property.getMethod = clazz.getMethod("get" + StringUtils.capitalize(property.getName()));
				} catch (Exception ex) {
				}
				try {
					property.setMethod = clazz.getMethod("set" + StringUtils.capitalize(property.getName()), field.getType());
				} catch (Exception ex) {
				}
				
				if (meta != null) {
					property.setEditType(meta.editType() != EditType.NONE ? meta.editType() : getDefaultEditType(property.getType()));
					property.setModifiable(meta.modifiable());
					property.setNullable(meta.nullable());
					property.setDisplay(meta.display());
				} else {
					property.setEditType(getDefaultEditType(property.getType()));
				}
				if (property.getMethod == null || property.setMethod == null) {
					if (!Modifier.isStatic(field.getModifiers())) {
						throw new NoSuchMethodException(clazz.getName() + "`s Field " + property.getCode() + " has not get or set method!");
					}
				}
				
				temp.put(property.getCode(), property);
			}
			
			List<Property> primaryProperties = primaries.get(clazz);
			PrimaryKey primaryKey = clazz.getAnnotation(PrimaryKey.class);
			if (primaryKey != null) {
				String[] primaryFields = primaryKey.fields();
				for (String primaryField : primaryFields) {
					Property property = temp.get(primaryField);
					if (property == null) {
						throw new ConfigurationException(clazz.getName() + " config primary key has error field name: " + primaryField);
					}
					
					property.setPrimaryKey(true);
					primaryProperties.add(property);
				}
			}
		}
	}
	
	public static PropertyType getPropertyType(Class<?> clazz) {
		PropertyType type = PropertyType.UserDefined;
		if (clazz == Boolean.class || clazz == boolean.class) {
			type = PropertyType.Boolean;
		} else if (clazz == Character.class || clazz == char.class) {
			type = PropertyType.Char;
		} else if (clazz == Short.class || clazz == short.class) {
			type = PropertyType.Short;
		} else if (clazz == Integer.class || clazz == int.class) {
			type = PropertyType.Integer;
		} else if (clazz == Long.class || clazz == long.class) {
			type = PropertyType.Long;
		} else if (clazz == Float.class || clazz == float.class) {
			type = PropertyType.Double;
		} else if (clazz == Double.class || clazz == double.class) {
			type = PropertyType.Double;
		} else if (clazz == String.class) {
			type = PropertyType.String;
		} else if (clazz == Date.class) {
			type = PropertyType.Date;
		} else if (Enum.class.isAssignableFrom(clazz)) {
			type = PropertyType.Enum;
		} else if (MultiEnum.class.isAssignableFrom(clazz)) {
			type = PropertyType.MultiEnum;
		} else if (EntityObject.class.isAssignableFrom(clazz)) {
			type = PropertyType.Object;
		} else if (Collection.class.isAssignableFrom(clazz)) {
			type = PropertyType.List;
		}
		return type;
	}
	
	public static Class<?> getPropertyClass(Property property) {
		if (property.sign != null) {
			return property.sign.getEntityClass();
		}
		Class<?> clazz = null;
		switch (property.getType()) {
		case Boolean:
			clazz = Boolean.class;
			break;
		case Char:
			clazz = Character.class;
			break;
		case Date:
			clazz = Date.class;
			break;
		case Double:
			clazz = Double.class;
			break;
		case Duration:
			clazz = DateDur.class;
			break;
		case Enum:
			clazz = Enum.class;
			break;
		case Integer:
			clazz = Integer.class;
			break;
		case List:
			clazz = Collection.class;
			break;
		case Long:
			clazz = Long.class;
			break;
		case MultiEnum:
			clazz = MultiEnum.class;
			break;
		case Object:
			clazz = EntityObject.class;
			break;
		case Short:
			clazz = Short.class;
			break;
		case UserDefined:
			break;
		case String:
			clazz = String.class;
			break;
		default:
			break;
		}
		return clazz;
	}
	
	public static EditType getDefaultEditType(PropertyType type) {
		EditType editType = EditType.TEXT;
		switch (type) {
		case Boolean:
			editType = EditType.CHECKBOX;
			break;
		case Char:
			editType = EditType.TEXT;
			break;
		case Date:
			editType = EditType.DATE;
			break;
		case Double:
			editType = EditType.TEXT;
			break;
		case Duration:
			editType = EditType.DURATION;
			break;
		case Enum:
			editType = EditType.RADIO;
			break;
		case Integer:
			editType = EditType.TEXT;
			break;
		case List:
			editType = EditType.MULTISELECT;
			break;
		case Long:
			editType = EditType.TEXT;
			break;
		case MultiEnum:
			editType = EditType.MULTISELECT;
			break;
		case Object:
			editType = EditType.SELECT;
			break;
		case Short:
			editType = EditType.TEXT;
			break;
		case UserDefined:
			break;
		case String:
			editType = EditType.TEXT;
			break;
		default:
			break;
		}
		
		return editType;
	}
	
	public static String getValue(EntityObject entity, Property property) {
		String value = "";
		if (property.getMethod == null) {
			return value;
		}
		
		try {
			value = property.getMethod.invoke(entity).toString();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	public static void setValue(EntityObject entity, Property property, String str) {
		if (property.setMethod == null) {
			return;
		}
		Object value = null;
		switch (property.getType()) {
		case Boolean:
			value = Boolean.valueOf(str);
			break;
		case Short:
			value = Short.valueOf(str);
		default:
			break;
		}
		try {
			if (property.getInherent()) {
				property.setMethod.invoke(entity, value);
			} else {
				property.setMethod.invoke(entity, property.getName(), value);
			}
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取默认的Jackson序列化Mapper
	 * @return
	 */
	public static ObjectMapper defaultObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateJsonSerializer());
        module.addDeserializer(Date.class, new DateJsonDeserializer());
        module.addSerializer(java.sql.Date.class, new SqlDateJsonSerializer());
        module.addDeserializer(java.sql.Date.class, new SqlDateJsonDeserializer());
        mapper.addHandler(new SignFieldHandler());
        mapper.registerModule(module);
        return mapper;
	}
}
