package com.pisces.platform.rds.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.utils.AppUtils;
import com.pisces.platform.core.utils.EntityUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserFieldTypeHandler extends BaseTypeHandler<Map<String, Object>> {
    public static ThreadLocal<Class<? extends EntityObject>> entityClazz = new ThreadLocal<>();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType)
            throws SQLException {
        try {
            ps.setString(i, EntityUtils.defaultObjectMapper().writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> getResult(String value) {
        Map<String, Object> result = new HashMap<String, Object>();
        ObjectMapper mapper = EntityUtils.defaultObjectMapper();
        try (JsonParser parser = mapper.getFactory().createParser(value)) {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = parser.getCurrentName();
                if (fieldName == null) {
                    continue;
                }
                parser.nextToken();
                Property property = AppUtils.getPropertyService().get(entityClazz.get(), fieldName);
                if (property != null) {
                    /*
                     * Object userValue = EntityUtils.convertTextValue(property, parser.getText(),
                     * null); if (userValue != null) { result.put(fieldName, userValue); }
                     */
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (!rs.wasNull()) {
            return getResult(value);
        }
        return null;
    }

    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        if (!rs.wasNull()) {
            return getResult(value);
        }
        return null;
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        if (!cs.wasNull()) {
            return getResult(value);
        }
        return null;
    }

}
