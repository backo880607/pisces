package com.pisces.core.primary.expression.value;

public class ValueBoolean extends ValueAbstract {
	public boolean value;
	public ValueBoolean(boolean bOK) {
		this.value = bOK;
	}
	
	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	@Override
	public Object getValue() {
		return (Boolean)this.value;
	}

	@Override
	public ValueBoolean greater(ValueAbstract rhs) {
		switch (rhs.getType()) {
        case BOOLEAN:
            return this.greaterImpl((ValueBoolean)rhs);
        default:
            break;
        }
        return super.greater(rhs);
	}
	private ValueBoolean greaterImpl(ValueBoolean rhs) {
		if (this.value && !rhs.value) {
			return new ValueBoolean(true);
		}
		return new ValueBoolean(false);
	}

	@Override
	public ValueBoolean greaterEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
        case BOOLEAN:
            return this.greaterEqualImpl((ValueBoolean)rhs);
        default:
            break;
        }
        return super.greaterEqual(rhs);
	}
	private ValueBoolean greaterEqualImpl(ValueBoolean rhs) {
		if (!this.value && rhs.value) {
			return new ValueBoolean(false);
		}
		return new ValueBoolean(true);
	}

	@Override
	public ValueBoolean less(ValueAbstract rhs) {
        switch (rhs.getType()) {
        case BOOLEAN:
            return this.lessImpl((ValueBoolean)rhs);
        default:
            break;
        }
        return super.less(rhs);
    }
	private ValueBoolean lessImpl(ValueBoolean rhs) {
	    if (!this.value && rhs.value) {
	        return new ValueBoolean(true);
	    }
	    return new ValueBoolean(false);
    }

	@Override
	public ValueBoolean lessEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
        case BOOLEAN:
            return this.lessEqualImpl((ValueBoolean)rhs);
        default:
            break;
        }
        return super.lessEqual(rhs);
	}
	private ValueBoolean lessEqualImpl(ValueBoolean rhs) {
		if (this.value && !rhs.value) {
			return new ValueBoolean(false);
		}
		return new ValueBoolean(true);
	}

	@Override
	public ValueBoolean equal(ValueAbstract rhs) {
	    switch (rhs.getType()) {
        case BOOLEAN:
            return this.equalImpl((ValueBoolean)rhs);
        default:
            break;
        }
        return super.equal(rhs);
	}
	private ValueBoolean equalImpl(ValueBoolean rhs) {
        return new ValueBoolean(this.value == rhs.value);
    }

	@Override
	public ValueBoolean notEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
        case BOOLEAN:
            return this.notEqualImpl((ValueBoolean)rhs);
        default:
            break;
        }
        return super.notEqual(rhs);
	}
	private ValueBoolean notEqualImpl(ValueBoolean rhs) {
		return new ValueBoolean(this.value != rhs.value);
	}

	@Override
	public ValueText toText() {
		return new ValueText(this.value ? "1" : "0");
	}

	@Override
	public ValueInt toInt() {
		return new ValueInt(this.value ? 1 : 0);
	}

	@Override
	public ValueDouble toDouble() {
		return new ValueDouble(this.value ? 1.0 : 0.0);
	}
}
