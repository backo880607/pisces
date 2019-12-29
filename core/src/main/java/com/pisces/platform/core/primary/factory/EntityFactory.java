package com.pisces.platform.core.primary.factory;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.relation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class EntityFactory {
    private Class<? extends EntityObject> clazz;
    EntityFactory superFactory = null;
    List<EntityFactory> childFactories = new ArrayList<>();
    Map<Sign, RelationData> relations = null;
    Map<String, Sign> signsName = new HashMap<>();
    int maxSign = 0;

    public EntityFactory(Class<? extends EntityObject> value) {
        this.clazz = value;
    }

    @SuppressWarnings("unchecked")
    public <T extends EntityObject> Class<T> getEntityClass() {
        return (Class<T>) this.clazz;
    }

    public EntityFactory getSuperFactory() {
        return this.superFactory;
    }

    public List<EntityFactory> getChildFactories() {
        return this.childFactories;
    }

    public boolean validSign(Sign sign) {
        return this.relations != null && this.relations.get(sign) != null;
    }

    public RelationData getSignData(Sign sign) {
        if (validSign(sign)) {
            return this.relations.get(sign);
        }

        return this.superFactory != null ? this.superFactory.getSignData(sign) : null;
    }

    public RelationData getSignData(String signName) {
        Sign sign = this.getSign(signName);
        return sign != null ? this.getSignData(sign) : null;
    }

    public Sign getSign(String signName) {
        Sign sign = this.signsName.get(signName);
        if (sign != null) {
            return sign;
        }
        return this.superFactory != null ? this.superFactory.getSign(signName) : null;
    }

    public Sign getReverse(Sign sign) {
        RelationData data = getSignData(sign);
        return data != null ? data.getReverse() : null;
    }

    public Sign getReverse(String signName) {
        RelationData data = getSignData(signName);
        return data != null ? data.getReverse() : null;
    }

    public EntityFactory getRelationFactory(Sign sign) {
        RelationData data = getSignData(sign);
        return data != null ? data.getFactory() : null;
    }

    public EntityFactory getRelationFactory(String signName) {
        RelationData data = getSignData(signName);
        return data != null ? data.getFactory() : null;
    }

    public Type getRelationType(Sign sign) {
        RelationData data = getSignData(sign);
        return data != null ? data.getType() : null;
    }

    public RelationKind getRelationKind(Sign sign) {
        RelationData data = getSignData(sign);
        return data != null ? data.getKind() : null;
    }

    public List<Sign> getOwners() {
        List<Sign> result = new ArrayList<>();
        for (Entry<Sign, RelationData> entry : this.relations.entrySet()) {
            if (entry.getValue().owner) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

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
