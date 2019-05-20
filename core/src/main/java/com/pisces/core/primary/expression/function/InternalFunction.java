package com.pisces.core.primary.expression.function;

import java.util.Collection;
import java.util.List;

import com.pisces.core.annotation.ELFunction;
import com.pisces.core.exception.OperandException;
import com.pisces.core.primary.expression.value.*;
import com.pisces.core.relation.RefBase;

class InternalFunction {
	static void register(FunctionManager manager) {
		manager.registerFunction(InternalFunction.class, "toStr");
		manager.registerFunction(InternalFunction.class, "toInt");
		manager.registerFunction(InternalFunction.class, "toDouble");
		manager.registerFunction(InternalFunction.class, "include");
		manager.registerFunction(InternalFunction.class, "startsWith");
		manager.registerFunction(InternalFunction.class, "endsWith");
		manager.registerFunction(InternalFunction.class, "isInt");
		manager.registerFunction(InternalFunction.class, "isDouble");
		manager.registerFunction(InternalFunction.class, "isDigit");
		manager.registerFunction(InternalFunction.class, "isBool");
		manager.registerFunction(InternalFunction.class, "isDateTime");
		manager.registerFunction(InternalFunction.class, "isStr");
		manager.registerFunction(InternalFunction.class, "isEmpty");
		manager.registerFunction(InternalFunction.class, "max");
		manager.registerFunction(InternalFunction.class, "min");
		manager.registerFunction(InternalFunction.class, "count");
		manager.registerFunction(InternalFunction.class, "average");
		manager.registerFunction(InternalFunction.class, "sum");
		manager.registerFunction(InternalFunction.class, "guard");
	}

	/**
	 * 加法
	 * @param params
	 * @return
	 */
	static ValueAbstract add(List<ValueAbstract> params) {
		return params.get(0).add(params.get(1));
	}
	
	/**
	 * 减法
	 * @param params
	 * @return
	 */
	static ValueAbstract sub(List<ValueAbstract> params) {
		return params.get(0).sub(params.get(1));
	}
	
	/**
	 * 乘法
	 * @param params
	 * @return
	 */
	static ValueAbstract multiply(List<ValueAbstract> params) {
		return params.get(0).multiply(params.get(1));
	}
	
	/**
	 * 除法
	 * @param params
	 * @return
	 */
	static ValueAbstract division(List<ValueAbstract> params) {
		return params.get(0).division(params.get(1));
	}
	
	/**
	 * 大于
	 * @param params
	 * @return
	 */
	static ValueAbstract greater(List<ValueAbstract> params) {
		return params.get(0).greater(params.get(1));
	}
	
	/**
	 * 大于等于
	 * @param params
	 * @return
	 */
	static ValueAbstract greaterEqual(List<ValueAbstract> params) {
		return params.get(0).greaterEqual(params.get(1));
	}
	
	/**
	 * 小于
	 * @param params
	 * @return
	 */
	static ValueAbstract less(List<ValueAbstract> params) {
		return params.get(0).less(params.get(1));
	}
	
	/**
	 * 小于等于
	 * @param params
	 * @return
	 */
	static ValueAbstract lessEqual(List<ValueAbstract> params) {
		return params.get(0).lessEqual(params.get(1));
	}
	
	/**
	 * 等于
	 * @param params
	 * @return
	 */
	static ValueAbstract equal(List<ValueAbstract> params) {
		return params.get(0).equal(params.get(1));
	}
	
	/**
	 * 不等于
	 * @param params
	 * @return
	 */
	static ValueAbstract notEqual(List<ValueAbstract> params) {
		return params.get(0).notEqual(params.get(1));
	}
	
	/**
	 * 且
	 * @param params
	 * @return
	 */
	static ValueAbstract and(List<ValueAbstract> params) {
		if (params.get(0).getType() != Type.BOOLEAN ||
			params.get(1).getType() != Type.BOOLEAN) {
			throw new OperandException(params.get(0) + " && " + params.get(1) + " is not supported!");
		}
		boolean result = ((ValueBoolean)params.get(0)).value && ((ValueBoolean)params.get(1)).value;
		return new ValueBoolean(result);
	}
	
