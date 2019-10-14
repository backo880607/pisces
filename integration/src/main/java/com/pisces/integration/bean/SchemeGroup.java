package com.pisces.integration.bean;

import java.util.Collection;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;
import com.pisces.integration.enums.SCHEME_TYPE;

@Table(name = "INTEGRATION_SCHEME_GROUP")
public class SchemeGroup extends EntityCoding {
	@NotNull
	private SCHEME_TYPE type;
	
	@Relation(clazz="Scheme", sign="schemeGroup", type=Type.OneToMulti, owner=true)
	public static final Sign schemes = sign();
	
	@Override
	public void init() {
		super.init();
		type = SCHEME_TYPE.Export;
	}

	public final SCHEME_TYPE getType() {
		return type;
	}

	public final void setType(SCHEME_TYPE type) {
		this.type = type;
	}
	
	public final Collection<Scheme> getSchemes() {
		return this.getEntities(schemes);
	}
}
