package com.pisces.core.primary.expression.function;

import com.pisces.core.exception.OperandException;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.primary.expression.value.ValueDouble;
import com.pisces.core.primary.expression.value.ValueInt;
import com.pisces.core.primary.expression.value.ValueNull;

class MathFunction {
	
	static void register(FunctionManager manager) {
		manager.registerFunction(MathFunction.class, "ABS");
		manager.registerFunction(MathFunction.class, "SIN");
		manager.registerFunction(MathFunction.class, "SINH");
		manager.registerFunction(MathFunction.class, "COS");
		manager.registerFunction(MathFunction.class, "TAN");
		manager.registerFunction(MathFunction.class, "TANH");
		manager.registerFunction(MathFunction.class, "ASIN");
		manager.registerFunction(MathFunction.class, "ACOS");
		manager.registerFunction(MathFunction.class, "ATAN");
		manager.registerFunction(MathFunction.class, "CBRT");
		manager.registerFunction(MathFunction.class, "CEIL");
		manager.registerFunction(MathFunction.class, "EXP");
		manager.registerFunction(MathFunction.class, "FLOOR");
		manager.registerFunction(MathFunction.class, "LOG");
		manager.registerFunction(MathFunction.class, "LOG10");
		manager.registerFunction(MathFunction.class, "LOG1P");
		manager.registerFunction(MathFunction.class, "POW");
		manager.registerUserFunction(MathFunction.class, "RANDOM");
		manager.registerFunction(MathFunction.class, "ROUND");
		manager.registerFunction(MathFunction.class, "SIGNUM");
		manager.registerFunction(MathFunction.class, "SQRT");
		manager.registerFunction(MathFunction.class, "TODEGREES");
		manager.registerFunction(MathFunction.class, "TORADIANS");
	}
	
