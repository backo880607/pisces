package com.pisces.core.primary;

import java.util.ArrayList;
import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.Expression;
import com.pisces.core.primary.factory.EntityFactory;
import com.pisces.core.primary.factory.FactoryManager;
import com.pisces.core.relation.RelationKind;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;
import com.pisces.core.utils.IExpression;
import com.pisces.core.utils.Primary;

public class PrimaryImpl implements Primary {
	
	@Override
	public void init() {
		FactoryManager.init();
	}
	
	@Override
	public void createRelation(EntityObject entity) {
		if (FactoryManager.hasFactory(entity.getClass())) {
			FactoryManager.getFactory(entity.getClass()).createRelation(entity);
		}
	}

	@Override
	public IExpression createExpression(String str) {
		Expression exp = new Expression();
		return exp.Parse(str) ? exp : null;
	}
	
	@Override
	public Class<? extends EntityObject> getSuperClass(Class<? extends EntityObject> clazz) {
		EntityFactory superFactory = FactoryManager.getFactory(clazz).getSuperFactory();
		return superFactory != null ? superFactory.getEntityClass() : null;
	}

	@Override
	public Class<? extends EntityObject> getSuperClass(String name) {
		EntityFactory superFactory = FactoryManager.getFactory(name).getSuperFactory();
		return superFactory != null ? superFactory.getEntityClass() : null;
	}

	@Override
	public List<Class<? extends EntityObject>> getChildClasses(Class<? extends EntityObject> clazz) {
		List<Class<? extends EntityObject>> childClasses = new ArrayList<>();
		List<EntityFactory> childFactories = FactoryManager.getFactory(clazz).getChildFactories();
		for (EntityFactory childFactory : childFactories) {
			childClasses.add(childFactory.getEntityClass());
		}
		return childClasses;
	}
	
	@Override
	public Sign getRelationSign(Class<? extends EntityObject> clazz, String signName) {
		return FactoryManager.getFactory(clazz).getSign(signName);
	}

	@Override
	public Type getRelationType(Class<? extends EntityObject> clazz, Sign sign) {
		return FactoryManager.getFactory(clazz).getRelationType(sign);
	}
	
	@Override
	public RelationKind getRelationKind(Class<? extends EntityObject> clazz, Sign sign) {
		return FactoryManager.getFactory(clazz).getRelationKind(sign);
	}

	@Override
	public Sign getRelationReverse(Class<? extends EntityObject> clazz, Sign sign) {
		return FactoryManager.getFactory(clazz).getReverse(sign);
	}
	
	@Override
	public List<Sign> getRelationOwners(Class<? extends EntityObject> clazz) {
		return FactoryManager.getFactory(clazz).getOwners();
	}

	@Override
	public List<Sign> getRelationNotOwners(Class<? extends EntityObject> clazz) {
		return FactoryManager.getFactory(clazz).getNotOwners();
	}

	@Override
	public Class<? extends EntityObject> getRelationClass(Class<? extends EntityObject> clazz, Sign sign) {
		EntityFactory relaFactory = FactoryManager.getFactory(clazz).getRelationFactory(sign);
		return relaFactory != null ? relaFactory.getEntityClass() : null;
	}

	@Override
	public Class<? extends EntityObject> getRelationClass(Class<? extends EntityObject> clazz, String signName) {
		EntityFactory relaFactory = FactoryManager.getFactory(clazz).getRelationFactory(signName);
		return relaFactory != null ? relaFactory.getEntityClass() : null;
	}
}
