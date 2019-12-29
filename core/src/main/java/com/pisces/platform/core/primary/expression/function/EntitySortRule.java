package com.pisces.platform.core.primary.expression.function;

import com.pisces.platform.core.entity.EntityObject;

import java.util.Comparator;
import java.util.Map;

public class EntitySortRule implements Comparator<EntityObject> {
    private Map<Long, Object> value;
    private boolean asc;

    public EntitySortRule(Map<Long, Object> sortValue, boolean bAsc) {
        this.value = sortValue;
        this.asc = bAsc;
    }

    @Override
    public int compare(EntityObject arg0, EntityObject arg1) {
        Object val1 = value.get(arg0.getId());
        Object val2 = value.get(arg1.getId());
        if (val1 == null && val2 == null) {
            return 0;
        }

        int result = 0;
        if (val1 == null && val2 != null) {
            result = -1;
        } else if (val1 != null && val2 == null) {
            result = 1;
        } else if ((boolean) BaseFunction.less(val1, val2)) {
            result = -1;
        } else if ((boolean) BaseFunction.greater(val1, val2)) {
            result = 1;
        }
        if (result == 0) {
            result = arg0.compareTo (arg1);
        }
        return asc ? result : -result;
    }
}
