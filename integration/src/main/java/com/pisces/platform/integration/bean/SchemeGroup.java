package com.pisces.platform.integration.bean;

import com.pisces.platform.core.annotation.Relation;
import com.pisces.platform.core.entity.EntityCoding;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;
import com.pisces.platform.integration.enums.SCHEME_TYPE;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Table(name = "INTEGRATION_SCHEME_GROUP")
public class SchemeGroup extends EntityCoding {
	@NotNull
	private SCHEME_TYPE type;

	@Relation(clazz="DataSource", type= Type.MultiToOne)
	public static final Sign dataSource = sign();
	
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
		return this.getList(schemes);
	}
	
	public final DataSource getDataSource() {
		return get(dataSource);
	}
	
	public void setDataSource(DataSource dataSource) {
		set(SchemeGroup.dataSource, dataSource);
	}
}
