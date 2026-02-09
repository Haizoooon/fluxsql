# 🔧 FluxSql

A modern, fluent Java ORM framework for seamless database operations with type-safe queries and intuitive API design.

---

## ✨ Features

- **Fluent API**: Chain methods elegantly for queries and updates
- **Type-Safe Queries**: Generic `Query<T>` class ensures compile-time safety
- **Advanced Filtering**: Support for comparison operators, pattern matching, and range queries
- **Automatic Schema Management**: Create and manage tables automatically with annotations
- **Simple Data Persistence**: Easily save, query, and update objects in your database
- **Flexible Updates**: Chainable update statements with WHERE conditions
- **Multiple Database Support**: Built on JDBC for broad database compatibility

---

## 🚀 Quick Start

### Installation

Add FluxSql to your project and include the necessary JDBC driver for your database.

### Setup

```java
import de.haizon.flux.Flux;
import de.haizon.flux.sql.FluxSqlWrapper;
import de.haizon.flux.stores.DataStore;

// Initialize the database connection
FluxSqlWrapper wrapper = new FluxSqlWrapper()
    .configuration("localhost", "mydb", "user", "password", 3306)
    .connect();

DataStore dataStore = Flux.createDatastore(wrapper).setDefaultTypes();
```

### Define Your Entity

Use the `@Entity` and `@Index` annotations to define your data model:

```java
import de.haizon.flux.annotations.Entity;
import de.haizon.flux.annotations.Index;

@Entity(tableName = "users")
public class User {
    @Index
    public String uuid;
    
    @Index
    public String name;
    
    @Index
    public int health;
}
```

---

## 📖 Usage Examples

### Creating Tables

```java
dataStore.ensureIndex(User.class);  // Creates table if it doesn't exist
```

### Saving Data

```java
User user = new User();
user.uuid = UUID.randomUUID().toString();
user.name = "John Doe";
user.health = 100;

dataStore.save(user);  // Automatically inserts all indexed fields
```

### Querying Data

#### Basic Query

```java
Query<User> query = new Query<>(User.class)
    .where("uuid", "123e4567-e89b-12d3-a456-426614174000");

User user = query.first();  // Get first result
List<User> users = query.execute();  // Get all results
```

#### Advanced Filters

```java
// Greater Than
Query<User> healthyUsers = new Query<>(User.class)
    .whereGreaterThan("health", 50)
    .execute();

// Less Than
Query<User> lowHealthUsers = new Query<>(User.class)
    .whereLessThan("health", 20)
    .execute();

// Between Range
Query<User> mediumHealthUsers = new Query<>(User.class)
    .whereBetween("health", 30, 70)
    .execute();

// IN List
Query<User> specificUsers = new Query<>(User.class)
    .whereIn("health", Arrays.asList(10, 20, 30))
    .execute();

// NOT Equal
Query<User> notDeadUsers = new Query<>(User.class)
    .whereNotEqual("health", 0)
    .execute();

// LIKE Pattern
Query<User> johnsUsers = new Query<>(User.class)
    .whereLike("name", "%John%")
    .execute();
```

#### Combining Filters

```java
Query<User> complexQuery = new Query<>(User.class)
    .whereGreaterThan("health", 30)
    .whereLessThan("health", 70)
    .execute();  // health > 30 AND health < 70
```

### Updating Data

```java
Update<User> update = new Update<>(User.class)
    .set("health", 50)
    .where("uuid", "123e4567-e89b-12d3-a456-426614174000")
    .execute();

// Multiple field updates
Update<User> multiUpdate = new Update<>(User.class)
    .set("health", 75)
    .set("name", "Jane Doe")
    .where("uuid", "123e4567-e89b-12d3-a456-426614174000")
    .execute();
```

---

## 🏗️ Architecture

### Core Components

- **`Flux`**: Main entry point for initializing the framework
- **`FluxSqlWrapper`**: Database connection and query execution handler
- **`DataStore`**: Entity management and schema creation
- **`Store`**: Direct table operations (insert, find)
- **`Query<T>`**: Type-safe SELECT queries with fluent API
- **`Update<T>`**: Type-safe UPDATE statements with fluent API

### Annotations

- **`@Entity`**: Mark a class as a database entity
- **`@Index`**: Mark fields to be included in the database table

---

## 💡 Supported Operators

| Operator | Method | Example |
|----------|--------|---------|
| = | `where()` | `where("age", 25)` |
| > | `whereGreaterThan()` | `whereGreaterThan("age", 18)` |
| < | `whereLessThan()` | `whereLessThan("age", 65)` |
| >= | `whereGreaterThanOrEqual()` | `whereGreaterThanOrEqual("age", 18)` |
| <= | `whereLessThanOrEqual()` | `whereLessThanOrEqual("age", 65)` |
| != | `whereNotEqual()` | `whereNotEqual("status", "inactive")` |
| LIKE | `whereLike()` | `whereLike("name", "%John%")` |
| IN | `whereIn()` | `whereIn("id", Arrays.asList(1, 2, 3))` |
| BETWEEN | `whereBetween()` | `whereBetween("age", 18, 65)` |

---

## 🔧 Type Support

FluxSql automatically handles the following Java types:

- `String` → VARCHAR
- `int` → INT
- `long` → BIGINT
- `double` → DOUBLE
- `boolean` → BOOLEAN

---

## 📋 Example Project Structure

```
FluxSql/
├── src/
│   ├── main/java/de/haizon/flux/
│   │   ├── Flux.java
│   │   ├── annotations/
│   │   │   ├── Entity.java
│   │   │   └── Index.java
│   │   ├── query/
│   │   │   ├── Query.java
│   │   │   └── Update.java
│   │   ├── sql/
│   │   │   └── FluxSqlWrapper.java
│   │   ├── stores/
│   │   │   ├── DataStore.java
│   │   │   └── Store.java
│   │   └── types/
│   │       └── DatabaseTypes.java
│   └── test/java/de/haizon/flux/test/
│       └── TestFlux.java
└── README.md
```

---

## 🛠️ Development

### Building

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

---

## 📝 License

This project is open source and available under the MIT License.

---

## 🤝 Contributing

Contributions are welcome! Feel free to submit pull requests or open issues for bugs and feature requests.

---

## 📞 Support

For questions or issues, please open an issue on the [GitHub repository](https://github.com/Haizoooon/fluxsql).

---

**Built with ❤️ by the FluxSql Team**
