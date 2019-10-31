package com.pisces.core.primary.expression.function;

import com.pisces.core.annotation.ELFunction;

class LogicFunction {

	static void register(FunctionManager manager) {
		manager.registerInnerFunction(LogicFunction.class);
	}
	
	@ELFunction
	public static Boolean funFalse() {
		return false;
	}
	
	@ELFunction
	public static Boolean funNot(Boolean param) {
		return !param;
	}
	
	@ELFunction
	public static Boolean funTrue() {
		return true;
	}
}
