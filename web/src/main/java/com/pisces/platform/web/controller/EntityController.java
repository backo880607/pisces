package com.pisces.platform.web.controller;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.core.utils.PageParam;
import com.pisces.platform.core.validator.InsertGroup;
import com.pisces.platform.core.validator.UpdateGroup;
import com.pisces.platform.web.annotation.ExceptionMessage;
import com.pisces.platform.web.config.WebMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

@Validated
public abstract class EntityController<T extends EntityObject, S extends EntityService<T>> extends BaseController {
    @Autowired
    private S service;

    protected S getService() {
        return this.service;
    }

    @GetMapping(value = "create")
    @ExceptionMessage(clazz = WebMessage.class, name = "CREATE")
    public ResponseData create() {
        return succeed(this.service.create());
    }

    @GetMapping("get")
    @ExceptionMessage(clazz = WebMessage.class, name = "GET")
    public ResponseData get() {
        return succeed(this.service.get());
    }

    @GetMapping("getAll")
    @ExceptionMessage(clazz = WebMessage.class, name = "GET")
    public ResponseData getAll() {
        return succeed(this.service.getAll());
    }

    @GetMapping(value = "getByPage")
    @ExceptionMessage(clazz = WebMessage.class, name = "GET")
    public ResponseData getByPage(@RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize,
                                  @RequestParam(required = true) String orderBy, @RequestParam(required = true) String filter) {
        PageParam param = new PageParam();
        param.setPageNum(pageNum);
        param.setPageSize(pageSize);
        param.setOrderBy(orderBy);
        param.setFilter(filter);
        return succeed(this.service.get(param));
    }

    @GetMapping("getById")
    @ExceptionMessage(clazz = WebMessage.class, name = "GET")
    public ResponseData getById(@RequestParam long id) {
        final T entity = this.service.getById(id);
        return entity != null ? succeed(entity) : failed(id, WebMessage.NOT_EXISTED);
    }

    @GetMapping("getByIds")
    @ExceptionMessage(clazz = WebMessage.class, name = "GET")
    public ResponseData getByIds(@RequestBody List<Long> ids) {
        return succeed(this.service.getByIds(ids));
    }

    @PostMapping("insert")
    @ExceptionMessage(clazz = WebMessage.class, name = "INSERT")
    public ResponseData insert(@RequestBody @Validated({InsertGroup.class, Default.class}) T record) {
        this.service.insert(record);
        return succeed(record.getId());
    }

    @PostMapping("insertList")
    @ExceptionMessage(clazz = WebMessage.class, name = "INSERT")
    public ResponseData insertList(@RequestBody @Validated({InsertGroup.class, Default.class}) List<T> records) {
        return succeed(this.service.insertList(records));
    }

    @PutMapping("update")
    @ExceptionMessage(clazz = WebMessage.class, name = "UPDATE")
    public ResponseData update(@RequestBody @Validated({UpdateGroup.class, Default.class}) T record) {
        final int value = this.service.update(record);
        return value > 0 ? succeed(record.getId()) : failed(record.getId(), WebMessage.NOT_EXISTED);
    }

    @PutMapping("updateList")
    @ExceptionMessage(clazz = WebMessage.class, name = "UPDATE")
    public ResponseData updateList(@RequestBody @Validated({UpdateGroup.class, Default.class}) List<T> records) {
        return succeed(this.service.updateList(records));
    }

    @DeleteMapping("delete")
    @ExceptionMessage(clazz = WebMessage.class, name = "DELETE")
    public ResponseData delete(@RequestBody T record) {
        final int value = this.service.delete(record);
        return value > 0 ? succeed(1) : failed(1, WebMessage.NOT_EXISTED);
    }

    @DeleteMapping("deleteList")
    @ExceptionMessage(clazz = WebMessage.class, name = "DELETE")
    public ResponseData deleteList(@RequestBody List<T> records) {
        return succeed(this.service.deleteList(records));
    }

    @DeleteMapping("deleteById")
    @ExceptionMessage(clazz = WebMessage.class, name = "DELETE")
    public ResponseData deleteById(@RequestParam long id) {
        final int value = this.service.deleteById(id);
        return value > 0 ? succeed(id) : failed(id, WebMessage.NOT_EXISTED);
    }

    @DeleteMapping("deleteByIds")
    @ExceptionMessage(clazz = WebMessage.class, name = "DELETE")
    public ResponseData deleteByIds(@RequestBody List<Long> ids) {
        return succeed(this.service.deleteByIds(ids));
    }
}
