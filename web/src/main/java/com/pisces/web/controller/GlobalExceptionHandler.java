package com.pisces.web.controller;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pisces.core.locale.LocaleManager;
import com.pisces.web.annotation.ExceptionMessage;
import com.pisces.web.config.WebMessage;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private WebMvcConfigurationSupport webConfig;
	
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
		BindingResult bindingResult = ex.getBindingResult();
		r.setException(bindingResult.toString());
		return new ResponseEntity<Object>(r, headers, status);
	}
}
