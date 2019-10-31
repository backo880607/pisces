package com.pisces.core.primary.expression.calculate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.Expression;
import com.pisces.core.primary.expression.ExpressionNode;
import com.pisces.core.primary.expression.function.FunctionManager;


public class FunctionCalculate implements Calculate {
	FunctionManager.Data data;
	List<Expression> paramExps = new ArrayList<Expression>();
	PropertyCalculate fieldCalc;
	boolean isLastChain;
	boolean isIF;
	
	@Override
	public Object GetValue(EntityObject entity) {
		Class<?> returnClass = getReturnClass();
		List<Object> params = new ArrayList<>();
		if (paramExps.isEmpty()) {
			return FunctionManager.instance.invoke(data, params, returnClass);
		}
		
		//int index = 1;
		Iterator<Expression> iter = paramExps.iterator();
		Expression exp = iter.next();
		Object paramValue = exp.getValueImpl(entity);
		if (this.isLastChain) {
			return funLastChain(paramValue, paramExps.get(1));
		} else if (this.isIF) {
			if (((boolean)paramValue)) {
				Expression firstExp = paramExps.get(1);
				return firstExp.getValueImpl(entity);
			}
			
			Expression secondExp = paramExps.get(2);
			return secondExp.getValueImpl(entity);
		}
		
		params.add(paramValue);
		if (Collection.class.isAssignableFrom(paramValue.getClass())) {
			@SuppressWarnings("unchecked")
			Collection<EntityObject> objects = (Collection<EntityObject>)paramValue;
			while (iter.hasNext()) {
				//++index;
				exp = iter.next();
				if (exp.hasField) {
					Map<Long, Object> values = new HashMap<>();
					for (EntityObject temp : objects) {
						values.put(temp.getId(), exp.getValueImpl(temp));
					}
					
					params.add(values);
				} else {
					params.add(exp.getValueImpl(null));
				}
			}
		} else {
			while (iter.hasNext()) {
				//++index;
				exp = iter.next();
				params.add(exp.getValueImpl(entity));
			}
		}
		
		Object result = FunctionManager.instance.invoke(data, params, returnClass);
		if (this.fieldCalc != null && EntityObject.class.isAssignableFrom(result.getClass())) {
			return this.fieldCalc.GetValue((EntityObject)result);
		}
		return result;
	}
	
	@Override
	public int Parse(String str, int index) {
		int temp = index;
		while (index < str.length() && str.charAt(index) != '(') {
			++index;
		}
		
		if (index == str.length()) {
			return -1;
		}
		
		String name = str.substring(temp, index);
		this.data = FunctionManager.instance.getFunction(name);
		this.isLastChain = name.equalsIgnoreCase("LastChain");
		this.isIF = name.equalsIgnoreCase("if");
		
		++index;
		while (index < str.length() && str.charAt(index) != ')') {
			Expression exp = new Expression();
			Entry<Integer, ExpressionNode> nd = exp.Create(str, index, true);
			if (nd.getValue() == null) {
				return -1;
			}
			exp.root = nd.getValue();
			index = nd.getKey();
			this.paramExps.add(exp);
		}
		
		if (index > str.length())
			return -1;
		
		++index;
		// 函数返回的是对象，则可以继续.操作
		if (EntityObject.class.isAssignableFrom(getReturnClass())) {
			temp = index;
			while (temp < str.length()) {
				if (str.charAt(temp) == '.') {
					fieldCalc = new PropertyCalculate();
					@SuppressWarnings("unchecked")
					Class<? extends EntityObject> clazz = (Class<? extends EntityObject>)getReturnClass();
					index = fieldCalc.Parse(str, index, clazz);
					if (index == -1) {
						return -1;
					}
					break;
				}
				if (!Character.isSpaceChar(str.charAt(temp))) {
					break;
				}
				++temp;
			}
		}
		
		// 检查函数是否有效
		List<Class<?>> paramClazzs = new ArrayList<Class<?>>();
		for (Expression exp : paramExps) {
			paramClazzs.add(exp.getReturnClass());
		}
		FunctionManager.instance.check(data, paramClazzs);
		return index;
	}

	@Override
	public Class<?> getReturnClass() {
		if (this.data.returnBy == 0) {
			return this.data.method.getReturnType();
		}
		
		return this.paramExps.get(this.data.returnBy - 1).getReturnClass();
	}
	
	private Collection<EntityObject> funLastChain(Object param, Expression exp) {
		if (EntityObject.class.isAssignableFrom(param.getClass())) {
			return null;
		}
		EntityObject entity = (EntityObject)param;
		Collection<EntityObject> result = new ArrayList<EntityObject>();
		funLastChainImpl(entity, exp, result);
		return result;
	}
	
	private void funLastChainImpl(EntityObject entity, Expression exp, Collection<EntityObject> result) {
		Object above = exp.getValue(entity);
		if (above == null) {
			result.add(entity);
			return;
		}
		if (Collection.class.isAssignableFrom(above.getClass())) {
			@SuppressWarnings("unchecked")
			Collection<EntityObject> value = (Collection<EntityObject>)above;
			if (value.isEmpty()) {
				result.add(entity);
			} else {
				for (EntityObject aboveEntity : value) {
					funLastChainImpl(aboveEntity, exp, result);
				}
			}
		}
	}
}