	/**
	 * 或
	 * @param params
	 * @return
	 */
	static ValueAbstract or(List<ValueAbstract> params) {
		ValueAbstract param1 = params.get(0);
		ValueAbstract param2 = params.get(1);
		if (param1.getType() != Type.BOOLEAN || 
			param2.getType() != Type.BOOLEAN) {
			throw new OperandException(params.get(0) + " || " + params.get(1) + " is not supported!");
		}
		
		boolean result = ((ValueBoolean)param1).value && ((ValueBoolean)param2).value;
		return new ValueBoolean(result);
	}
	
	/**
	 * 非
	 * @param params
	 * @return
	 */
	static ValueAbstract not(List<ValueAbstract> params) {
		if (params.get(0).getType() != Type.BOOLEAN) {
			throw new OperandException(" ! " + params.get(0) + " is not supported!");
		}
		return new ValueBoolean(!((ValueBoolean)params.get(1)).value);
	}
	
	/**
	 * 转换为String
	 * @param params
	 * @return
	 */
	static ValueAbstract toStr(ValueAbstract param) {
		return param.toText();
	}
	
	/**
	 * 转换为Int
	 * @param params
	 * @return
	 */
	static ValueAbstract toInt(ValueAbstract param) {
		return param.toInt();
	}
	
	/**
	 * 转换为Double
	 * @param params
	 * @return
	 */
	static ValueAbstract toDouble(ValueAbstract param) {
		return param.toDouble();
	}
	
	/**
	 * 第一参数内容是否包含第二参数内容
	 * @param params
	 * @return
	 */
	static boolean include(String param1, String param2) {
		return param1.indexOf(param2) >= 0;
	}
	
	/**
	 * 第一参数内容是否包含第二参数内容
	 * @param params
	 * @return
	 */
	static boolean startsWith(String param1, String param2) {
		return param1.startsWith(param2);
	}
	
	/**
	 * 第一参数内容是否包含第二参数内容
	 * @param params
	 * @return
	 */
	static boolean endsWith(String param1, String param2) {
		return param1.endsWith(param2);
	}
	
	/**
	 * 是否为整型
	 * @param params
	 * @return
	 */
	static ValueAbstract isInt(ValueAbstract param) {
		return new ValueBoolean(param.getType() == Type.LONG);
	}
	
	/**
	 * 是否为浮点型
	 * @param params
	 * @return
	 */
	static ValueAbstract isDouble(ValueAbstract param) {
		return new ValueBoolean(param.getType() == Type.DOUBLE);
	}
	
	/**
	 * 是否为数字
	 * @param params
	 * @return
	 */
	static ValueAbstract isDigit(ValueAbstract param) {
		return new ValueBoolean(param.getType() == Type.LONG || param.getType() == Type.DOUBLE);
	}
	
	/**
	 * 是否为日期类型
	 * @param params
	 * @return
	 */
	static ValueAbstract isDateTime(ValueAbstract param) {
		return new ValueBoolean(param.getType() == Type.DATETIME);
	}
	
	/**
	 * 是否为布尔型
	 * @param params
	 * @return
	 */
	static ValueAbstract isBool(ValueAbstract param) {
		return new ValueBoolean(param.getType() == Type.BOOLEAN);
	}
	
	/**
	 * 是否为字符串
	 * @param params
	 * @return
	 */
	static ValueAbstract isStr(ValueAbstract param) {
		return new ValueBoolean(param.getType() == Type.TEXT);
	}
	
	/**
	 * 是否为空
	 * @param params
	 * @return
	 */
	static ValueAbstract isEmpty(ValueAbstract param) {
		return new ValueBoolean(param.getType() == Type.EnumType);
	}
	