	/**
	 * 取绝对值
	 * @param params
	 * @return
	 */
	static ValueAbstract funAbs(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueInt(Math.abs(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.abs(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("abs is not supported for " + param.getValue());
	}
	
	/**
	 * 对弧度取sin(三角正弦)
	 * @param params
	 * @return
	 */
	static ValueAbstract funSin(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.sin(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.sin(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("sin is not supported for " + param.getValue());
	}
	
	/**
	 * 对弧度取sinh(双曲正弦)
	 * @param params
	 * @return
	 */
	static ValueAbstract funSinh(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.sinh(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.sinh(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("sinh is not supported for " + param.getValue());
	}
	
	/**
	 * 对弧度取cos(三角余弦)
	 * @param params
	 * @return
	 */
	static ValueAbstract funCos(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.cos(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.cos(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("cos is not supported for " + param.getValue());
	}
	
	/**
	 * 对弧度取tan(三角正切)
	 * @param params
	 * @return
	 */
	static ValueAbstract funTan(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.tan(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.tan(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("tan is not supported for " + param.getValue());
	}
	
	/**
	 * 对弧度取tanh(双曲正切)
	 * @param params
	 * @return
	 */
	static ValueAbstract funTanh(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.tanh(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.tanh(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("tanh is not supported for " + param.getValue());
	}
	
	/**
	 * 对值取弧度asin(反正弦)
	 * @param params
	 * @return
	 */
	static ValueAbstract funASin(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.asin(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.asin(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("asin is not supported for " + param.getValue());
	}
	
	/**
	 * 对值取弧度acos(反余弦)
	 * @param params
	 * @return
	 */
	static ValueAbstract funACos(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.acos(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.acos(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("acos is not supported for " + param.getValue());
	}
	
	/**
	 * 对值取弧度atan(反正切)
	 * @param params
	 * @return
	 */
	static ValueAbstract funATan(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.atan(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.atan(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("atan is not supported for " + param.getValue());
	}
	
	/**
	 * 将弧度转换为角度
	 * @param params
	 * @return
	 */
	static ValueAbstract funToDegrees(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.toDegrees(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.toDegrees(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("toDegrees is not supported for " + param.getValue());
	}
	
	/**
	 * 将角度转换为弧度
	 * @param params
	 * @return
	 */
	static ValueAbstract funToRadians(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.toRadians(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.toRadians(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("toRadians is not supported for " + param.getValue());
	}
	
	/**
	 * 返回欧拉数 e 的n次幂
	 * @param params
	 * @return
	 */
	static ValueAbstract funExp(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.exp(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.exp(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("exp is not supported for " + param.getValue());
	}
	
	/**
	 * 计算自然对数
	 * @param params
	 * @return
	 */
	static ValueAbstract funLog(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.log(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.log(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("log is not supported for " + param.getValue());
	}
	
	/**
	 * 计算底数为10的对数
	 * @param params
	 * @return
	 */
	static ValueAbstract funLog10(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.log10(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.log10(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("log10 is not supported for " + param.getValue());
	}
	
	/**
	 * 返回参数与1之和的自然对数
	 * @param params
	 * @return
	 */
	static ValueAbstract funLog1p(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.log1p(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.log1p(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("log1p is not supported for " + param.getValue());
	}
	
	/**
	 * 取整，返回小于目标数的最大整数
	 * @param params
	 * @return
	 */
	static ValueAbstract funFloor(ValueAbstract param1, ValueAbstract param2) {
		double digits = 0;
		if (param2.getClass() != ValueNull.class) {
			switch (param2.getType()) {
			case LONG:
				digits = ((ValueInt)param2).value;
				break;
			case DOUBLE:
				digits = ((ValueDouble)param2).value;
				break;
			default:
				return null;
			}
		}
		digits = Math.pow(10.0, digits);
		double value = 0.0;
		switch (param1.getType()) {
		case LONG:
			value = ((ValueInt)param1).value;
			break;
		case DOUBLE:
			value = ((ValueDouble)param1).value;
			break;
		default:
			return null;
		}
		
		value = Math.floor(value*digits) / digits;
		return param2.getClass() == ValueNull.class ? new ValueInt((long)value) : new ValueDouble(value);
	}
	
	/**
	 * 取整，返回大于目标数的最小整数
	 * @param params
	 * @return
	 */
	static ValueAbstract funCeil(ValueAbstract param1, ValueAbstract param2) {
		double digits = 0;
		if (param2.getClass() != ValueNull.class) {
			switch (param2.getType()) {
			case LONG:
				digits = ((ValueInt)param2).value;
				break;
			case DOUBLE:
				digits = ((ValueDouble)param2).value;
				break;
			default:
				return null;
			}
		}
		digits = Math.pow(10.0, digits);
		double value = 0.0;
		switch (param1.getType()) {
		case LONG:
			value = ((ValueInt)param1).value;
			break;
		case DOUBLE:
			value = ((ValueDouble)param1).value;
			break;
		default:
			return null;
		}
		
		value = Math.ceil(value*digits) / digits;
		return param2.getClass() == ValueNull.class ? new ValueInt((long)value) : new ValueDouble(value);
	}
	
	/**
	 * 四舍五入取整
	 * @param params
	 * @return
	 */
	static ValueAbstract funRound(ValueAbstract param1, ValueAbstract param2) {
		double digits = 0;
		if (param2.getClass() != ValueNull.class) {
			switch (param2.getType()) {
			case LONG:
				digits = ((ValueInt)param2).value;
				break;
			case DOUBLE:
				digits = ((ValueDouble)param2).value;
				break;
			default:
				return null;
			}
		}
		digits = Math.pow(10.0, digits);
		double value = 0.0;
		switch (param1.getType()) {
		case LONG:
			value = ((ValueInt)param1).value;
			break;
		case DOUBLE:
			value = ((ValueDouble)param1).value;
			break;
		default:
			return null;
		}
		
		value = Math.round(value*digits) / digits;
		return param2.getClass() == ValueNull.class ? new ValueInt((long)value) : new ValueDouble(value);
	}
	
	/**
	 * 计算平方根
	 * @param params
	 * @return
	 */
	static ValueAbstract funSqrt(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.sqrt(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.sqrt(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("sqrt is not supported for " + param.getValue());
	}
	
	/**
	 * 计算立方根
	 * @param params
	 * @return
	 */
	static ValueAbstract funCbrt(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.cbrt(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.cbrt(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("cbrt is not supported for " + param.getValue());
	}
	
	/**
	 * 计算立方根
	 * @param params
	 * @return
	 */
	static ValueAbstract funPow(ValueAbstract param1, ValueAbstract param2) {
		double value1 = (Double)param1.getValue();
		double value2 = (Double)param2.getValue();
		return new ValueDouble(Math.pow(value1, value2));
	}
	
	/**
	 * 符号函数:如果参数为0,则返回0;如果参数大于0,则返回1.0;如果参数小于0,则返回 -1.0
	 * @param params
	 * @return
	 */
	static ValueAbstract funSignum(ValueAbstract param) {
		switch (param.getType()) {
		case LONG:
			return new ValueDouble(Math.signum(((ValueInt)param).value));
		case DOUBLE:
			return new ValueDouble(Math.signum(((ValueDouble)param).value));
		default:
			break;
		}
		
		throw new OperandException("cbrt is not supported for " + param.getValue());
	}
	
	/**
	 * 返回一个伪随机数，该值大于等于 0.0 且小于 1.0
	 * @param params
	 * @return
	 */
	static double funRandom() {
		return Math.random();
	}
}
