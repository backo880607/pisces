package com.pisces.platform.core.primary.expression.calculate;

import com.pisces.platform.core.entity.EntityObject;

import java.util.Collection;
import java.util.TreeMap;

public class ReverseCalculate implements Calculate {
    private TreeMap<EntityObject, Collection<EntityObject>> value = new TreeMap<>();

    @Override
    public Object getValue(EntityObject entity) {
        return this.value.get(entity);
    }

    @Override
    public int parse(String str, int index) {
        PropertyCalculate fieldCalculate = new PropertyCalculate();
        index = fieldCalculate.parse(str, index);
        if (index < 0) {
            return index;
        }

        this.value.clear();
		/*MemoryManager.foreach(fieldCalculate.entityCls, (ApsBaseEntity entity) -> {
			ValueAbstract valueAbstract = fieldCalculate.getValue(entity);
			if (valueAbstract == null || valueAbstract.valueType != ValueAbstract.ValueType.Object) {
				return;
			}
			
			ValueObject valueObject = (ValueObject)valueAbstract;
			List<ApsBaseEntity> listObjects = this.value.get(valueObject.value);
			if (listObjects == null) {
				listObjects = new ArrayList<>();
				this.value.put(valueObject.value, listObjects);
			}
			
			listObjects.add(entity);
		});*/

        return ++index;
    }

    @Override
    public Class<?> getReturnClass() {
        // TODO Auto-generated method stub
        return null;
    }
}
