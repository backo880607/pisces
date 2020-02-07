package com.pisces.platform.user.bean;

import com.pisces.platform.core.annotation.PrimaryKey;
import com.pisces.platform.core.annotation.PropertyMeta;
import com.pisces.platform.core.annotation.Relation;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.relation.RelationKind;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Table(name = "USER_ACCOUNT")
@PrimaryKey(fields = {"username"})
public class Account extends EntityObject {
    private String username;
    @Length(min = 6, max = 32)
    private String password;
    private String salt;
    private Boolean accountExpired;
    private Boolean accountLocked;
    private Boolean credentialsExpired;
    @Email
    private String email;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$")
    @NotBlank()
    private String telephone;
    private Boolean sex;

    public static final Sign department = sign();

    @Relation(clazz = "Role", type = Type.MultiToMulti)
    public static final Sign roles = sign();

    @Override
    public void init() {
        super.init();
        username = "";
        password = "";
        salt = "";
        accountExpired = false;
        accountLocked = false;
        credentialsExpired = false;
        email = "";
        telephone = "";
        sex = true;
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

    public Department getDepartment() {
        return get(department);
    }

    public void setDepartment(Department department) {
        set(Account.department, department);
    }

    public Collection<Role> getRoles() {
        return getList(roles);
    }
}
