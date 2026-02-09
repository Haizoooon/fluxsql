package de.haizon.flux.update;

import de.haizon.flux.Flux;
import de.haizon.flux.annotations.Entity;
import de.haizon.flux.result.UpdateResult;

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

    public UpdateResult execute() {
        String query = buildQuery();
        try {
            int rowsAffected = Flux.getWrapper().executeUpdate(query);
            return new UpdateResult(rowsAffected, query);
        } catch (Exception e) {
            return new UpdateResult(e, query);
        }
    }

    private String buildQuery() {
        String query = updateBuilder.toString();
        if (hasWhere) {
            query += " WHERE " + whereBuilder.toString();
        }
        return query;
    }

    @Override
    public String toString() {
        return buildQuery();
    }
}
