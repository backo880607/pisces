package com.pisces.platform.core.primary.expression;

import com.pisces.platform.core.primary.expression.calculate.Calculate;

public class ExpressionNode {
    public OperType type = OperType.PLUS;
    public Calculate calculate = null;
    public ExpressionNode lchild = null;
    public ExpressionNode rchild = null;

    public ExpressionNode(OperType type, Calculate cal, ExpressionNode lc, ExpressionNode rc) {
        this.type = type;
        this.calculate = cal;
        this.lchild = lc;
        this.rchild = rc;
    }

    @SuppressWarnings("unchecked")
    public <T extends Calculate> T getCalculate() {
        return (T) this.calculate;
    }
}
