package com.pisces.integration.bean;

import java.util.Collection;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;
import com.pisces.integration.enums.SchemeType;

public class SchemeGroup extends EntityCoding {
	private SchemeType type = SchemeType.Export;
	
	@Relation(clazz="Scheme", sign="schemeGroup", type=Type.OneToMulti, owner=true)
	public static final Sign schemes = sign();

	public final SchemeType getType() {
		return type;
	}

	public final void setType(SchemeType type) {
		this.type = type;
	}
	
	public final Collection<Scheme> getSchemes() {
		return this.getEntities(schemes);
	}
}
