package com.pisces.core.primary.expression.value;

import com.pisces.core.utils.DoubleUtils;

public class ValueDouble extends ValueAbstract {
	public double value;
	public ValueDouble(double value) {
		this.value = value;
	}
	
	@Override
	public Type getType() {
		return Type.DOUBLE;
	}
	
	@Override
	public Object getValue() {
		return (Double)this.value;
	}
	
	@Override
	public ValueAbstract add(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.addImpl((ValueInt)rhs);
		case DOUBLE:
			return this.addImpl((ValueDouble)rhs);
		default:
			break;
		}
		return super.add(rhs);
	}
	private ValueAbstract addImpl(ValueInt rhs) {
		this.value += (double)rhs.value;
		return this;
	}
	private ValueAbstract addImpl(ValueDouble rhs) {
		this.value += rhs.value;
		return this;
	}
	
	@Override
	public ValueAbstract sub(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.subImpl((ValueInt)rhs);
		case DOUBLE:
			return this.subImpl((ValueDouble)rhs);
		default:
				break;
		}
		return null;
	}
	private ValueAbstract subImpl(ValueInt rhs) {
		this.value -= rhs.value;
		return this;
	}
	private ValueAbstract subImpl(ValueDouble rhs) {
		this.value -= rhs.value;
		return this;
	}
	
	@Override
	public ValueAbstract multiply(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.multiplyImpl((ValueInt)rhs);
		case DOUBLE:
			return this.multiplyImpl((ValueDouble)rhs);
		default:
			break;
		}
		return super.multiply(rhs);
	}
	private ValueAbstract multiplyImpl(ValueInt rhs) {
		this.value *= rhs.value;
		return this;
	}
	private ValueAbstract multiplyImpl(ValueDouble rhs) {
		this.value *= rhs.value;
		return this;
	}
	
	@Override
	public ValueAbstract division(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.divisionImpl((ValueInt)rhs);
		case DOUBLE:
			return this.divisionImpl((ValueDouble)rhs);
		default:
			break;
		}
		return super.division(rhs);
	}
	private ValueAbstract divisionImpl(ValueInt rhs) {
		this.value /= rhs.value != 0 ? rhs.value : 1;
		return this;
	}
	private ValueAbstract divisionImpl(ValueDouble rhs) {
		this.value /= DoubleUtils.isZero(rhs.value) ? 1.0f : rhs.value;
		return this;
	}
	
	@Override
	public ValueBoolean greater(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.greaterImpl((ValueInt)rhs);
		case DOUBLE:
			return this.greaterImpl((ValueDouble)rhs);
		default:
			break;
		}
		return super.greater(rhs);
	}
	private ValueBoolean greaterImpl(ValueInt rhs) {
		return new ValueBoolean(DoubleUtils.isLess(rhs.value, this.value));
	}
	private ValueBoolean greaterImpl(ValueDouble rhs) {
		return new ValueBoolean(DoubleUtils.isLess(rhs.value, this.value));
	}
	
	@Override
	public ValueBoolean greaterEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.greaterEqualImpl((ValueInt)rhs);
		case DOUBLE:
			return this.greaterEqualImpl((ValueDouble)rhs);
		default:
			break;
		}
		return super.greaterEqual(rhs);
	}
	private ValueBoolean greaterEqualImpl(ValueInt rhs) {
		return new ValueBoolean(!DoubleUtils.isLess(this.value, rhs.value));
	}
	private ValueBoolean greaterEqualImpl(ValueDouble rhs) {
		return new ValueBoolean(!DoubleUtils.isLess(this.value, rhs.value));
	}
	
	@Override
	public ValueBoolean less(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.lessImpl((ValueInt)rhs);
		case DOUBLE:
			return this.lessImpl((ValueDouble)rhs);
		default:
			break;
		}
		return super.less(rhs);
	}
	private  ValueBoolean lessImpl(ValueInt rhs) {
		return new ValueBoolean(DoubleUtils.isLess(this.value, rhs.value));
	}
	private ValueBoolean lessImpl(ValueDouble rhs) {
		return new ValueBoolean(DoubleUtils.isLess(this.value, rhs.value));
	}
	
	@Override
	public ValueBoolean lessEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.lessEqualImpl((ValueInt)rhs);
		case DOUBLE:
			return this.lessEqualImpl((ValueDouble)rhs);
		default:
			break;
		}
		return super.lessEqual(rhs);
	}
	private ValueBoolean lessEqualImpl(ValueInt rhs) {
		return new ValueBoolean(!DoubleUtils.isLess(rhs.value, this.value));
	}
	private ValueBoolean lessEqualImpl(ValueDouble rhs) {
		return new ValueBoolean(!DoubleUtils.isLess(rhs.value, this.value));
	}
	
	@Override
	public ValueBoolean equal(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.equalImpl((ValueInt)rhs);
		case DOUBLE:
			return this.equalImpl((ValueDouble)rhs);
		default:
			break;
		}
		return super.equal(rhs);
	}
	private ValueBoolean equalImpl(ValueInt rhs) {
		return new ValueBoolean(DoubleUtils.isZero(this.value - rhs.value));
	}
	private ValueBoolean equalImpl(ValueDouble rhs) {
		return new ValueBoolean(DoubleUtils.isZero(this.value - rhs.value));
	}
	
	@Override
	public ValueBoolean notEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.notEqualImpl((ValueInt)rhs);
		case DOUBLE:
			return this.notEqualImpl((ValueDouble)rhs);
		default:
			break;
		}
		return super.notEqual(rhs);
	}
	private ValueBoolean notEqualImpl(ValueInt rhs) {
		return new ValueBoolean(!DoubleUtils.isZero(this.value - rhs.value));
	}
	private ValueBoolean notEqualImpl(ValueDouble rhs) {
		return new ValueBoolean(!DoubleUtils.isZero(this.value - rhs.value));
	}
	
	@Override
	public ValueText toText() {
		return new ValueText(String.valueOf(this.value));
	}
	
	@Override
	public ValueInt toInt() {
		return new ValueInt((int)this.value);
	}
	
	@Override
	public ValueDouble toDouble() {
		return this;
	}
}
