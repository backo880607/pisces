package com.pisces.core.primary.expression.value;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pisces.core.utils.DateUtils;

public class ValueDateTime extends ValueAbstract {
	public Date value;
	public static SimpleDateFormat DATETIME_FORMATOR = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public ValueDateTime(Date time) {
		this.value = time;
	}
	
	public ValueDateTime(String str) {
		try {
			this.value = DATETIME_FORMATOR.parse(str);
		} catch (ParseException e) {
			this.value = new Date(0);
		}
	}
	
	@Override
	public Type getType() {
		return Type.DATETIME;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public ValueAbstract add(ValueAbstract rhs) {
		if (this.value == null) {
			return new ValueInt(0);
		}
		
		switch (rhs.getType()) {
		case LONG:
			return this.addImpl((ValueInt)rhs);
		case DATETIME:
			return this.addImpl((ValueDateTime)rhs);
		case DURATION:
			return this.addImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.add(rhs);
	}
	
	private ValueAbstract addImpl(ValueInt rhs) {
		long value = this.value.getTime() + rhs.value * DateUtils.PER_DAY;
		return new ValueDateTime(new Date(value));
	}
	private ValueAbstract addImpl(ValueDateTime rhs) {
		long value = rhs.value == null ? 0 : this.value.getTime() + rhs.value.getTime();
		return new ValueDateTime(new Date(value));
	}
	private ValueAbstract addImpl(ValueDuration rhs) {
		long value = this.value.getTime() + rhs.value;
		return new ValueDateTime(new Date(value));
	}

	@Override
	public ValueAbstract sub(ValueAbstract rhs) {
		if (this.value == null) {
			return new ValueInt(0);
		}
		
		switch (rhs.getType()) {
		case LONG:
			return this.subImpl((ValueInt)rhs);
		case DATETIME:
			return this.subImpl((ValueDateTime)rhs);
		case DURATION:
			return this.subImpl((ValueDuration)rhs);
		default:
			break;
		}
		return super.sub(rhs);
	}
	
	private ValueAbstract subImpl(ValueInt rhs) {
		long value = this.value.getTime() - rhs.value * DateUtils.PER_DAY;
		return new ValueDateTime(new Date(value));
	}
	private ValueAbstract subImpl(ValueDateTime rhs) {
		long value = rhs.value == null ? 0 : this.value.getTime() - rhs.value.getTime();
		return new ValueInt(value / DateUtils.PER_SECOND);
	}
	private ValueAbstract subImpl(ValueDuration rhs) {
		long value = this.value.getTime() - rhs.value;
		return new ValueDateTime(new Date(value));
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
		try {
			Date temp = DATETIME_FORMATOR.parse(rhs.value);
			return new ValueBoolean(this.value.getTime() > temp.getTime());
		} catch (ParseException e) {
		}
		return null;
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
		try {
			Date temp = DATETIME_FORMATOR.parse(rhs.value);
			return new ValueBoolean(this.value.getTime() >= temp.getTime());
		} catch (ParseException e) {
		}
		return null;
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
		try {
			Date temp = DATETIME_FORMATOR.parse(rhs.value);
			return new ValueBoolean(this.value.getTime() < temp.getTime());
		} catch (ParseException e) {
		}
		return null;
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
		try {
			Date temp = DATETIME_FORMATOR.parse(rhs.value);
			return new ValueBoolean(this.value.getTime() <= temp.getTime());
		} catch (ParseException e) {
		}
		return null;
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
		try {
			Date temp = DATETIME_FORMATOR.parse(rhs.value);
			return new ValueBoolean(this.value.getTime() == temp.getTime());
		} catch (ParseException e) {
		}
		return null;
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
		try {
			Date temp = DATETIME_FORMATOR.parse(rhs.value);
			return new ValueBoolean(this.value.getTime() != temp.getTime());
		} catch (ParseException e) {
		}
		return null;
	}

	@Override
	public ValueText toText() {
		if (this.value == null || this.value.getTime() == 0) {
			return new ValueText("");
		}
		return new ValueText(ValueDateTime.DATETIME_FORMATOR.format(this.value));
	}
	
	public ValueAbstract toText(String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return new ValueText(formater.format(this.value));
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
