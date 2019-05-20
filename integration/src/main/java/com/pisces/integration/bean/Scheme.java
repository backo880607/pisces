package com.pisces.integration.bean;

import java.util.Collection;

import javax.validation.constraints.NotBlank;

import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EntityCoding;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;
import com.pisces.integration.enums.ImportType;

public class Scheme extends EntityCoding {
	private ImportType importType = ImportType.ReplaceImport;
	private String filter;
	private String orderBy;
	@NotBlank
	private String outName;
	@NotBlank
	private String inName;
	
	public static final Sign schemeGroup = sign();
	@Relation(clazz="DataSource", type=Type.MultiToOne)
	public static final Sign dataSource = sign();
	
	@Relation(clazz="FieldInfo", sign="scheme", type=Type.OneToMulti, owner=true)
	public static final Sign fields = sign();
	
	public final ImportType getImportType() {
		return importType;
	}
	
	public final void setImportType(ImportType importType) {
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
	
	public final SchemeGroup getSchemeGroup() {
		return getEntity(schemeGroup);
	}
	
	public final DataSource getDataSource() {
		return getEntity(dataSource);
	}
	
	public final Collection<FieldInfo> getFields() {
		return getEntities(fields);
	}
}
