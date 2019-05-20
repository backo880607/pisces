package com.pisces.core.primary.expression.function;

import java.util.List;

import com.pisces.core.primary.expression.value.Type;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueBoolean;
import com.pisces.core.primary.expression.value.ValueInt;
import com.pisces.core.primary.expression.value.ValueNull;

/**
 * 逻辑类函数
 * @author niuhaitao
 *
 */
class LogicFunction {

	static void register(FunctionManager manager) {
		manager.registerFunction(LogicFunction.class, "AND");
		manager.registerFunction(LogicFunction.class, "FALSE");
		manager.registerFunction(LogicFunction.class, "NOT");
		manager.registerFunction(LogicFunction.class, "OR");
		manager.registerFunction(LogicFunction.class, "TRUE");
		manager.registerFunction(LogicFunction.class, "XOR");
	}
	
	/**
	 * 检查是否所有参数均为TRUE。
	 * @param params
	 * @return
	 */
	static ValueAbstract funAnd(List<ValueAbstract> params) {
		boolean result = false;
		for (ValueAbstract param : params) {
			if (param.getClass() == ValueNull.class || param.getType() != Type.BOOLEAN) {
				continue;
			}
			
			result = ((ValueBoolean)param).value;
			if (!result) {
				break;
			}
		}
		return new ValueBoolean(result);
	}
	
	/**
	 * 返回逻辑值FALSE。
	 * @param params
	 * @return
	 */
	static boolean funFalse() {
		return false;
	}
	
	/**
	 * 对参数的逻辑值求反。
	 * @param params
	 * @return
	 */
	static boolean funNot(boolean param) {
		return !param;
	}
	
	/**
	 * 如果任一参数为TRUE，则返回TRUE，否则返回FALSE。
	 * @param params
	 * @return
	 */
	static ValueAbstract funOr(List<ValueAbstract> params) {
		boolean result = false;
		for (ValueAbstract param : params) {
			if (param.getClass() == ValueNull.class || param.getType() != Type.BOOLEAN) {
				continue;
			}
			
			result = ((ValueBoolean)param).value;
			if (result) {
				break;
			}
		}
		
		return new ValueBoolean(result);
	}
	
	/**
	 * 返回逻辑值TRUE。
	 * @param params
	 * @return
	 */
	static boolean funTrue() {
		return true;
	}
	
	/**
	 * 返回所有参数的逻辑“异或”值。
	 * @param params
	 * @return
	 */
	static ValueAbstract funXor(List<ValueAbstract> params) {
		long result = 0;
		return new ValueInt(result);
	}
}
