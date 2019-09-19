package com.pisces.core.primary.expression.value;

import java.text.ParseException;
import java.util.Date;

import com.pisces.core.enums.PROPERTY_TYPE;
import com.pisces.core.exception.ExpressionException;
import com.pisces.core.utils.DateUtils;

public class ValueDateTime extends ValueAbstract {
	private String format;
	public Date value;
	
	public ValueDateTime(Date time, PROPERTY_TYPE type) {
		this.value = time;
		switch (type) {
		case DATE:
			format = DateUtils.DATE_FORMAT;
			break;
		case TIME:
			format = DateUtils.TIME_FORMAT;
			break;
		case DATE_TIME:
			format = DateUtils.SIMPLE_FROMAT;
			break;
		default:
			throw new ExpressionException("not support time type: " + type);
		}
	}
	
	public ValueDateTime(String str) {
		try {
			this.value = DateUtils.parse(str);
			this.format = DateUtils.SIMPLE_FROMAT;
		} catch (ParseException e) {
			try {
				this.value = DateUtils.parse(str, DateUtils.DATE_FORMAT);
				this.format = DateUtils.DATE_FORMAT;
			} catch (ParseException e2) {
				try {
					this.value = DateUtils.parse(str, DateUtils.TIME_FORMAT);
					this.format = DateUtils.TIME_FORMAT;
				} catch (ParseException e3) {
					throw new ExpressionException("invalid date format: " + str);
				}
			}
		}
	}
	
	@Override
	public Type getType() {
		return Type.DATETIME;
	}
	
	@Override
	public Class<?> getReturnClass() {
		return Date.class;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public ValueAbstract add(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.addImpl((ValueInt)rhs);
		case DURATION:
			return this.addImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.add(rhs);
	}
	
	private ValueAbstract addImpl(ValueInt rhs) {
		if (format == DateUtils.TIME_FORMAT) {
			throw new ExpressionException("not support by time format");
		}
		if (this.value != DateUtils.INVALID) {
			this.value.setTime(this.value.getTime() + rhs.value * DateUtils.PER_DAY);
		}
		return this;
	}
	private ValueAbstract addImpl(ValueDuration rhs) {
		if (this.value != DateUtils.INVALID) {
			this.value.setTime(this.value.getTime() + rhs.value);
		}
		return this;
	}

	@Override
	public ValueAbstract sub(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case LONG:
			return this.subImpl((ValueInt)rhs);
		case DURATION:
			return this.subImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.sub(rhs);
	}
	
	private ValueAbstract subImpl(ValueInt rhs) {
		if (format == DateUtils.TIME_FORMAT) {
			throw new ExpressionException("not support by time format");
		}
		if (this.value != DateUtils.INVALID) {
			this.value.setTime(this.value.getTime() - rhs.value * DateUtils.PER_DAY);
		}
		return this;
	}
	private ValueAbstract subImpl(ValueDuration rhs) {
		if (this.value != DateUtils.INVALID) {
			this.value.setTime(this.value.getTime() - rhs.value);
		}
		return this;
	}

	@Override
	public ValueBoolean greater(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DATETIME:
			return this.greaterImpl((ValueDateTime)rhs);
		case TEXT:
			return this.greaterImpl((ValueText)rhs);
		default:
			break;
		}
		return super.greater(rhs);
	}
	
	private ValueBoolean greaterImpl(ValueDateTime rhs) {
		return new ValueBoolean(this.value.getTime() > rhs.value.getTime());
	}
	private ValueBoolean greaterImpl(ValueText rhs) {
		ValueDateTime temp = new ValueDateTime(rhs.value);
		return new ValueBoolean(this.value.getTime() > temp.value.getTime());
	}

	@Override
	public ValueBoolean greaterEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DATETIME:
			return this.greaterEqualImpl((ValueDateTime)rhs);
		case TEXT:
			return this.greaterEqualImpl((ValueText)rhs);
		default:
			break;
		}
		return super.greaterEqual(rhs);
	}
	
	private ValueBoolean greaterEqualImpl(ValueDateTime rhs) {
		return new ValueBoolean(this.value.getTime() >= rhs.value.getTime());
	}
	private ValueBoolean greaterEqualImpl(ValueText rhs) {
		ValueDateTime temp = new ValueDateTime(rhs.value);
		return new ValueBoolean(this.value.getTime() >= temp.value.getTime());
	}

	@Override
	public ValueBoolean less(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DATETIME:
			return this.lessImpl((ValueDateTime)rhs);
		case TEXT:
			return this.lessImpl((ValueText)rhs);
		default:
			break;
		}
		return super.less(rhs);
	}
	
	private ValueBoolean lessImpl(ValueDateTime rhs) {
		return new ValueBoolean(this.value.getTime() < rhs.value.getTime());
	}
	private ValueBoolean lessImpl(ValueText rhs) {
		ValueDateTime temp = new ValueDateTime(rhs.value);
		return new ValueBoolean(this.value.getTime() < temp.value.getTime());
	}

	@Override
	public ValueBoolean lessEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DATETIME:
			return this.lessEqualImpl((ValueDateTime)rhs);
		case TEXT:
			return this.lessEqualImpl((ValueText)rhs);
		default:
			break;
		}
		return super.lessEqual(rhs);
	}
	
	private ValueBoolean lessEqualImpl(ValueDateTime rhs) {
		return new ValueBoolean(this.value.getTime() <= rhs.value.getTime());
	}
	private ValueBoolean lessEqualImpl(ValueText rhs) {
		ValueDateTime temp = new ValueDateTime(rhs.value);
		return new ValueBoolean(this.value.getTime() <= temp.value.getTime());
	}

	@Override
	public ValueBoolean equal(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DATETIME:
			return this.equalImpl((ValueDateTime)rhs);
		case TEXT:
			return this.equalImpl((ValueText)rhs);
		default:
			break;
		}
		return super.equal(rhs);
	}
	
	private ValueBoolean equalImpl(ValueDateTime rhs) {
		return new ValueBoolean(this.value.getTime() == rhs.value.getTime());
	}
	private ValueBoolean equalImpl(ValueText rhs) {
		ValueDateTime temp = new ValueDateTime(rhs.value);
		return new ValueBoolean(this.value.getTime() == temp.value.getTime());
	}

	@Override
	public ValueBoolean notEqual(ValueAbstract rhs) {
		switch (rhs.getType()) {
		case DATETIME:
			return this.notEqualImpl((ValueDateTime)rhs);
		case TEXT:
			return this.notEqualImpl((ValueText)rhs);
		default:
			break;
		}
		return super.notEqual(rhs);
	}
	
	private ValueBoolean notEqualImpl(ValueDateTime rhs) {
		return new ValueBoolean(this.value.getTime() != rhs.value.getTime());
	}
	private ValueBoolean notEqualImpl(ValueText rhs) {
		ValueDateTime temp = new ValueDateTime(rhs.value);
		return new ValueBoolean(this.value.getTime() != temp.value.getTime());
	}

	@Override
	public ValueText toText() {
		return new ValueText(DateUtils.format(this.value, this.format));
	}
	
	public ValueAbstract toText(String format) {
		return new ValueText(DateUtils.format(this.value, format));
	}

	@Override
	public ValueInt toInt() {
		return new ValueInt(this.value.getTime());
	}

	@Override
	public ValueDouble toDouble() {
		return new ValueDouble((double)this.value.getTime());
	}
}
