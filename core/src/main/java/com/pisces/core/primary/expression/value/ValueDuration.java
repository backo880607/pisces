package com.pisces.core.primary.expression.value;

import java.util.Date;

import com.pisces.core.entity.DateDur;
import com.pisces.core.utils.DateUtils;

public class ValueDuration extends ValueAbstract {
	public long value;
	
	public ValueDuration(long dur) {
		this.value = dur;
	}
	public ValueDuration(String dur) {
		DateDur dateDur = new DateDur(dur);
		this.value = dateDur.getTimeInMillis();
	}

	@Override
	public Type getType() {
		return Type.DURATION;
	}
	
	@Override
	public Class<?> getReturnClass() {
		return Long.class;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public ValueAbstract add(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DATETIME:
			return this.addImpl((ValueDateTime)rhs);
		case DURATION:
			return this.addImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.add(rhs);
	}
	private ValueAbstract addImpl(ValueDateTime rhs) {
		long value = this.value + rhs.value.getTime();
		return new ValueDateTime(new Date(value));
	}
	private ValueAbstract addImpl(ValueDuration rhs) {
		long value = this.value + rhs.value;
		return new ValueDuration(value);
	}

	@Override
	public ValueAbstract sub(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DURATION:
			return this.subImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.sub(rhs);
	}
	private ValueAbstract subImpl(ValueDuration rhs) {
		long value = this.value - rhs.value;
		return new ValueDuration(value);
	}

	@Override
	public ValueBoolean greater(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DURATION:
			return this.greaterImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.greater(rhs);
	}
	private ValueBoolean greaterImpl(ValueDuration rhs) {
		return new ValueBoolean(this.value > rhs.value);
	}

	@Override
	public ValueBoolean greaterEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DURATION:
			return this.greaterEqualImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.greaterEqual(rhs);
	}
	private ValueBoolean greaterEqualImpl(ValueDuration rhs) {
		return new ValueBoolean(this.value >= rhs.value);
	}

	@Override
	public ValueBoolean less(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DURATION:
			return this.lessImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.less(rhs);
	}
	private ValueBoolean lessImpl(ValueDuration rhs) {
		return new ValueBoolean(this.value < rhs.value);
	}

	@Override
	public ValueBoolean lessEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DURATION:
			return this.lessEqualImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.lessEqual(rhs);
	}
	private ValueBoolean lessEqualImpl(ValueDuration rhs) {
		return new ValueBoolean(this.value <= rhs.value);
	}

	@Override
	public ValueBoolean equal(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DURATION:
			return this.equalImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.equal(rhs);
	}
	private ValueBoolean equalImpl(ValueDuration rhs) {
		return new ValueBoolean(this.value == rhs.value);
	}

	@Override
	public ValueBoolean notEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DURATION:
			return this.notEqualImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.notEqual(rhs);
	}
	private ValueBoolean notEqualImpl(ValueDuration rhs) {
		return new ValueBoolean(this.value != rhs.value);
	}

	@Override
	public ValueText toText() {
		DateDur dateDur = new DateDur("");
		dateDur.setTime((int)(this.value / DateUtils.PER_SECOND));
		return new ValueText(dateDur.getString());
	}

	@Override
	public ValueInt toInt() {
		return new ValueInt(this.value / DateUtils.PER_SECOND);
	}

	@Override
	public ValueDouble toDouble() {
		return new ValueDouble(this.value / DateUtils.PER_SECOND);
	}

}
