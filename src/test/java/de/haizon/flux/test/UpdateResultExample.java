package de.haizon.flux.test;

/**
 * Example of using Update with Lambda callbacks directly in execute()
 */
public class UpdateResultExample {

    public static void main(String[] args) {

        // Note: This is a documentation example. In actual usage, you would have a real database connection.
        // See the README.md for complete setup instructions.

        /*
        // Example 1: Simple update with onSuccess callback
        new Update<>(YourEntity.class)
                .set("health", 50)
                .where("uuid", "123e4567-e89b-12d3-a456-426614174000")
                .execute(result -> {
                    System.out.println("✓ Update successful!");
                    System.out.println("  Rows affected: " + result.getRowsAffected());
                    System.out.println("  Query: " + result.getQuery());
                });

        // Example 2: Update with both onSuccess and onFailure callbacks
        new Update<>(YourEntity.class)
                .set("health", 75)
                .set("name", "Updated User")
                .where("uuid", "123e4567-e89b-12d3-a456-426614174000")
                .execute(
                    result -> System.out.println("✓ Success: " + result.getRowsAffected() + " rows affected"),
                    error -> System.out.println("✗ Error: " + error.getMessage())
                );

        // Example 3: Get UpdateResult and use it
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

