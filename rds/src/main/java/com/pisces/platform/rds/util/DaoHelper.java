package com.pisces.platform.rds.util;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.core.service.ServiceManager;
import com.pisces.platform.core.utils.AppUtils;
import com.pisces.platform.core.utils.Primary;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DaoHelper {

    public void buildRelation() {
        RelationCache cache = AppUtils.getUserData(RelationCache.class);
        for (Entry<Class<? extends EntityObject>, Map<EntityObject, Map<Sign, List<Long>>>> entryClazz : cache.getDatas().entrySet()) {
            Class<? extends EntityObject> clazz = entryClazz.getKey();
            for (Entry<EntityObject, Map<Sign, List<Long>>> entryEntity : entryClazz.getValue().entrySet()) {
                EntityObject entity = entryEntity.getKey();
                for (Entry<Sign, List<Long>> entryData : entryEntity.getValue().entrySet()) {
                    Class<? extends EntityObject> relaClazz = Primary.get().getRelationClass(clazz, entryData.getKey());
                    EntityService<? extends EntityObject> relaService = ServiceManager.getService(relaClazz);
                    if (relaService == null) {
                        continue;
                    }
                    for (Long id : entryData.getValue()) {
                        if (id == null) {
                            continue;
                        }
                        EntityObject relaEntity = relaService.getById(id);
                        entity.set(entryData.getKey(), relaEntity);
                    }
                }
            }
        }
    }

}
