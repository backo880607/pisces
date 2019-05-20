package com.pisces.core.primary.expression.value;

public class ValueInt extends ValueAbstract {
	public long value;
	
	public ValueInt(long value) {
		this.value = value;
	}
	
	@Override
	public Type getType() {
		return Type.LONG;
	}
	
	@Override
	public Object getValue() {
		return (Long)this.value;
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
		this.value += rhs.value;
		return this;
	}
	private ValueAbstract addImpl(ValueDouble rhs) {
		return rhs.add(this);
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
		return super.sub(rhs);
	}
	private ValueAbstract subImpl(ValueInt rhs) {
		this.value -= rhs.value;
		return this;
	}
	private ValueAbstract subImpl(ValueDouble rhs) {
		return rhs.sub(this);
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
		return rhs.multiply(this);
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
		return rhs.division(this);
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
		return new ValueBoolean(this.value > rhs.value);
	}
	private ValueBoolean greaterImpl(ValueDouble rhs) {
		return new ValueBoolean(this.value > rhs.value);
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
		return new ValueBoolean(this.value >= rhs.value);
	}
	private ValueBoolean greaterEqualImpl(ValueDouble rhs) {
		return new ValueBoolean(this.value >= rhs.value);
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
	private ValueBoolean lessImpl(ValueInt rhs) {
		return new ValueBoolean(this.value < rhs.value);
	}
	private ValueBoolean lessImpl(ValueDouble rhs) {
		return new ValueBoolean(this.value < rhs.value);
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
		return new ValueBoolean(this.value <= rhs.value);
	}
	private ValueBoolean lessEqualImpl(ValueDouble rhs) {
		return new ValueBoolean(this.value <= rhs.value);
	}
	
	@Override
	public ValueBoolean equal(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.equalImpl((ValueInt)rhs);
		case TEXT:
			return this.equalImpl((ValueText)rhs);
		default:
			break;
		}
		return super.equal(rhs);
	}
	private ValueBoolean equalImpl(ValueInt rhs) {
		return new ValueBoolean(this.value == rhs.value);
	}
	private ValueBoolean equalImpl(ValueText rhs) {
		return new ValueBoolean(this.value == Long.valueOf(rhs.value));
	}
	
	@Override
	public ValueBoolean notEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.notEqualImpl((ValueInt)rhs);
		case TEXT:
			return this.notEqualImpl((ValueText)rhs);
		default:
			break;
		}
		return super.notEqual(rhs);
	}
	private ValueBoolean notEqualImpl(ValueInt rhs) {
		return new ValueBoolean(this.value != rhs.value);
	}
	private ValueBoolean notEqualImpl(ValueText rhs) {
		return new ValueBoolean(this.value != Long.valueOf(rhs.value));
	}
	
	@Override
	public ValueText toText() {
		return new ValueText(String.valueOf(this.value));
	}
	
	@Override
	public ValueInt toInt() {
		return this;
	}
	
	@Override
	public ValueDouble toDouble() {
		return new ValueDouble(this.value);
	}
}
