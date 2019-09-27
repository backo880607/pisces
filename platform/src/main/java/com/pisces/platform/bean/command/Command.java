package com.pisces.platform.bean.command;

import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Ioc;
import com.pisces.core.relation.Sign;

@PrimaryKey(fields={"code"})
public class Command extends EntityObject {
	private String code;
	
	public static final Sign group = sign();
	
	@Override
	public void init() {
		super.init();
		this.code = "";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CommandGroup getGroup() {
		return getEntity(group);
	}
	
	public void setGroup(CommandGroup group) {
		Ioc.set(this, Command.group, group);
	}
}
