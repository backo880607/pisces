package com.pisces.user.bean;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Table;

import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;
import com.pisces.core.utils.DateUtils;

@Table(name = "USER_TENANT")
public class Tenant extends EntityCoding {
	private Date endDate;
	private Integer maxLoginQty;
	private Integer maxAccountQty;
	
	public static final Sign user = sign();
	
	@Override
	public void init() {
		super.init();
		endDate = DateUtils.MAX;
		maxLoginQty = 5;
		maxAccountQty = 5;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getMaxLoginQty() {
		return maxLoginQty;
	}

	public void setMaxLoginQty(Integer maxLoginQty) {
		this.maxLoginQty = maxLoginQty;
	}

	public Integer getMaxAccountQty() {
		return maxAccountQty;
	}

	public void setMaxAccountQty(Integer maxAccountQty) {
		this.maxAccountQty = maxAccountQty;
	}

	public Collection<Account> getUser() {
		return getEntities(user);
	}
}
