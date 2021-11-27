package com.learning.basic.orm;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class FieldMapper {

    private static final Map<Class<?>, PreparedSetter> mappers = new HashMap<>();

    static {
        mappers.put(Integer.class, (ps, index, value) -> ps.setInt(index, (int) value));
        mappers.put(Long.class, (ps, index, value) -> ps.setLong(index, (long) value));
        mappers.put(String.class, (ps, index, value) -> ps.setString(index, (String) value));
        mappers.put(BigDecimal.class, (ps, index, value) -> ps.setBigDecimal(index, (BigDecimal) value));
    }

    public void setUpPreparedStatement(PreparedStatement ps, List<Object> values) {
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

}

@FunctionalInterface
interface PreparedSetter {

    void setField(PreparedStatement ps, int index, Object value) throws SQLException;

}