package com.pisces.rds.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

public class LocaleHandler extends BaseTypeHandler<Locale> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Locale parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.toString());
	}

	@Override
	public Locale getNullableResult(ResultSet rs, String columnName) throws SQLException {
		final String value = rs.getString(columnName);
		return parse(value);
	}

	@Override
	public Locale getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		final String value = rs.getString(columnIndex);
		return parse(value);
	}

	@Override
	public Locale getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		final String value = cs.getString(columnIndex);
		return parse(value);
	}

	private Locale parse(String lang) {
		if (StringUtils.isEmpty(lang)) {
			return Locale.SIMPLIFIED_CHINESE;
		}
		String language = "";
        String country =  "";
        String variant =  "";
        String separator = "_";

        int i1 = lang.indexOf(separator);
        if ( i1 < 0 ) {
            language = lang;
        } else {
            language = lang.substring(0, i1);
            ++i1;
            int i2 = lang.indexOf(separator, i1);
            if (i2 < 0) {
                country = lang.substring(i1);
            } else {
                country = lang.substring(i1, i2);
                variant = lang.substring(i2+1);
            }
        }
        
        if(language.length() == 2) {
           language = language.toLowerCase();
        } else {
          language = "";
        }
        
        if(country.length() == 2) {
           country = country.toUpperCase();
        } else {
          country = "";
        }
        
        if((variant.length() > 0) && 
            ((language.length() == 2) ||(country.length() == 2))) {
           variant = variant.toUpperCase();
        } else {
            variant = "";
        }
        return new Locale(language, country, variant );
	}
}
