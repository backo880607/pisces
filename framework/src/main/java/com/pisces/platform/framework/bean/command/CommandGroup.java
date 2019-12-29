package com.pisces.platform.framework.bean.command;

import com.pisces.platform.core.annotation.PrimaryKey;
import com.pisces.platform.core.annotation.Relation;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;

import javax.persistence.Table;
import java.util.Collection;

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
