package de.haizon.flux.test;


/**
 * Example of using UpdateResult with Lambda callbacks
 */
public class UpdateResultExample {

    public static void main(String[] args) {

        // Note: This is a documentation example. In actual usage, you would have a real database connection.
        // See the README.md for complete setup instructions.

        /*
        // Example 1: Simple update with lambda callbacks
        new Update<>(YourEntity.class)
                .set("health", 50)
                .where("uuid", "123e4567-e89b-12d3-a456-426614174000")
                .execute()
                .onSuccess(result -> {
                    System.out.println("✓ Update successful!");
                    System.out.println("  Rows affected: " + result.getRowsAffected());
                })
                .onFailure(error -> System.out.println("✗ Update failed: " + error.getMessage()))
                .log();

        // Example 2: Chain multiple callbacks
        new Update<>(YourEntity.class)
                .set("health", 75)
                .set("name", "Updated User")
                .where("uuid", "123e4567-e89b-12d3-a456-426614174000")
                .execute()
                .then(result -> System.out.println("Query executed: " + result.getQuery()))
                .then(result -> {
                    if (result.isSuccess()) {
                        System.out.println("Success with " + result.getRowsAffected() + " rows affected");
                    }
                })
                .log();

        // Example 3: Store result and use it later
        var updateResult = new Update<>(YourEntity.class)
                .set("health", 100)
                .where("uuid", "123e4567-e89b-12d3-a456-426614174000")
                .execute();

        if (updateResult.isSuccess()) {
            System.out.println("Updated " + updateResult.getRowsAffected() + " rows");
        } else {
            System.out.println("Error: " + updateResult.getException().getMessage());
        }
        */
    }

}

