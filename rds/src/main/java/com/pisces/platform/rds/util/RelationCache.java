package com.pisces.platform.rds.util;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.relation.Sign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelationCache {
    private Map<Class<? extends EntityObject>, Map<EntityObject, Map<Sign, List<Long>>>> datas = new HashMap<>();

    private Map<Sign, List<Long>> getEntityData(EntityObject entity) {
        Map<EntityObject, Map<Sign, List<Long>>> classData = datas.get(entity.getClass());
        if (classData == null) {
            classData = new HashMap<>();
            datas.put(entity.getClass(), classData);
        }
        Map<Sign, List<Long>> entityData = classData.get(entity);
        if (entityData == null) {
            entityData = new HashMap<>();
            classData.put(entity, entityData);
        }
        return entityData;
    }

    public void add(EntityObject entity, Sign sign, Long id) {
        if (id == null) {
            return;
        }

        Map<Sign, List<Long>> entityData = getEntityData(entity);
        List<Long> data = entityData.get(sign);
        if (data == null) {
            data = new ArrayList<>();
            entityData.put(sign, data);
        }
        data.add(id);
    }

    public void add(EntityObject entity, Sign sign, List<Long> ids) {
        if (ids == null) {
            return;
        }
        getEntityData(entity).put(sign, ids);
    }

    public Map<Class<? extends EntityObject>, Map<EntityObject, Map<Sign, List<Long>>>> getDatas() {
        return this.datas;
    }
}
