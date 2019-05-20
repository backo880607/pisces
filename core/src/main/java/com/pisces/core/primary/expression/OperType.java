package com.pisces.core.primary.expression;

public enum OperType {
	DATA(0), 
	LPAREN(1), // 左圆括号
	RPAREN(1), // 右圆括号
	LSQUARE(1),	// 左方括号
	RSQUARE(1),	// 右方括号
	POINT(1),		// 点，成员选择（对象）
	NEGATIVE(2),	// 负号
	SELFINCRE(2),	// 自增运算符
	NOT(2), 	// 非
	MULTIPLIED(3), 	// 乘
	DIVIDED(3), 	// 除
	MOD(3),			// 取模
	PLUS(4), 	// 加
	MINUS(4), // 减
	GREATER(6), // 大于
	GREATEREQUAL(6), // 大于等于
	LESS(6), // 小于
	LESSEQUAL(6), // 小于等于
	EQUAL(7), // 等于
	NOTEQUAL(7), // 不等于
	AND(11), // 且
	OR(12), // 或
	FUN(16), // 函数
	EOL(17); // 非法
	
	private int _value;
	
	OperType(int value){
		_value = value;
	}
	
	public int value() {
        return this._value;
    }
}
