package com.pisces.platform.core.service;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;

import java.util.List;

public interface PropertyService extends EntityService<Property> {
    List<Property> get(Class<? extends EntityObject> clazz);

    Property get(Class<? extends EntityObject> clazz, String code);

    List<Property> getPrimaries(Class<? extends EntityObject> clazz);
}
