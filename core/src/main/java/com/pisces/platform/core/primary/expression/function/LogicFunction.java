package com.pisces.platform.core.primary.expression.function;

import com.pisces.platform.core.annotation.ELFunction;

class LogicFunction {

    protected LogicFunction() {}

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
