package com.pisces.rds.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.pisces.core.entity.DateDur;

public class DateDurHandler extends BaseTypeHandler<DateDur> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, DateDur parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getValue());
	}

	@Override
	public DateDur getNullableResult(ResultSet rs, String columnName) throws SQLException {
		final String value = rs.getString(columnName);
		return new DateDur(rs.wasNull() ? "" : value);
	}

	@Override
	public DateDur getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		final String value = rs.getString(columnIndex);
		return new DateDur(rs.wasNull() ? "" : value);
	}

	@Override
	public DateDur getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		final String value = cs.getString(columnIndex);
		return new DateDur(cs.wasNull() ? "" : value);
	}

}
