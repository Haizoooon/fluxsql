package de.haizon.flux.stores;

import de.haizon.flux.Flux;
import de.haizon.flux.annotations.Entity;
import de.haizon.flux.annotations.Index;
import de.haizon.flux.types.DatabaseTypes;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DataStore {

    private final Map<Class<?>, DatabaseTypes> databaseTypesMap;
    private final List<Class<?>> stores;

    public DataStore(){
        this.databaseTypesMap = new ConcurrentHashMap<>();
        this.stores = new ArrayList<>();
    }

    public DataStore setDefaultTypes(){
        databaseTypesMap.put(String.class, DatabaseTypes.VARCHAR);
        databaseTypesMap.put(UUID.class, DatabaseTypes.VARCHAR);
        databaseTypesMap.put(int.class, DatabaseTypes.INT);
        databaseTypesMap.put(long.class, DatabaseTypes.BIGINT);
        return this;
    }

    public DataStore setDatabaseType(Object object, DatabaseTypes databaseTypes){
        databaseTypesMap.put(object.getClass(), databaseTypes);
        return this;
    }

    public DataStore ensureIndex(Class<?> clazz){

        Entity entity = clazz.getAnnotation(Entity.class);

        Map<String, DatabaseTypes> fieldsMap = new ConcurrentHashMap<>();

        for (Field field : clazz.getFields()) {

            if(field.getAnnotation(Index.class) != null){
                System.out.println(field.getName() + field.getType());

                fieldsMap.put(field.getName(), getDatabaseTypeByClass(field.getType()));
            }

        }

        StringBuilder builder = new StringBuilder();

        StringJoiner sj = new StringJoiner(", ");

        fieldsMap.forEach((name, type) -> {
            StringBuilder fieldDef = new StringBuilder();
            fieldDef.append(name).append(" ").append(type.name());

            // Nur eine Länge hinzufügen, wenn sie sinnvoll ist (> 0)
            if (type.getLength() > 0) {
                fieldDef.append("(").append(type.getLength()).append(")");
            }

            sj.add(fieldDef.toString());
        });

        builder.append(sj.toString());

        String query = "CREATE TABLE IF NOT EXISTS " + entity.tableName() + " (" + builder + ")";

        System.out.println(query);

        Flux.getWrapper().execute(query);

        stores.add(clazz);

        return this;
    }

    private DatabaseTypes getDatabaseTypeByClass(Class<?> clazz){
        return databaseTypesMap.get(clazz);
    }

    public Store find(Class<?> clazz){
        if(stores.contains(clazz)) return new Store(clazz);
        return null;
    }

    public DataStore ensureIndexes(String packageName){

        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replace("[.]", "/"));

        if(inputStream != null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            for (Class<?> aClass : reader.lines().filter(line -> line.endsWith(".class")).map(line -> getClass(line, packageName)).toList()) {
                System.out.println(aClass);
                ensureIndex(aClass);
            }

        }

        return this;
    }

    public void save(Object object){
        // save all indexed fields of the object to the database
        try {
            Class<?> clazz = object.getClass();

            // Check if this class has been registered
            if (!stores.contains(clazz)) {
                System.out.println("Error: Class " + clazz.getName() + " has not been indexed. Call ensureIndex() first.");
                return;
            }

            // Get all indexed fields
            List<Object> fieldValues = new ArrayList<>();

            for (Field field : clazz.getFields()) {
                if (field.getAnnotation(Index.class) != null) {
                    fieldValues.add(field.get(object));
                }
            }

            // Get the Store for this class and insert the values
            Store store = find(clazz);
            if (store != null) {
                store.insert(fieldValues.toArray());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

}
