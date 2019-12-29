package com.pisces.platform.framework.bean.command;

import com.pisces.platform.core.annotation.PrimaryKey;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.relation.Sign;

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
		return get(group);
	}
	
	public void setGroup(CommandGroup group) {
		set(Command.group, group);
	}
}
