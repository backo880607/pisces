package com.pisces.core.primary.expression.function;

import com.pisces.core.annotation.ELFunction;

/**
 * 逻辑类函数
 * @author niuhaitao
 *
 */
class LogicFunction {

	static void register(FunctionManager manager) {
		manager.registerUserFunction(LogicFunction.class);
	}
	
	/**
	 * 返回逻辑值FALSE。
	 * @param params
	 * @return
	 */
	@ELFunction
	public static Boolean funFalse() {
		return false;
	}
	
	/**
	 * 对参数的逻辑值求反。
	 * @param params
	 * @return
	 */
	@ELFunction
	public static Boolean funNot(Boolean param) {
		return !param;
	}
	
	/**
	 * 返回逻辑值TRUE。
	 * @param params
	 * @return
	 */
	@ELFunction
	public static Boolean funTrue() {
		return true;
	}
}
