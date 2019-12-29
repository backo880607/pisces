package com.pisces.platform.web.controller;

import com.pisces.platform.core.dao.DaoManager;
import com.pisces.platform.core.locale.LocaleManager;
import com.pisces.platform.web.config.WebMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private ResponseData result(boolean success, Object data, Enum<?> message, Object... arguments) {
        DaoManager.sync();
        ResponseData responseResult = new ResponseData();
        responseResult.setSuccess(success);
        if (message != null) {
            responseResult.setStatus(message.ordinal());
            responseResult.setName(message.name());
            responseResult.setMessage(LocaleManager.getLanguage(message, arguments));
        }
        responseResult.setData(data);
        return responseResult;
    }

    protected ResponseData exception(Exception ex, Enum<?> message, Object... arguments) {
        if (message == null) {
            message = WebMessage.SYSTEM;
        }
        ResponseData responseResult = new ResponseData();
        responseResult.setSuccess(false);
        responseResult.setStatus(message.ordinal());
        responseResult.setName(message.name());
        responseResult.setMessage(LocaleManager.getLanguage(message, arguments));
        responseResult.setException(ex.getMessage());
        responseResult.setData(null);
        return responseResult;
    }

    protected ResponseData succeed() {
        return succeed(null);
    }

    protected ResponseData succeed(Object data) {
        return succeed(data, null);
    }

    protected ResponseData succeed(Enum<?> status, Object... arguments) {
        return succeed(null, status, arguments);
    }

    protected ResponseData succeed(Object data, Enum<?> status, Object... arguments) {
        return result(true, data, status, arguments);
    }

    protected ResponseData failed() {
        return failed(null);
    }

    protected ResponseData failed(Object data) {
        return failed(data, null);
    }

    protected ResponseData failed(Enum<?> status, Object... arguments) {
        return failed(null, status, arguments);
    }

    protected ResponseData failed(Object data, Enum<?> status, Object... arguments) {
        return result(false, data, status, arguments);
    }
}
