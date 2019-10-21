package com.pisces.platform.bean.command;

import java.util.Collection;

import javax.persistence.Table;

import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;

@Table(name = "PLATFORM_COMMAND_GROUP")
@PrimaryKey(fields={"code"})
public class CommandGroup extends EntityObject {
	private String code;
	
	@Relation(clazz = "Command", sign = "group", type = Type.OneToMulti, owner = true)
	public static final Sign commands = sign();
	
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
	
	public Collection<Command> getCommands() {
		return getList(commands);
	}
}
