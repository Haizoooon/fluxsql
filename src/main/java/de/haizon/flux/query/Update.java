package de.haizon.flux.query;

import de.haizon.flux.Flux;
import de.haizon.flux.annotations.Entity;
import de.haizon.flux.annotations.Index;

import java.lang.reflect.Field;

public class Update<T> {

    private final Class<T> clazz;
    private final Entity entity;
    private final StringBuilder updateBuilder;
    private final StringBuilder whereBuilder;
    private boolean hasWhere = false;

    public Update(Class<T> clazz) {
        this.clazz = clazz;
        this.entity = clazz.getAnnotation(Entity.class);
        this.updateBuilder = new StringBuilder("UPDATE " + entity.tableName() + " SET ");
        this.whereBuilder = new StringBuilder();
    }

    public Update<T> set(String column, String value) {
        if (updateBuilder.toString().contains("SET ") && !updateBuilder.toString().endsWith("SET ")) {
            updateBuilder.append(", ");
        }
        updateBuilder.append(column).append(" = '").append(value).append("'");
        return this;
    }

    public Update<T> set(String column, int value) {
        if (updateBuilder.toString().contains("SET ") && !updateBuilder.toString().endsWith("SET ")) {
            updateBuilder.append(", ");
        }
        updateBuilder.append(column).append(" = ").append(value);
        return this;
    }

    public Update<T> set(String column, long value) {
        if (updateBuilder.toString().contains("SET ") && !updateBuilder.toString().endsWith("SET ")) {
            updateBuilder.append(", ");
        }
        updateBuilder.append(column).append(" = ").append(value);
        return this;
    }

    public Update<T> set(String column, double value) {
        if (updateBuilder.toString().contains("SET ") && !updateBuilder.toString().endsWith("SET ")) {
            updateBuilder.append(", ");
        }
        updateBuilder.append(column).append(" = ").append(value);
        return this;
    }

    public Update<T> where(String column, String value) {
        if (hasWhere) {
            whereBuilder.append(" AND ");
        } else {
            hasWhere = true;
        }
        whereBuilder.append(column).append(" = '").append(value).append("'");
        return this;
    }

    public Update<T> where(String column, int value) {
        if (hasWhere) {
            whereBuilder.append(" AND ");
        } else {
            hasWhere = true;
        }
        whereBuilder.append(column).append(" = ").append(value);
        return this;
    }

    public void execute() {
        String query = updateBuilder.toString();
        if (hasWhere) {
            query += " WHERE " + whereBuilder.toString();
        }
        Flux.getWrapper().executeUpdate(query);
    }

    @Override
    public String toString() {
        String query = updateBuilder.toString();
        if (hasWhere) {
            query += " WHERE " + whereBuilder.toString();
        }
        return query;
    }
}
