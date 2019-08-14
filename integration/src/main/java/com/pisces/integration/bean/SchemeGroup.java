package com.pisces.integration.bean;

import java.util.Collection;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;
import com.pisces.integration.enums.SchemeType;

@Table(name = "integration_scheme_group")
public class SchemeGroup extends EntityCoding {
	@NotNull
	private SchemeType type;
	
	@Relation(clazz="Scheme", sign="schemeGroup", type=Type.OneToMulti, owner=true)
	public static final Sign schemes = sign();
	
	@Override
	public void init() {
		super.init();
		type = SchemeType.Export;
	}

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
