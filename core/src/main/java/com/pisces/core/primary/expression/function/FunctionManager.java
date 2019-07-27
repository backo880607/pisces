package com.pisces.core.primary.expression.function;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.validation.ValidationException;

import com.pisces.core.annotation.ELFunction;
import com.pisces.core.annotation.ELParm;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.primary.expression.OperType;
import com.pisces.core.primary.expression.exception.FunctionException;
import com.pisces.core.primary.expression.value.ValueAbstract;
import com.pisces.core.service.EntityService;

public class FunctionManager {
	public static class Data {
		public Method method;
		public int returnBy = 0;
		public int optionals = 0;
		Map<Integer, Class<?>[]> paramClazzs = new HashMap<>();
	}
	
	public static class InnerData extends Data {
		Function<List<ValueAbstract>, ValueAbstract> fun;
	}
	
	public static class OuterData extends Data {
		EntityService<? extends EntityObject> service;
	}
	
	private HashMap<String, Data> functions = new HashMap<>();
	
	private FunctionManager() {
	}
	
	{
		InternalFunction.register(this);
		DateTimeFunction.register(this);
		LogicFunction.register(this);
		MathFunction.register(this);
		TextFunction.register(this);
		ObjectFunction.register(this);
	}
	
	public static FunctionManager instance = new FunctionManager();
	
	public Data getFunction(String name) {
		Data data = this.functions.get(name.trim().toUpperCase());
		if (data == null) {
			throw new FunctionException("unregister function: " + name);
		}
		
		return data;
	}
	
	public Function<List<ValueAbstract>, ValueAbstract> getFunction(OperType operType) {
		Function<List<ValueAbstract>, ValueAbstract> fun = null;
		switch (operType) {
		case PLUS:
			fun = InternalFunction::add;
			break;
		case AND:
			fun = InternalFunction::and;
			break;
		case DATA:
			break;
		case DIVIDED:
			fun = InternalFunction::division;
			break;
		case EOL:
			break;
		case EQUAL:
			fun = InternalFunction::equal;
			break;
		case FUN:
			break;
		case GREATER:
			fun = InternalFunction::greater;
			break;
		case GREATEREQUAL:
			fun = InternalFunction::greaterEqual;
			break;
		case LESS:
			fun = InternalFunction::less;
			break;
		case LESSEQUAL:
			fun = InternalFunction::lessEqual;
			break;
		case LPAREN:
			break;
		case MINUS:
			fun = InternalFunction::sub;
			break;
		case MULTIPLIED:
			fun = InternalFunction::multiply;
			break;
		case NOT:
			fun = InternalFunction::not;
			break;
		case NOTEQUAL:
			fun = InternalFunction::notEqual;
			break;
		case OR:
			fun = InternalFunction::or;
			break;
		case RPAREN:
			break;
		default:
			break;
		}
		
		return fun;
	}
	
	public void check(Data fun, List<Class<?>> argumentClazzs) {
		Class<?>[] parameterClazzs =  fun.method.getParameterTypes();
		if (argumentClazzs.size() > parameterClazzs.length) {
			throw new FunctionException("Too many parameters: ");
		}
		int index = 0;
		for (Class<?> argClazz : argumentClazzs) {
			Class<?>[] specifyClses = fun.paramClazzs.get(index);
			if (specifyClses != null) {
				boolean bMatch = false;
				for (Class<?> specifyCls : specifyClses) {
					if (specifyCls == argClazz) {
						bMatch = true;
						break;
					}
				}
				if (!bMatch) {
					throw new FunctionException("Invalid parameter type: " + argClazz.getName());
				}
			} else if (parameterClazzs[index] != argClazz) {
				throw new FunctionException("Invalid parameter type: " + argClazz.getName());
			}
			++index;
		}
		if ((parameterClazzs.length - index) > fun.optionals) {
			throw new FunctionException("缺少必填项!");
		}
	}
	
