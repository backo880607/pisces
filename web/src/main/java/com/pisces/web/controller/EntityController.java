package com.pisces.web.controller;

import java.util.List;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.service.EntityService;
import com.pisces.core.utils.PageParam;
import com.pisces.core.validator.InsertGroup;
import com.pisces.core.validator.UpdateGroup;
import com.pisces.web.annotation.ExceptionMessage;
import com.pisces.web.config.WebMessage;

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
	
	@GetMapping("select")
	@ExceptionMessage(clazz = WebMessage.class, name = "SELECT")
	public ResponseData select() {
		return succeed(this.service.select());
	}
	
	@GetMapping("selectAll")
	@ExceptionMessage(clazz = WebMessage.class, name = "SELECT")
	public ResponseData selectAll() {
		return succeed(this.service.selectAll());
	}
	
	@GetMapping(value = "selectByPage")
	@ExceptionMessage(clazz = WebMessage.class, name = "SELECT")
	public ResponseData selectByPage(@RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize, 
			@RequestParam(required = true) String orderBy, @RequestParam(required = true) String filter) {
		PageParam param = new PageParam();
		param.setPageNum(pageNum);
		param.setPageSize(pageSize);
		param.setOrderBy(orderBy);
		param.setFilter(filter);
		return succeed(this.service.select(param));
	}
	
	@GetMapping("selectById")
	@ExceptionMessage(clazz = WebMessage.class, name = "SELECT")
	public ResponseData selectById(@RequestParam long id) {
		final T entity = this.service.selectById(id);
		return entity != null ? succeed(entity) : failed(id, WebMessage.NOT_EXISTED);
	}
	
	@GetMapping("selectByIds")
	@ExceptionMessage(clazz = WebMessage.class, name = "SELECT")
	public ResponseData selectByIds(@RequestBody List<Long> ids) {
		return succeed(this.service.selectByIds(ids));
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
