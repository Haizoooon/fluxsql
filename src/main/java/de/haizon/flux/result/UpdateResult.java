package de.haizon.flux.result;

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


    @Override
    public String toString() {
        return "UpdateResult{" +
                "rowsAffected=" + rowsAffected +
                ", success=" + success +
                ", query='" + query + '\'' +
                '}';
    }
}

