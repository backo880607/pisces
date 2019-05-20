package com.pisces.core.primary.expression.value;

public class ValueText extends ValueAbstract {
	public String value;
	
	public ValueText(String value) {
		this.value = value;
	}
	
	@Override
	public Type getType() {
		return Type.TEXT;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}
	
	@Override
	public ValueAbstract add(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case TEXT:
			return this.addImpl((ValueText)rhs);
		default:
			break;
		}
		return null;
	}
	private ValueAbstract addImpl(ValueText rhs) {
		this.value += rhs.value;
		return this;
	}
	
	@Override
	public ValueBoolean greater(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case TEXT:
			return this.greaterImpl((ValueText)rhs);
		default:
			break;
		}
		return null;
	}
	private ValueBoolean greaterImpl(ValueText rhs) {
		return new ValueBoolean(this.value.compareTo(rhs.value) > 0);
	}
	
	@Override
	public ValueBoolean greaterEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case TEXT:
			return this.greaterEqualImpl((ValueText)rhs);
		default:
			break;
		}
		return null;
	}
	private ValueBoolean greaterEqualImpl(ValueText rhs) {
		return new ValueBoolean(this.value.compareTo(rhs.value) >= 0);
	}
	
	@Override
	public ValueBoolean less(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case TEXT:
			return this.lessImpl((ValueText)rhs);
		default:
			break;
		}
		return null;
	}
	private ValueBoolean lessImpl(ValueText rhs) {
		return new ValueBoolean(this.value.compareTo(rhs.value) < 0);
	}
	
	@Override
	public ValueBoolean lessEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case TEXT:
			return this.lessEqualImpl((ValueText)rhs);
		default:
			break;
		}
		return null;
	}
	private ValueBoolean lessEqualImpl(ValueText rhs) {
		return new ValueBoolean(this.value.compareTo(rhs.value) <= 0);
	}
	
	@Override
	public ValueBoolean equal(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case TEXT:
			return this.equalImpl((ValueText)rhs);
		case LONG:
			return this.equalImpl((ValueInt)rhs);
		case EnumType:
			return this.equalImpl((ValueEnum)rhs);
		default:
			break;
		}
		return null;
	}
	private ValueBoolean equalImpl(ValueText rhs) {
		return new ValueBoolean(this.value.compareTo(rhs.value) == 0);
	}
	private ValueBoolean equalImpl(ValueInt rhs) {
		return new ValueBoolean(Long.valueOf(this.value) == rhs.value);
	}
	private ValueBoolean equalImpl(ValueEnum rhs) {
		return new ValueBoolean(this.value.equalsIgnoreCase(rhs.value.name()));
	}
	
	@Override
	public ValueBoolean notEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case TEXT:
			return this.notEqualImpl((ValueText)rhs);
		case LONG:
			return this.notEqualImpl((ValueInt)rhs);
		case EnumType:
			return this.notEqualImpl((ValueEnum)rhs);
		default:
			break;
		}
		return null;
	}
	private ValueBoolean notEqualImpl(ValueText rhs) {
		return new ValueBoolean(this.value.compareTo(rhs.value) != 0);
	}
	private ValueBoolean notEqualImpl(ValueInt rhs) {
		return new ValueBoolean(Long.valueOf(this.value) != rhs.value);
	}
	private ValueBoolean notEqualImpl(ValueEnum rhs) {
		return new ValueBoolean(!this.value.equalsIgnoreCase(rhs.value.name()));
	}
	
	@Override
	public ValueText toText() {
		return this;
	}
	
	@Override
	public ValueInt toInt() {
		if (this.value.isEmpty()) {
			return new ValueInt(0);
		}
		return new ValueInt(Integer.parseInt(this.value));
	}
	
	@Override
	public ValueDouble toDouble() {
		if (this.value.isEmpty()) {
			return new ValueDouble(0.0);
		}
		return new ValueDouble(Double.parseDouble(this.value));
	}
}