	/**
	 * 取最大值
	 * @param params
	 * @return
	 */
	static ValueAbstract max(List<ValueAbstract> params) {
		ValueAbstract result = null;
		if (params.get(0).getType() == Type.List) {
			result = ObjectFunction.funGetSortedList(params.get(0), params.get(1), params.get(2));
			RefBase entities = (RefBase)result.getValue();
			return ((ValueListAbstract)params.get(1)).value.get(entities.last().getId());
		} else {
			for (ValueAbstract context : params) {
				if (context.getClass() == ValueNull.class) {
					continue;
				}
				
				if (result == null) {
					result = context;
				} else {
					if (result.less(context).value) {
						result = context;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 取最小值
	 * @param params
	 * @return
	 */
	static ValueAbstract min(List<ValueAbstract> params) {
		ValueAbstract result = null;
		if (params.get(0).getType() == Type.List) {
			result = ObjectFunction.funGetSortedList(params.get(0), params.get(1), params.get(2));
			RefBase entities = (RefBase)result.getValue();
			return ((ValueListAbstract)params.get(1)).value.get(entities.first().getId());
		} else {
			for (ValueAbstract context : params) {
				if (context.getClass() == ValueNull.class) {
					continue;
				}
				
				if (result == null) {
					result = context;
				} else {
					if (result.greater(context).value) {
						result = context;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 返回对象集合中对象的个数。
	 * @param params
	 * @return
	 */
	static ValueAbstract count(ValueAbstract param1, ValueAbstract param2) {
		int count = 0;
		if (param2.getClass() == ValueNull.class) {
			count = ((ValueList)param1).value.size();
		} else {
			Collection<ValueAbstract> values = ((ValueListAbstract)param2).value.values();
			for (ValueAbstract context : values) {
				if (context.getClass() != ValueNull.class && context.getType() == Type.BOOLEAN &&
						((ValueBoolean)context).value) {
					++count;
				}
			}
		}
		
		return new ValueInt(count);
	}
	
	/**
	 * 返回平均值，第一参数为对象列表，则对对象集合求平均值。
	 * @param params
	 * @return
	 */
	@ELFunction(flexible = true)
	static ValueAbstract funAverage(List<ValueAbstract> params) {
		Collection<ValueAbstract> values = params;
		if (params.get(0).getType() == Type.List) {
			ValueAbstract param2 = params.get(1);
			if (param2.getType() == Type.ListAbstract) {
				values = ((ValueListAbstract)param2).value.values();
			}
		}
		
		ValueAbstract result = null;
		final int count = values.size();
		for (ValueAbstract context : values) {
			if (context.getClass() == ValueNull.class) {
				continue;
			}
			if (result == null) {
				result = context;
			} else {
				result = result.add(context);
			}
		}
		
		return result != null ? result.division(new ValueInt(count)) : null;
	}
	
	/**
	 * 求和，第一参数为对象列表，则对对象集合求和。
	 * @param params
	 * @return
	 */
	@ELFunction(flexible = true)
	static ValueAbstract funSum(List<ValueAbstract> params) {
		Collection<ValueAbstract> values = params;
		if (params.get(0).getType() == Type.List) {
			ValueAbstract param2 = params.get(1);
			if (param2.getType() == Type.ListAbstract) {
				values = ((ValueListAbstract)param2).value.values();
			}
		}
		ValueAbstract result = null;
		for (ValueAbstract context : values) {
			if (context.getClass() == ValueNull.class) {
				continue;
			}
			if (result == null) {
				result = context;
			} else {
				result = result.add(context);
			}
		}
		
		return result;
	}
	
	/**
	 * Guard函数，如果第一参数为null则返回第二参数，否则返回第一参数。
	 * @param params
	 * @return
	 */
	static ValueAbstract guard(ValueAbstract param1, ValueAbstract param2) {
		if (param1.getClass() == ValueNull.class) {
			return param2;
		} else if (param1.getType() == Type.TEXT && ((ValueText)param1).value.isEmpty()) {
			return param2;
		}
		
		return param1;
	}
}
