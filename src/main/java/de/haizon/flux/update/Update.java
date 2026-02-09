package de.haizon.flux.update;

import de.haizon.flux.Flux;
import de.haizon.flux.annotations.Entity;
import de.haizon.flux.result.UpdateResult;
import de.haizon.flux.result.UpdateResultType;

import java.util.function.Consumer;

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

    // Single execute() method - returns UpdateResult without callbacks
    public UpdateResult execute() {
        String query = buildQuery();
        try {
            int rowsAffected = Flux.getWrapper().executeUpdate(query);
            return new UpdateResult(rowsAffected, query);
        } catch (Exception e) {
            return new UpdateResult(e, query);
        }
    }

    // execute() with onSuccess callback only
    public UpdateResult execute(Consumer<UpdateResult> onSuccess) {
        return execute(UpdateResultType.SUCCESS, onSuccess, null);
    }

    // execute() with onSuccess and onFailure callbacks
    public UpdateResult execute(Consumer<UpdateResult> onSuccess, Consumer<Exception> onFailure) {
        return execute(UpdateResultType.BOTH, onSuccess, onFailure);
    }

    // Main execute() implementation with UpdateResultType
    public UpdateResult execute(UpdateResultType type, Consumer<UpdateResult> onSuccess, Consumer<Exception> onFailure) {
        UpdateResult result = execute();

        switch (type) {
            case SUCCESS:
                if (result.isSuccess() && onSuccess != null) {
                    onSuccess.accept(result);
                }
                break;
            case FAILURE:
                if (!result.isSuccess() && onFailure != null) {
                    onFailure.accept(result.getException());
                }
                break;
            case BOTH:
                if (result.isSuccess()) {
                    if (onSuccess != null) {
                        onSuccess.accept(result);
                    }
                } else {
                    if (onFailure != null) {
                        onFailure.accept(result.getException());
                    }
                }
                break;
        }

        return result;
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
