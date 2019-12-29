package com.pisces.platform.core.primary.expression.function;

import com.pisces.platform.core.annotation.ELFunction;
import com.pisces.platform.core.annotation.ELParm;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.utils.DateUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

class InternalFunction extends BaseFunction {
    static void register(FunctionManager manager) {
        manager.registerInnerFunction(InternalFunction.class);
    }

    @ELFunction
    public static Boolean include(String param1, String param2) {
        return param1.indexOf(param2) >= 0;
    }

    @ELFunction
    public static Boolean startsWith(String param1, String param2) {
        return param1.startsWith(param2);
    }

    @ELFunction
    public static Boolean endsWith(String param1, String param2) {
        return param1.endsWith(param2);
    }

    @ELFunction(flexible = true)
    public static Boolean isInt(Object param) {
        return param.getClass() == Long.class;
    }

    @ELFunction(flexible = true)
    public static Boolean isDouble(Object param) {
        return param.getClass() == Double.class;
    }

    @ELFunction
    public static Boolean isDigit(Object param) {
        return param.getClass() == Long.class || param.getClass() == Double.class;
    }

    @ELFunction
    public static Boolean isDateTime(Object param) {
        return param.getClass() == Date.class;
    }

    @ELFunction
    public static Boolean isBool(Object param) {
        return param.getClass() == Boolean.class;
    }

    @ELFunction
    public static Boolean isStr(Object param) {
        return param.getClass() == String.class;
    }

    @SuppressWarnings("unchecked")
    @ELFunction
    public static Boolean isEmpty(@ELParm(nullable = true) Object value) {
        boolean result = true;
        if (value == null) {
            result = true;
        } else if (value.getClass() == String.class) {
            result = ((String) value).isEmpty();
        } else if (value.getClass() == Date.class) {
            result = ((Date) value).equals(DateUtils.INVALID);
        } else if (value.getClass() == Collection.class) {
            result = ((Collection<EntityObject>) value).isEmpty();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @ELFunction(flexible = true)
    public static Object max(Object param1, Object param2, Object... values) {
        Object result = null;
        if (param1.getClass() == Collection.class) {
            if (param2.getClass() == Map.class) {
                Map<Long, Object> fieldValues = (Map<Long, Object>) param2;
                Map<Long, Object> filter = null;
                if (values.length > 0) {
                    filter = (Map<Long, Object>) values[0];
                }

                Collection<EntityObject> entities = EntityFunction.funGetSortedList((Collection<EntityObject>) param1, fieldValues, false, filter);
                return entities.isEmpty() ? null : fieldValues.get(entities.iterator().next().getId());
            }
        } else {
            result = (boolean) less(param1, param2) ? param2 : param1;
            for (Object context : values) {
                if ((boolean) less(result, context)) {
                    result = context;
                }
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @ELFunction(flexible = true)
    static Object min(Object param1, Object param2, Object... values) {
        Object result = null;
        if (param1.getClass() == Collection.class) {
            if (param2.getClass() == Map.class) {
                Map<Long, Object> fieldValues = (Map<Long, Object>) param2;
                Map<Long, Object> filter = null;
                if (values.length > 0) {
                    filter = (Map<Long, Object>) values[0];
                }

                Collection<EntityObject> entities = EntityFunction.funGetSortedList((Collection<EntityObject>) param1, fieldValues, true, filter);
                return entities.isEmpty() ? null : fieldValues.get(entities.iterator().next().getId());
            }
        } else {
            result = (boolean) greater(param1, param2) ? param2 : param1;
            for (Object context : values) {
                if ((boolean) greater(result, context)) {
                    result = context;
                }
            }
        }

        return result;
    }

    @ELFunction
    static int count(Collection<EntityObject> entities, Map<Long, Object> filter) {
        int count = 0;
        if (filter == null) {
            count = entities.size();
        } else {
            for (Entry<Long, Object> context : filter.entrySet()) {
                if (context.getValue().getClass() == Boolean.class && (boolean) context.getValue()) {
                    ++count;
                }
            }
        }

        return count;
    }

    @ELFunction(flexible = true)
    static Object funAverage(Object param1, Object param2, Object... values) {
        Object result = null;
        if (param1.getClass() == Collection.class) {
            if (param2.getClass() == Map.class) {
                @SuppressWarnings("unchecked")
                Map<Long, Object> context = (Map<Long, Object>) param2;
                for (Entry<Long, Object> entry : context.entrySet()) {
                    result = result == null ? entry.getValue() : plus(result, entry.getValue());
                }

                result = division(result, context.size());
            }
        } else {
            result = plus(param1, param2);
            for (Object context : values) {
                result = plus(result, context);
            }
            result = division(result, 2 + values.length);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @ELFunction(flexible = true)
    static Object funSum(Object param1, Object param2, Object... values) {
        Object result = null;
        if (param1.getClass() == Collection.class) {
            if (param2.getClass() == Map.class) {
                for (Entry<Long, Object> entry : ((Map<Long, Object>) param2).entrySet()) {
                    result = result == null ? entry.getValue() : plus(result, entry.getValue());
                }
            }
        } else {
            result = plus(param1, param2);
            for (Object context : values) {
                result = plus(result, context);
            }
        }

        return result;
    }

    @ELFunction
    static Object guard(@ELParm(nullable = true) Object param1, Object param2) {
        if (param1 == null) {
            return param2;
        } else if (param1.getClass() == String.class && ((String) param1).isEmpty()) {
            return param2;
        }

        return param1;
    }
}
