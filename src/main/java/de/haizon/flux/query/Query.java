package de.haizon.flux.query;

import de.haizon.flux.Flux;
import de.haizon.flux.annotations.Entity;
import de.haizon.flux.annotations.Index;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Query<T> {

    private final Class<T> clazz;
    private final Entity entity;
    private final StringBuilder queryBuilder;

    public Query(Class<T> clazz) {
        this.clazz = clazz;
        this.entity = clazz.getAnnotation(Entity.class);
        this.queryBuilder = new StringBuilder("SELECT * FROM " + entity.tableName());
    }

    public Query<T> where(String column, String value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" = '").append(value).append("'");
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" = '").append(value).append("'");
        }
        return this;
    }

    public Query<T> where(String column, int value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" = ").append(value);
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" = ").append(value);
        }
        return this;
    }

    // Greater Than
    public Query<T> whereGreaterThan(String column, int value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" > ").append(value);
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" > ").append(value);
        }
        return this;
    }

    public Query<T> whereGreaterThan(String column, String value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" > '").append(value).append("'");
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" > '").append(value).append("'");
        }
        return this;
    }

    // Less Than
    public Query<T> whereLessThan(String column, int value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" < ").append(value);
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" < ").append(value);
        }
        return this;
    }

    public Query<T> whereLessThan(String column, String value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" < '").append(value).append("'");
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" < '").append(value).append("'");
        }
        return this;
    }

    // Greater Than or Equal
    public Query<T> whereGreaterThanOrEqual(String column, int value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" >= ").append(value);
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" >= ").append(value);
        }
        return this;
    }

    public Query<T> whereGreaterThanOrEqual(String column, String value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" >= '").append(value).append("'");
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" >= '").append(value).append("'");
        }
        return this;
    }

    // Less Than or Equal
    public Query<T> whereLessThanOrEqual(String column, int value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" <= ").append(value);
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" <= ").append(value);
        }
        return this;
    }

    public Query<T> whereLessThanOrEqual(String column, String value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" <= '").append(value).append("'");
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" <= '").append(value).append("'");
        }
        return this;
    }

    // Not Equal
    public Query<T> whereNotEqual(String column, String value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" != '").append(value).append("'");
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" != '").append(value).append("'");
        }
        return this;
    }

    public Query<T> whereNotEqual(String column, int value) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" != ").append(value);
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" != ").append(value);
        }
        return this;
    }

    // LIKE (for pattern matching)
    public Query<T> whereLike(String column, String pattern) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" LIKE '").append(pattern).append("'");
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" LIKE '").append(pattern).append("'");
        }
        return this;
    }

    // IN (check if value is in a list)
    public Query<T> whereIn(String column, List<?> values) {
        StringBuilder valueList = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) valueList.append(", ");
            Object val = values.get(i);
            if (val instanceof String) {
                valueList.append("'").append(val).append("'");
            } else {
                valueList.append(val);
            }
        }
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" IN (").append(valueList).append(")");
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" IN (").append(valueList).append(")");
        }
        return this;
    }

    // BETWEEN
    public Query<T> whereBetween(String column, int start, int end) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" BETWEEN ").append(start).append(" AND ").append(end);
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" BETWEEN ").append(start).append(" AND ").append(end);
        }
        return this;
    }

    public Query<T> whereBetween(String column, String start, String end) {
        if (queryBuilder.toString().contains("WHERE")) {
            queryBuilder.append(" AND ").append(column).append(" BETWEEN '").append(start).append("' AND '").append(end).append("'");
        } else {
            queryBuilder.append(" WHERE ").append(column).append(" BETWEEN '").append(start).append("' AND '").append(end).append("'");
        }
        return this;
    }

    public T first() {
        List<T> results = execute();
        return results.isEmpty() ? null : results.get(0);
    }

    public List<T> execute() {
        List<T> results = new ArrayList<>();

        try {
            Statement statement = Flux.getWrapper().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(queryBuilder.toString());

            while (resultSet.next()) {
                T instance = clazz.getDeclaredConstructor().newInstance();

                for (Field field : clazz.getFields()) {
                    if (field.getAnnotation(Index.class) != null) {
                        String fieldName = field.getName();
                        Object value = resultSet.getObject(fieldName);

                        if (value != null) {
                            if (field.getType() == String.class) {
                                field.set(instance, value.toString());
                            } else if (field.getType() == int.class) {
                                field.set(instance, Integer.parseInt(value.toString()));
                            } else if (field.getType() == long.class) {
                                field.set(instance, Long.parseLong(value.toString()));
                            } else if (field.getType() == double.class) {
                                field.set(instance, Double.parseDouble(value.toString()));
                            } else if (field.getType() == boolean.class) {
                                field.set(instance, Boolean.parseBoolean(value.toString()));
                            } else {
                                field.set(instance, value);
                            }
                        }
                    }
                }
                results.add(instance);
            }

            statement.close();
        } catch (SQLException | ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return results;
    }

    public void delete() {
        String deleteQuery = queryBuilder.toString().replaceFirst("SELECT \\* FROM", "DELETE FROM");
        Flux.getWrapper().execute(deleteQuery);
    }

    @Override
    public String toString() {
        return queryBuilder.toString();
    }
}
