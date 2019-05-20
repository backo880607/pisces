package com.pisces.user.bean;

import java.util.Collection;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.pisces.core.annotation.PrimaryKey;
import com.pisces.core.annotation.PropertyMeta;
import com.pisces.core.annotation.Relation;
import com.pisces.core.entity.EffectTaskType;
import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.RelationKind;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;

@PrimaryKey(fields={"username"})
public class Account extends EntityObject {
	private String username;
	@Length(min=6, max=32)
	private String password;
	private String salt;
	private Boolean accountExpired;
	private Boolean accountLocked;
	private Boolean credentialsExpired;
	private Boolean disabled;
	@Email
	private String email;
	@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String telephone;
	@NotNull
    private Boolean sex;
    private EffectTaskType type = new EffectTaskType(EffectTaskType.Impl.Manuf);
    
    @Relation(clazz = "Tenant", sign = "user", type = Type.MultiToMulti)
    @PropertyMeta(kind = RelationKind.Sequence)
    public static final Sign tenant = sign();
    
    @Relation(clazz = "Role", type=Type.MultiToMulti)
    @PropertyMeta(kind = RelationKind.Set)
    public static final Sign roles = sign();
    
    public Account() {
	}
    
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public Boolean getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public Boolean getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Boolean getCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(Boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public Boolean getSex() {
		return sex;
	}
	
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	
	public Collection<Tenant> getTenants() {
		return getEntities(tenant);
	}

	public EffectTaskType getType() {
		return type;
	}

	public void setType(EffectTaskType type) {
		this.type = type;
	}
}
