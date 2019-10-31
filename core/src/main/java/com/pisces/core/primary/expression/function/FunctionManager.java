package com.pisces.core.primary.expression.function;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import javax.validation.ValidationException;

import com.pisces.core.annotation.ELFunction;
import com.pisces.core.annotation.ELParm;
import com.pisces.core.config.CoreMessage;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.exception.ExpressionException;
import com.pisces.core.primary.expression.OperType;
import com.pisces.core.service.EntityService;

public class FunctionManager {
	public static class Data {
		public Method method;
		public int returnBy = 0;
		public int optionals = 0;
		Map<Integer, Class<?>[]> paramClazzs = new HashMap<>();
	}
	
	public static class InnerData extends Data {
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
		EntityFunction.register(this);
	}
	
	public static FunctionManager instance = new FunctionManager();
	
	public Data getFunction(String name) {
		Data data = this.functions.get(name.trim().toUpperCase());
		if (data == null) {
			throw new ExpressionException(CoreMessage.InvalidFunctionName, name);
		}
		
		return data;
	}
	
	public BiFunction<Object, Object, Object> getFunction(OperType operType) {
		BiFunction<Object, Object, Object> fun = null;
		switch (operType) {
		case PLUS:
			fun = BaseFunction::plus;
			break;
		case AND:
			fun = BaseFunction::and;
			break;
		case DATA:
			break;
		case DIVIDED:
			fun = BaseFunction::division;
			break;
		case EOL:
			break;
		case EQUAL:
			fun = BaseFunction::equal;
			break;
		case FUN:
			break;
		case GREATER:
			fun = BaseFunction::greater;
			break;
		case GREATEREQUAL:
			fun = BaseFunction::greaterEqual;
			break;
		case LESS:
			fun = BaseFunction::less;
			break;
		case LESSEQUAL:
			fun = BaseFunction::lessEqual;
			break;
		case LPAREN:
			break;
		case MINUS:
			fun = BaseFunction::minus;
			break;
		case MULTIPLIED:
			fun = BaseFunction::multiply;
			break;
		case NOT:
			fun = BaseFunction::not;
			break;
		case NOTEQUAL:
			fun = BaseFunction::notEqual;
			break;
		case OR:
			fun = BaseFunction::or;
			break;
		case RPAREN:
			break;
		default:
			break;
		}
		
		return fun;
	}
	
	public BiFunction<Class<?>, Class<?>, Class<?>> getReturnClass(OperType operType) {
		BiFunction<Class<?>, Class<?>, Class<?>> fun = null;
		switch (operType) {
		case PLUS:
			fun = BaseFunction::plusClass;
			break;
		case AND:
			fun = BaseFunction::andClass;
			break;
		case DATA:
			break;
		case DIVIDED:
			fun = BaseFunction::divisionClass;
			break;
		case EOL:
			break;
		case EQUAL:
			fun = BaseFunction::equalClass;
			break;
		case FUN:
			break;
		case GREATER:
			fun = BaseFunction::greaterClass;
			break;
		case GREATEREQUAL:
			fun = BaseFunction::greaterEqualClass;
			break;
		case LESS:
			fun = BaseFunction::lessClass;
			break;
		case LESSEQUAL:
			fun = BaseFunction::lessEqualClass;
			break;
		case LPAREN:
			break;
		case MINUS:
			fun = BaseFunction::minusClass;
			break;
		case MULTIPLIED:
			fun = BaseFunction::multiplyClass;
			break;
		case NOT:
			fun = BaseFunction::notClass;
			break;
		case NOTEQUAL:
			fun = BaseFunction::notEqualClass;
			break;
		case OR:
			fun = BaseFunction::orClass;
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
			throw new ExpressionException(CoreMessage.TooManyParams, "");
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
					throw new ExpressionException(CoreMessage.InvalidParamType, argClazz.getName());
				}
			} else if (parameterClazzs[index] != argClazz) {
				throw new ExpressionException(CoreMessage.InvalidParamType, argClazz.getName());
			}
			++index;
		}
		if ((parameterClazzs.length - index) > fun.optionals) {
			throw new ExpressionException(CoreMessage.LessParams);
		}
	}
	
	public void registerInnerFunction(Class<?> clazz) {
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
	
	public Object invoke(Data fun, List<Object> params, Class<?> returnClass) {
		if (fun.getClass() == InnerData.class) {
			try {
				Object value = invokeInner((InnerData)fun, params);
				if (value != null) {
					return value;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			}
		} else {
			try {
				Object value = invokeOuter((OuterData)fun, params);
				if (value != null) {
					return value;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			}
		}
		throw new ValidationException();
	}
	
	private Object invokeInner(InnerData fun, List<Object> params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
			throw new UnsupportedOperationException();
		}
		
		return value;
	}
	
	public Object invokeOuter(OuterData fun, List<Object> params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
			value = fun.method.invoke(fun.service, params.get(0));
			break;
		case 2:
			value = fun.method.invoke(fun.service, params.get(0), params.get(1));
			break;
		case 3:
			value = fun.method.invoke(fun.service, params.get(0), params.get(1), params.get(2));
			break;
		default:
			throw new UnsupportedOperationException();
		}
		
		return value;
	}
	
	public Set<String> getAllFunctions() {
		return this.functions.keySet();
	}
}
