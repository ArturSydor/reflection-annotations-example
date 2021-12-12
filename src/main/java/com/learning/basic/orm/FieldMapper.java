package com.learning.basic.orm;

import com.learning.basic.orm.annotation.Column;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

@Slf4j
class FieldMapper {

    private static final Map<Class<?>, PreparedSetter> mappers = new HashMap<>();

    static {
        mappers.put(Integer.class, (ps, index, value) -> ps.setInt(index, (int) value));
        mappers.put(Long.class, (ps, index, value) -> ps.setLong(index, (long) value));
        mappers.put(String.class, (ps, index, value) -> ps.setString(index, (String) value));
        mappers.put(BigDecimal.class, (ps, index, value) -> ps.setBigDecimal(index, (BigDecimal) value));
    }

    public void setUpPreparedStatement(PreparedStatement ps, Object... values) {
        int index = 1;
        for (Object value : values) {
            try {
                if (Objects.nonNull(value)) {
                    mappers.get(value.getClass()).setField(ps, index, value);
                } else {
                    ps.setNull(index, Types.NULL);
                }
                index++;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public <ObjectType> Optional<ObjectType> mapToObject(ResultSet rs, Class<ObjectType> clazz) throws SQLException {
        try {
            var newObject = clazz.getConstructor().newInstance();

            if (rs.next()) {
                for (Field field : clazz.getDeclaredFields()) {
                    var value = rs.getObject(field.getAnnotation(Column.class).value());
                    var propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
                    var setter = propertyDescriptor.getWriteMethod();
                    setter.setAccessible(true);
                    setter.invoke(newObject, value);
                }
            } else {
                return Optional.empty();
            }

            return Optional.of(newObject);
        } catch (ReflectiveOperationException | IntrospectionException e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

}

@FunctionalInterface
interface PreparedSetter {

    void setField(PreparedStatement ps, int index, Object value) throws SQLException;

}