	/*public void registerFunction(Class<?> cls, String name) {
		for (Method method : cls.getMethods()) {
			if (Modifier.isStatic(method.getModifiers()) && method.getName().equals(name)) {
				InnerData data = new InnerData();
				data.method = method;
				Class<?>[] paramClses = method.getParameterTypes();
				if (paramClses.length == 1 && paramClses[0] == List.class) {
				}
				ELFunction funAnno = method.getAnnotation(ELFunction.class);
				if (funAnno != null) {
					data.returnBy = funAnno.returnBy();
					data.optionals = funAnno.options();
				} else {
					data.returnBy = 0;
					data.optionals = 0;
				}
				
				Annotation[][] paramAnno = method.getParameterAnnotations();
				for (Class<?> paramCls : paramClses) {
					
				}
				functions.put(name.toUpperCase(), data);
				break;
			}
		}
	}*/
	
	public void registerUserFunction(Class<?> clazz) {
		for (Method method : clazz.getMethods()) {
			ELFunction funAnno = method.getAnnotation(ELFunction.class);
			if (funAnno == null) {
				continue;
			}
			
			if (!Modifier.isStatic(method.getModifiers())) {
				// 自定义函数表达式必须为静态类型
				continue;
			}
			
			InnerData data = new InnerData();
			data.method = method;
			data.returnBy = funAnno.returnBy();
			data.optionals = funAnno.options();
			
			Annotation[][] paramAnnoes = method.getParameterAnnotations();
			for (int i = 0; i < paramAnnoes.length; ++i) {
				if (paramAnnoes[i] == null || paramAnnoes[i].length == 0 || paramAnnoes[i][0].annotationType() != ELParm.class) {
					continue;
				}
				
				ELParm paramAnno = (ELParm)paramAnnoes[i][0];
				data.paramClazzs.put(i, paramAnno.clazz());
			}
			
			String methodName = method.getName().toUpperCase();
			if (methodName.startsWith("FUN")) {
				methodName = methodName.substring(3);
			}
			functions.put(methodName, data);
		}
	}
	
	public ValueAbstract invoke(Data fun, List<ValueAbstract> params, Class<?> returnClass) {
		if (fun.getClass() == InnerData.class) {
			try {
				ValueAbstract value = invokeInner((InnerData)fun, params);
				if (value != null) {
					return value;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			}
		} else {
			try {
				Object value = invokeOuter((OuterData)fun, params);
				if (value != null) {
					
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			}
		}
		throw new ValidationException();
	}
	
	private ValueAbstract invokeInner(InnerData fun, List<ValueAbstract> params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object value = null;
		if (fun.optionals > 0) {
			for (int i = params.size() + 1; i <= fun.paramClazzs.size(); ++i) {
				params.add(null);
			}
		}
		switch (params.size()) {
		case 0:
			value = fun.method.invoke(null);
			break;
		case 1:
			value = fun.method.invoke(null, params.get(0));
			break;
		case 2:
			value = fun.method.invoke(null, params.get(0), params.get(1));
			break;
		case 3:
			value = fun.method.invoke(null, params.get(0), params.get(1), params.get(2));
			break;
		default:
			throw new FunctionException();
		}
		
		return (ValueAbstract)value;
	}
	
	public Object invokeOuter(OuterData fun, List<ValueAbstract> params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object value = null;
		if (fun.optionals > 0) {
			for (int i = params.size() + 1; i <= fun.paramClazzs.size(); ++i) {
				params.add(null);
			}
		}
		switch (params.size()) {
		case 0:
			value = fun.method.invoke(fun.service);
			break;
		case 1:
			value = fun.method.invoke(fun.service, getValue(params, 0));
			break;
		case 2:
			value = fun.method.invoke(fun.service, getValue(params, 0), getValue(params, 1));
			break;
		case 3:
			value = fun.method.invoke(fun.service, getValue(params, 0), getValue(params, 1), getValue(params, 2));
			break;
		default:
			throw new FunctionException();
		}
		
		return value;
	}
	
	private Object getValue(List<ValueAbstract> params, int index) {
		ValueAbstract value = params.get(index);
		return value != null ? value.getValue() : null;
	}
	
	public Set<String> getAllFunctions() {
		return this.functions.keySet();
	}
}
