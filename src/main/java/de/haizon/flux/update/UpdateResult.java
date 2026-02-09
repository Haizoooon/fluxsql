package de.haizon.flux.update;

public class UpdateResult {

    private final UpdateResultType type;
    private final String message;

    public UpdateResult(UpdateResultType type, String message) {
        this.type = type;
        this.message = message;
    }

    public UpdateResultType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

}
