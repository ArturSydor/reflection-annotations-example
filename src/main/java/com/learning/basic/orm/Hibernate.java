package com.learning.basic.orm;

import com.learning.basic.orm.annotation.Column;
import com.learning.basic.orm.annotation.Entity;
import com.learning.basic.orm.annotation.PrimaryKey;
import com.learning.basic.orm.annotation.Table;
import com.learning.basic.orm.exception.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Hibernate<ObjectType, IdType> {

    private static final String INSERT_TEMPLATE = "insert into %s(%s) values(%s)";

    private static final String SELECT_TEMPLATE = "select %s from %s %s";

    private static final String URL = "jdbc:postgresql://localhost:5432/test";

    private static final String USER = "test";

    private static final String PASSWORD = "test";

    private final Connection connection;

    private final FieldMapper mapper;

    private Hibernate() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            mapper = new FieldMapper();
        } catch (SQLException ex) {
            log.error("Failed to create connection {}", ex.getMessage());
            throw new PersistenceException("Failed to create connection.", ex);
        }
    }

    public void executeQuery(String query) {
        try(var statement = createPreparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenceException("Exception during query execution.", ex);
        }
    }

    public static <ObjectType, IdType> Hibernate<ObjectType, IdType> getDbManager() {
        return new Hibernate<>();
    }

    public int save(ObjectType object) throws IllegalAccessException {
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

        try (PreparedStatement preparedStatement = createInsertPreparedStatement(tableName, columnsJoiner.toString(), values.size())) {
            mapper.setUpPreparedStatement(preparedStatement, values.toArray());
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new PersistenceException("Exception when saving: " + object, ex);
        }
    }

    public Optional<ObjectType> findById(IdType id, Class<ObjectType> clazz) {
        try {
            validateEntityDefinition(clazz);
            var table = clazz.getAnnotation(Table.class).value();
            var whereClause = "where %s = ?";

            var columns = new StringJoiner(",");

            for (Field field : clazz.getDeclaredFields()) {
                if(field.isAnnotationPresent(PrimaryKey.class)) {
                    whereClause = String.format(whereClause, field.getAnnotation(Column.class).value());
                }
                if (field.isAnnotationPresent(Column.class)) {
                    columns.add(field.getAnnotation(Column.class).value());
                }
            }

            var preparedStatement = createSelectPreparedStatement(table, columns.toString(), whereClause);
            mapper.setUpPreparedStatement(preparedStatement, id);

            return mapper.mapToObject(preparedStatement.executeQuery(), clazz);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            throw new PersistenceException(String.format("Exception when searching for %s by id=%s", clazz.getSimpleName(), id), ex);
        }
    }

    private PreparedStatement createSelectPreparedStatement(String tableName, String columns, String whereClause) throws SQLException {
        var sql = String.format(SELECT_TEMPLATE, columns, tableName, Objects.isNull(whereClause) ? "" : whereClause);
        return createPreparedStatement(sql);
    }

    private PreparedStatement createInsertPreparedStatement(String tableName, String columns, int columnsCount) throws SQLException {
        var sql = String.format(INSERT_TEMPLATE, tableName, columns, getPreparedParams(columnsCount));
        return createPreparedStatement(sql);
    }



    private PreparedStatement createPreparedStatement(String query) throws SQLException {
        log.debug(query);
        return connection.prepareStatement(query);
    }

    private String getPreparedParams(int numberOfColumns) {
        return IntStream.rangeClosed(0, numberOfColumns - 1)
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
