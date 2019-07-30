package com.pisces.web.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.entity.Property;
import com.pisces.core.locale.ILanguageManager;
import com.pisces.core.locale.LocaleManager;
import com.pisces.core.utils.EntityUtils;
import com.pisces.core.validator.ErrorInfo;
import com.pisces.web.annotation.ExceptionMessage;
import com.pisces.web.config.WebMessage;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private WebMvcConfigurationSupport webConfig;
	
	@Autowired
	private ILanguageManager language;
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@ExceptionHandler({Exception.class})
    public ResponseData jsonHandler(HttpServletRequest request, Exception ex) throws Exception {
		ResponseData r = new ResponseData();
		Enum<?> message = WebMessage.UNKNOWN;
		
		HandlerExecutionChain chain = webConfig.requestMappingHandlerMapping().getHandler(request);
		HandlerMethod handlerMethod = (HandlerMethod)chain.getHandler();
		Method method = handlerMethod.getMethod();
		ExceptionMessage exceptionMessage = method.getAnnotation(ExceptionMessage.class);
		if (exceptionMessage != null) {
			Enum<?>[] values = exceptionMessage.clazz().getEnumConstants();
			for (Enum<?> value : values) {
				if (value.name().equals(exceptionMessage.name())) {
					message = value;
					break;
				}
			}
		}
		
		r.setSuccess(false);
		r.setStatus(message.ordinal());
		r.setName(message.name());
        r.setMessage(LocaleManager.getLanguage(message));
        r.setException(ex.getMessage());
        return r;
    }
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final Enum<?> message = WebMessage.INVALIDATION_ENTITY_PROPERTY;
		ResponseData r = new ResponseData();
		r.setSuccess(false);
		r.setStatus(message.ordinal());
		r.setName(message.name());
		r.setMessage(LocaleManager.getLanguage(message));
		r.setData(getErrorInfos(ex));
		return new ResponseEntity<Object>(r, headers, status);
	}
	
	// 获取验证错误提示消息
	private List<ErrorInfo> getErrorInfos(MethodArgumentNotValidException ex) {
		List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();
		BindingResult bindingResult = ex.getBindingResult();
		for (ObjectError error : bindingResult.getAllErrors()) {
			ErrorInfo info = new ErrorInfo();
			info.setClazz(error.getObjectName());
			if (error.getDefaultMessage().startsWith("{") &&
				error.getDefaultMessage().endsWith("}")) {
				info.setMessage(language.get(error.getDefaultMessage().substring(1, error.getDefaultMessage().length() - 1)));
			} else {
				info.setMessage(error.getDefaultMessage());
			}
			if (error instanceof FieldError) {
				FieldError fieldError = (FieldError)error;
				info.setField(fieldError.getField());
				info.setValue(fieldError.getRejectedValue().toString());
			}
			
			Object source = getSource(error);
			if (source != null && source instanceof ConstraintViolationImpl) {
				ConstraintViolationImpl<?> impl = (ConstraintViolationImpl<?>)source;
				Object rootValue = error instanceof FieldError ? impl.getRootBean() : impl.getInvalidValue();
				if (rootValue != null) {
					info.setClazz(language.get(rootValue.getClass()));
					if (error instanceof FieldError) {
						info.setField(language.get(rootValue.getClass(), ((FieldError)error).getField()));
					}
					if (EntityObject.class.isAssignableFrom(impl.getRootBeanClass())) {
						EntityObject entity = (EntityObject)rootValue;
						List<Property> properties = EntityUtils.getPrimaries(entity.getClass());
						for (Property property : properties) {
							info.getEntity().put(property.getName(), EntityUtils.getTextValue(entity, property));
						}
					} else if (info.getValue() == null) {
						info.setValue(rootValue.toString());
					}
				}
			}
			
			errorInfos.add(info);
		}
		return errorInfos;
	}
	
	private Object getSource(ObjectError error) {
		Field modifiersField = null;
		try {
			modifiersField = ObjectError.class.getDeclaredField("source");
			modifiersField.setAccessible(true); //Field 的 modifiers 是私有的
			return modifiersField.get(error);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
		}
		return null;
	}
}
