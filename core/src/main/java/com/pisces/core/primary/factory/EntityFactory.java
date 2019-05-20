package com.pisces.core.primary.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.RefBase;
import com.pisces.core.relation.RefList;
import com.pisces.core.relation.RefSequence;
import com.pisces.core.relation.RefSet;
import com.pisces.core.relation.RefSingleton;
import com.pisces.core.relation.RelationKind;
import com.pisces.core.relation.RelationMap;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;

public final class EntityFactory {
	private Class<? extends EntityObject> clazz;
	EntityFactory superFactory = null;
	List<EntityFactory> childFactories = new ArrayList<>();
	Map<Sign, RelationData> relations = null;
	Map<String, Sign> signsName = new HashMap<>();
	int maxSign = 0;
	
	public EntityFactory(Class<? extends EntityObject> clazz) {
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends EntityObject> Class<T> getEntityClass() {
		return (Class<T>)this.clazz;
	}
	
	public EntityFactory getSuperFactory() {
		return this.superFactory;
	}
	
	public List<EntityFactory> getChildFactories() {
		return this.childFactories;
	}
	
	/**
	 * 是否为有效关联标识
	 * @param sign
	 * @return
	 */
	public boolean validSign(Sign sign) {
		return this.relations != null && this.relations.get(sign) != null;
	}
	
	/**
	 * 由关联标识，取关联数据
	 * @param sign
	 * @return
	 */
	public RelationData getSignData(Sign sign) {
		if (validSign(sign)) {
			return this.relations.get(sign);
		}
		
		return this.superFactory != null ? this.superFactory.getSignData(sign) : null;
	}
	
	/**
	 * 由关联名，取关联数据
	 * @param signName
	 * @return
	 */
	public RelationData getSignData(String signName) {
		Sign sign = this.getSign(signName);
		return sign != null ? this.getSignData(sign) : null;
	}
	
	/**
	 * 由关联名，取关联标识
	 * @param signName
	 * @return
	 */
	public Sign getSign(String signName) {
		Sign sign = this.signsName.get(signName);
		if (sign != null) {
			return sign;
		}
		return this.superFactory != null ? this.superFactory.getSign(signName) : null;
	}
	
	/**
	 * 由关联标识，获取反向关联标识
	 * @param sign
	 * @return
	 */
	public Sign getReverse(Sign sign) {
		RelationData data = getSignData(sign);
		return data != null ? data.getReverse() : null;
	}
	
	/**
	 * 由关联名，获取反向关联标识
	 * @param signName
	 * @return
	 */
	public Sign getReverse(String signName) {
		RelationData data = getSignData(signName);
		return data != null ? data.getReverse() : null;
	}
	
	/**
	 * 由关联标识，获取关联对象的工厂
	 * @param sign
	 * @return
	 */
	public EntityFactory getRelationFactory(Sign sign) {
		RelationData data = getSignData(sign);
		return data != null ? data.getFactory() : null;
	}
	
	/**
	 * 由关联名，获取关联对象的工厂
	 * @param signName
	 * @return
	 */
	public EntityFactory getRelationFactory(String signName) {
		RelationData data = getSignData(signName);
		return data != null ? data.getFactory() : null;
	}
	
	/**
	 * 由关联标识，获取关联类型
	 * @param sign
	 * @return
	 */
	public Type getRelationType(Sign sign) {
		RelationData data = getSignData(sign);
		return data != null ? data.getType() : null;
	}
	
	/**
	 * 由关联标识，获取关联对象的数据类型
	 * @param sign
	 * @return
	 */
	public RelationKind getRelationKind(Sign sign) {
		RelationData data = getSignData(sign);
		return data != null ? data.getKind() : null;
	}
	
	/**
	 * 获取拥有关联标识
	 * @return
	 */
	public List<Sign> getOwners() {
		List<Sign> result = new ArrayList<>();
		for (Entry<Sign, RelationData> entry : this.relations.entrySet()) {
			if (entry.getValue().owner) {
				result.add(entry.getKey());
			}
		}
		
		return result;
	}
	
	/**
	 * 获取非拥有关联标识
	 * @return
	 */
	public List<Sign> getNotOwners() {
		List<Sign> result = new ArrayList<>();
		for (Entry<Sign, RelationData> entry : this.relations.entrySet()) {
			if (!entry.getValue().owner) {
				result.add(entry.getKey());
			}
		}
		
		return result;
	}
	
	public void createRelation(EntityObject entity) {
		if (this.maxSign == 0) {
			return;
		}
		Map<Sign, RefBase> relaData = entity.getRelations();
		if (relaData == null) {
			relaData = new RelationMap<>(this.maxSign);
			entity.setRelations(relaData);
		}
		for (Entry<Sign, RelationData> entry : this.relations.entrySet()) {
			RefBase ref = null;
			switch (entry.getValue().getKind()) {
			case List:
				ref = new RefList();
				break;
			case Sequence:
				ref = new RefSequence();
				break;
			case Set:
				ref = new RefSet();
				break;
			case Singleton:
				ref = new RefSingleton();
				break;
			default:
				break;
			}
			relaData.put(entry.getKey(), ref);
		}
		
		if (getSuperFactory() != null) {
			getSuperFactory().createRelation(entity);
		}
	}
}
