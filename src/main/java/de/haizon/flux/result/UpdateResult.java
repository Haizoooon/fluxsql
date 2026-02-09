package de.haizon.flux.result;

import java.util.function.Consumer;

public class UpdateResult {

    private final int rowsAffected;
    private final String query;
    private final boolean success;
    private Exception exception;

    public UpdateResult(int rowsAffected, String query) {
        this.rowsAffected = rowsAffected;
        this.query = query;
        this.success = true;
    }

    public UpdateResult(int rowsAffected, String query, boolean success) {
        this.rowsAffected = rowsAffected;
        this.query = query;
        this.success = success;
    }

    public UpdateResult(Exception exception, String query) {
        this.rowsAffected = 0;
        this.query = query;
        this.success = false;
        this.exception = exception;
    }

    public int getRowsAffected() {
        return rowsAffected;
    }

    public String getQuery() {
        return query;
    }

    public boolean isSuccess() {
        return success;
    }

    public Exception getException() {
        return exception;
    }

    // Lambda support methods

    public UpdateResult onSuccess(Consumer<UpdateResult> callback) {
        if (success) {
            callback.accept(this);
        }
        return this;
    }

    public UpdateResult onFailure(Consumer<Exception> callback) {
        if (!success && exception != null) {
            callback.accept(exception);
        }
        return this;
    }

    public UpdateResult then(Consumer<UpdateResult> callback) {
        callback.accept(this);
        return this;
    }

    public UpdateResult log() {
        if (success) {
            System.out.println("✓ Update successful: " + rowsAffected + " row(s) affected");
            System.out.println("  Query: " + query);
        } else {
            System.out.println("✗ Update failed");
            if (exception != null) {
                System.out.println("  Error: " + exception.getMessage());
            }
            System.out.println("  Query: " + query);
        }
        return this;
    }

    @Override
    public String toString() {
        return "UpdateResult{" +
                "rowsAffected=" + rowsAffected +
                ", success=" + success +
                ", query='" + query + '\'' +
                '}';
    }
}

