package com.pisces.integration.bean;

import java.util.Collection;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;
import com.pisces.integration.enums.IMPORT_TYPT;

@Table(name = "INTEGRATION_SCHEME")
public class Scheme extends EntityCoding {
	@NotNull
	private IMPORT_TYPT importType;
	private String filter;
	private String orderBy;
	@NotBlank
	private String outName;
	@NotBlank
	private String inName;
	
	public static final Sign schemeGroup = sign();
	
	@Relation(clazz="FieldInfo", type=Type.OneToMulti, owner=true)
	public static final Sign fields = sign();
	
	@Override
	public void init() {
		super.init();
		importType = IMPORT_TYPT.ReplaceImport;
		filter = "";
		orderBy = "";
		outName = "";
		inName = "";
	}
	
	public final IMPORT_TYPT getImportType() {
		return importType;
	}
	
	public final void setImportType(IMPORT_TYPT importType) {
		this.importType = importType;
	}
	
	public final String getFilter() {
		return filter;
	}
	
	public final void setFilter(String filter) {
		this.filter = filter;
	}
	
	public final String getOrderBy() {
		return orderBy;
	}
	
	public final void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public final String getOutName() {
		return outName;
	}
	
	public final void setOutName(String outName) {
		this.outName = outName;
	}
	
	public final String getInName() {
		return inName;
	}
	
	public final void setInName(String inName) {
		this.inName = inName;
	}

	public SchemeGroup getSchemeGroup() {
		return get(schemeGroup);
	}
	
	public void setSchemeGroup(SchemeGroup schemeGroup) {
		set(Scheme.schemeGroup, schemeGroup);
	}
	
	public final Collection<FieldInfo> getFields() {
		return getList(fields);
	}
}
