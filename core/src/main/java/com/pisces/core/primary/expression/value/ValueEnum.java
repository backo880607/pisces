package com.pisces.core.primary.expression.value;

public class ValueEnum extends ValueAbstract {
	public Enum<?> value;

	public ValueEnum(Enum<?> value) {
		this.value = value;
	}
	
	@Override
	public Type getType() {
		return Type.EnumType;
	}
	
	@Override
	public Class<?> getReturnClass() {
		return Enum.class;
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public ValueBoolean greater(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case EnumType:
			return this.greaterImpl((ValueEnum)rhs);
		case TEXT:
			return this.greaterImpl((ValueText)rhs);
		case LONG:
			return this.greaterImpl((ValueInt)rhs);
		default:
			break;
		}
		return super.greater(rhs);
	}
	private ValueBoolean greaterImpl(ValueEnum rhs) {
		return new ValueBoolean(this.value.ordinal() > rhs.value.ordinal());
	}
	private ValueBoolean greaterImpl(ValueText rhs) {
		@SuppressWarnings("unchecked")
		Enum<?> target = Enum.valueOf(this.value.getClass(), rhs.value);
		return new ValueBoolean(this.value.ordinal() > target.ordinal());
	}
	private ValueBoolean greaterImpl(ValueInt rhs) {
		return new ValueBoolean(this.value.ordinal() > rhs.value);
	}

	@Override
	public ValueBoolean greaterEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case EnumType:
			return this.greaterEqualImpl((ValueEnum)rhs);
		case TEXT:
			return this.greaterEqualImpl((ValueText)rhs);
		case LONG:
			return this.greaterEqualImpl((ValueInt)rhs);
		default:
			break;
		}
		return super.greaterEqual(rhs);
	}
	private ValueBoolean greaterEqualImpl(ValueEnum rhs) {
		return new ValueBoolean(this.value.ordinal() >= rhs.value.ordinal());
	}
	private ValueBoolean greaterEqualImpl(ValueText rhs) {
		@SuppressWarnings("unchecked")
		Enum<?> target = Enum.valueOf(this.value.getClass(), rhs.value);
		return new ValueBoolean(this.value.ordinal() >= target.ordinal());
	}
	private ValueBoolean greaterEqualImpl(ValueInt rhs) {
		return new ValueBoolean(this.value.ordinal() >= rhs.value);
	}

	@Override
	public ValueBoolean less(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case EnumType:
			return this.lessImpl((ValueEnum)rhs);
		case TEXT:
			return this.lessImpl((ValueText)rhs);
		case LONG:
			return this.lessImpl((ValueInt)rhs);
		default:
			break;
		}
		return super.less(rhs);
	}
	private ValueBoolean lessImpl(ValueEnum rhs) {
		return new ValueBoolean(this.value.ordinal() < rhs.value.ordinal());
	}
	private ValueBoolean lessImpl(ValueText rhs) {
		@SuppressWarnings("unchecked")
		Enum<?> target = Enum.valueOf(this.value.getClass(), rhs.value);
		return new ValueBoolean(this.value.ordinal() < target.ordinal());
	}
	private ValueBoolean lessImpl(ValueInt rhs) {
		return new ValueBoolean(this.value.ordinal() < rhs.value);
	}

	@Override
	public ValueBoolean lessEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case EnumType:
			return this.lessEqualImpl((ValueEnum)rhs);
		case TEXT:
			return this.lessEqualImpl((ValueText)rhs);
		case LONG:
			return this.lessEqualImpl((ValueInt)rhs);
		default:
			break;
		}
		return super.lessEqual(rhs);
	}
	private ValueBoolean lessEqualImpl(ValueEnum rhs) {
		return new ValueBoolean(this.value.ordinal() <= rhs.value.ordinal());
	}
	private ValueBoolean lessEqualImpl(ValueText rhs) {
		@SuppressWarnings("unchecked")
		Enum<?> target = Enum.valueOf(this.value.getClass(), rhs.value);
		return new ValueBoolean(this.value.ordinal() <= target.ordinal());
	}
	private ValueBoolean lessEqualImpl(ValueInt rhs) {
		return new ValueBoolean(this.value.ordinal() <= rhs.value);
	}

	@Override
	public ValueBoolean equal(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case EnumType:
			return this.equalImpl((ValueEnum)rhs);
		case TEXT:
			return this.equalImpl((ValueText)rhs);
		case LONG:
			return this.equalImpl((ValueInt)rhs);
		default:
			break;
		}
		return super.equal(rhs);
	}
	private ValueBoolean equalImpl(ValueEnum rhs) {
		return new ValueBoolean(this.value.ordinal() == rhs.value.ordinal());
	}
	private ValueBoolean equalImpl(ValueText rhs) {
		return new ValueBoolean(this.value.name().equalsIgnoreCase(rhs.value));
	}
	private ValueBoolean equalImpl(ValueInt rhs) {
		return new ValueBoolean(this.value.ordinal() == rhs.value);
	}

	@Override
	public ValueBoolean notEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case EnumType:
			return this.notEqualImpl((ValueEnum)rhs);
		case TEXT:
			return this.notEqualImpl((ValueText)rhs);
		case LONG:
			return this.notEqualImpl((ValueInt)rhs);
		default:
			break;
		}
		return super.notEqual(rhs);
	}
	private ValueBoolean notEqualImpl(ValueEnum rhs) {
		return new ValueBoolean(this.value.ordinal() != rhs.value.ordinal());
	}
	private ValueBoolean notEqualImpl(ValueText rhs) {
		return new ValueBoolean(!this.value.name().equalsIgnoreCase(rhs.value));
	}
	private ValueBoolean notEqualImpl(ValueInt rhs) {
		return new ValueBoolean(this.value.ordinal() != rhs.value);
	}

	@Override
	public ValueText toText() {
		return new ValueText(this.value.name());
	}

	@Override
	public ValueInt toInt() {
		return new ValueInt(this.value.ordinal());
	}

	@Override
	public ValueDouble toDouble() {
		return new ValueDouble(this.value.ordinal());
	}

}
