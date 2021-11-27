package com.learning.basic.orm;

import com.learning.basic.orm.annotation.Column;
import com.learning.basic.orm.annotation.Entity;
import com.learning.basic.orm.annotation.PrimaryKey;
import com.learning.basic.orm.annotation.Table;
import com.learning.basic.orm.exception.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Hibernate<T> {

    private static final String URL = "";

    private static final String USER = "";

    private static final String PASSWORD = "";

    private static final String INSERT_TEMPLATE = "insert into %s(%s) values(%s)";

    private Connection connection;

    private final FieldMapper mapper;

    private Hibernate() {
//        try {
//            connection = DriverManager.getConnection("");

            mapper = new FieldMapper();
//        } catch (SQLException ex) {
//            log.error("Failed to create connection {}", ex.getMessage());
//            throw new PersistenceException("Failed to create connection.", ex);
//        }
    }

    public void executeQuery(String query) {
        try(var statement = createPreparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException("Exception during query execution.", ex);
        }
    }

    public static <T> Hibernate<T> getDbManager() {
        return new Hibernate<>();
    }

    public int save(T object) throws IllegalAccessException {
        var clazz = object.getClass();
        validateEntityDefinition(clazz);

        var tableName = clazz.getAnnotation(Table.class).value();

        List<Object> values = new ArrayList<>();
        StringJoiner columnsJoiner = new StringJoiner(",");

        var declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(Boolean.TRUE);
            if (field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(PrimaryKey.class)) {
                log.debug("{} = {}", field.getName(), field.get(object));
                columnsJoiner.add(field.getAnnotation(Column.class).value());
                values.add(field.get(object));
            }
        }

        try (PreparedStatement preparedStatement = createPreparedStatement(tableName, columnsJoiner.toString(), values.size())) {
            mapper.setUpPreparedStatement(preparedStatement, values);
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new PersistenceException("Exception when saving: " + object, ex);
        }
    }

    private PreparedStatement createPreparedStatement(String tableName, String columns, int columnsCount) throws SQLException {
        var sql = String.format(INSERT_TEMPLATE, tableName, columns, getPreparedParams(columnsCount));
        log.debug(sql);
        return connection.prepareStatement(sql);
    }

    private PreparedStatement createPreparedStatement(String query) throws SQLException {
        log.debug(query);
        return connection.prepareStatement(query);
    }

    private String getPreparedParams(int count) {
        return IntStream.rangeClosed(0, count)
                .mapToObj(i -> "?")
                .collect(Collectors.joining(","));
    }

    private void validateEntityDefinition(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException("Object is not marked with entity annotation, it cannot be saved to database.");
        }

        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalStateException("Entity table is not known.");
        }
    }

}
