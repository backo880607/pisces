package com.pisces.core.relation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.utils.Primary;

public class Ioc {
	
	//private static native int getRelationType(Class<?> cls, int sign);
	//private static native int getRelationReverseSign(Class<?> cls, int sign);
	//private static native Class<?> getEntityClass(Class<?> cls, int sign);
	//public static native Class<?> getEntityClassByName(Class<?> cls, String sign);

	public static <T extends EntityObject> T get(EntityObject entity, Sign sign) {
		return entity.getEntity(sign);
	}
	
	public static <T extends EntityObject> T get(EntityObject entity, Predicate<T> filter, Sign sign,
			Sign... args) {
		Collection<EntityObject> relaEntities = getList(entity, sign);
		for (EntityObject relaEntity : relaEntities) {
			T result = getImpl(relaEntity, filter, args, 0);
			if (result != null) {
				return result;
			}
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends EntityObject> T getImpl(EntityObject entity, Predicate<T> filter, Sign[] args,
			int index) {
		if (index >= args.length) {
			if (filter == null || filter.test((T) entity)) {
				return (T) entity;
			}
		} else {
			Collection<EntityObject> relaEntities = getList(entity, args[index]);
			for (EntityObject relaEntity : relaEntities) {
				T result = getImpl(relaEntity, filter, args, index + 1);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}
	
	public static <T extends EntityObject> Collection<T> getList(EntityObject entity, Sign sign) {
		return entity.getEntities(sign);
	}
	
	public static <T extends EntityObject> Collection<T> getList(EntityObject entity, Sign sign, Sign... args) {
		return getList(entity, null, sign, args);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends EntityObject> Collection<T> getList(EntityObject entity, Predicate<T> filter, Sign sign,
			Sign... args) {
		Collection<EntityObject> result = new RefSequence();
		Collection<EntityObject> relaEntities = getList(entity, sign);
		for (EntityObject relaEntity : relaEntities) {
			getListImpl(result, relaEntity, filter, args, 0);
		}

		return (Collection<T>) result;
	}
	
	public static <T extends EntityObject> List<T> getListEx(EntityObject entity, Sign sign, Class<T> cls) {
		List<T> result = new ArrayList<>();
		Collection<T> relaEntities = getList(entity, sign);
		for (T relaEntity : relaEntities) {
			if (relaEntity.getClass() == cls) {
				result.add(relaEntity);
			}
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends EntityObject> void getListImpl(Collection<EntityObject> result, EntityObject entity, Predicate<T> filter,
			Sign[] args, int index) {
		if (index >= args.length) {
			if (filter == null || filter.test((T) entity)) {
				result.add(entity);
			}
		} else {
			Collection<EntityObject> relaEntities = getList(entity, args[index]);
			for (EntityObject relaEntity : relaEntities) {
				getListImpl(result, relaEntity, filter, args, index + 1);
			}
		}
	}
	
	public static <T extends EntityObject> void foreach(EntityObject entity, Consumer<T> fun, Sign sign,
			Sign... args) {
		Collection<T> relaEntities = getList(entity, sign, args);
		for (T relaEntity : relaEntities) {
			fun.accept(relaEntity);
		}
	}
	
	public static void set(EntityObject entity, Sign sign, EntityObject relaEntity) {
		if (entity == null || relaEntity == null || sign == null) {
			return;
		}
		final Sign reverse = Primary.get().getRelationReverse(entity.getClass(), sign);
		switch (Primary.get().getRelationType(entity.getClass(), sign)) {
		case OneToOne:
			clear(entity, sign);
			clear(relaEntity, reverse);
			break;
		case OneToMulti:
			clear(relaEntity, reverse);
			break;
		case MultiToOne:
			clear(entity, sign);
			break;
		case MultiToMulti:
			break;
		default:
			break;
		}
		setImpl(entity, sign, relaEntity, reverse);
	}
	
	private static void setImpl(EntityObject entity, Sign sign, EntityObject relaEntity, Sign reverseSign) {
		entity.getEntities(sign).add(relaEntity);
		if (reverseSign != null) {
			relaEntity.getEntities(reverseSign).add(entity);
		}
	}
	
	public static void clear(EntityObject entity, Sign sign) {
		if (entity == null || sign == null) {
			return;
		}
		Sign reverse = Primary.get().getRelationReverse(entity.getClass(), sign);
		if (reverse != null) {
			for (EntityObject relaEntity : entity.getEntities(sign)) {
				relaEntity.getEntities(reverse).remove(entity);
			}
		}
		entity.getEntities(sign).clear();
	}
	
	public static void remove(EntityObject entity, Sign sign) {
		Sign reverse = Primary.get().getRelationReverse(entity.getClass(), sign);
		if (reverse != null) {
			for (EntityObject relaEntity : entity.getEntities(sign)) {
				if (relaEntity != null) {
					relaEntity.getEntities(reverse).remove(entity);
				}
			}
		}
		entity.getEntities(sign).clear();
	}
	
	public static void remove(EntityObject entity, Sign sign, EntityObject relaEntity) {
		Sign reverse = Primary.get().getRelationReverse(entity.getClass(), sign);
		if (reverse != null) {
			relaEntity.getEntities(reverse).remove(entity);
		}
		entity.getEntities(sign).remove(relaEntity);
	}
	
	public static void delete(EntityObject entity) {
		if (entity == null) {
			return;
		}
		
		List<Sign> ownerSigns = Primary.get().getRelationOwners(entity.getClass());
		for (Sign sign : ownerSigns) {
			List<EntityObject> relaEntities = new ArrayList<>(Ioc.getList(entity, sign));
			for (EntityObject relaEntity : relaEntities) {
				delete(relaEntity);
			}
		}
		
		List<Sign> notOwnerSigns = Primary.get().getRelationNotOwners(entity.getClass());
		for (Sign sign : notOwnerSigns) {
			Sign reverse = Primary.get().getRelationReverse(entity.getClass(), sign);
			if (reverse != null) {
				List<EntityObject> relaEntities = new ArrayList<>(Ioc.getList(entity, sign));
				for (EntityObject relaEntity : relaEntities) {
					relaEntity.getEntities(reverse).remove(entity);
				}
			}
		}
	}
	
	public static <T extends EntityObject> Class<T> getEntityClass(EntityObject entity, Sign sign) {
		return getEntityClass(entity.getClass(), sign);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends EntityObject> Class<T> getEntityClass(Class<? extends EntityObject> clazz, Sign sign) {
		return (Class<T>) Primary.get().getRelationClass(clazz, sign);
	}
	
	public static <T extends EntityObject> void recursion(EntityObject entity, Predicate<T> fun, Sign... args) {
		if (args.length == 0) {
			return;
		}
		recursionImpl(entity, fun, args, 0);
	}
	
	@SuppressWarnings("unchecked")
	private final static <T extends EntityObject> void recursionImpl(EntityObject entity, Predicate<T> fun,
			Sign[] args, int index) {
		if (index >= args.length) {
			if (!fun.test((T) entity)) {
				return;
			}
			index = 0;
		}

		Collection<EntityObject> relaEntities = getList(entity, args[index]);
		for (EntityObject relaEntity : relaEntities) {
			recursionImpl(relaEntity, fun, args, index + 1);
		}
	}
	
	private static <T extends EntityObject> LinkedList<T> compareImpl(EntityObject entity, Predicate<T> filter,
			Comparator<T> comp, Sign sign, Sign... args) {
		LinkedList<T> entities = (LinkedList<T>) getList(entity, filter, sign, args);
		entities.sort(comp);
		return entities;
	}
	
	public static <T extends EntityObject> T getMax(EntityObject entity, Comparator<T> comp, Sign sign,
			Sign... args) {
		return getMax(entity, null, comp, sign, args);
	}
	
	public static <T extends EntityObject> T getMax(EntityObject entity, Predicate<T> filter, Comparator<T> comp,
			Sign sign, Sign... args) {
		LinkedList<T> entities = compareImpl(entity, filter, comp, sign, args);
		return entities.isEmpty() ? null : entities.getLast();
	}
	
	public static <T extends EntityObject> T getMin(EntityObject entity, Comparator<T> comp, Sign sign,
			Sign... args) {
		return getMin(entity, null, comp, sign, args);
	}
	
	public static <T extends EntityObject> T getMin(EntityObject entity, Predicate<T> filter, Comparator<T> comp,
			Sign sign, Sign... args) {
		LinkedList<T> entities = compareImpl(entity, filter, comp, sign, args);
		return entities.isEmpty() ? null : entities.getFirst();
	}
}
