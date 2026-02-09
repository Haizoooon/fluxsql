package de.haizon.flux.stores;

import de.haizon.flux.Flux;
import de.haizon.flux.annotations.Entity;
import de.haizon.flux.annotations.Index;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Store {

    private final Class<?> clazz;
    private final Entity entity;
    private final List<String> indexList;

    public Store(Class<?> clazz){
        this.clazz = clazz;
        this.indexList = new ArrayList<>();

        this.entity = clazz.getAnnotation(Entity.class);

        for (Field field : clazz.getFields()) {
            if(field.getAnnotation(Index.class) != null){
                this.indexList.add(field.getName());
            }
        }
    }

    public boolean exists(String type, String name){

        return true;
    }

    public void insert(Object... objects){

        StringBuilder builder = new StringBuilder("INSERT INTO " + entity.tableName() + " (");

        if(objects.length == indexList.size()){

            builder.append(String.join(", ", indexList));

            builder.append(") VALUES (");

            List<String> inputs = new ArrayList<>();

            for (Object object : objects) {
                inputs.add("'" + object.toString() + "'");
            }

            builder.append(String.join(", ", inputs));
            builder.append(")");

            Flux.getWrapper().executeUpdate(builder.toString());

            System.out.println(builder);

        } else {
            System.out.println("DEBUG = " + Arrays.toString(objects));
            System.out.println("DEBUG = " + indexList);
            System.out.println("Objects and indexes are not the same size (" + objects.length + " / " + indexList.size() + ")");
        }

    }

    public Object find(Object... objects){
        // in database is (uuid, health, etc..) find by uuid and get health or something

        try {
            StringBuilder builder = new StringBuilder("SELECT * FROM " + entity.tableName() + " WHERE ");

            List<String> conditions = new ArrayList<>();

            for (int i = 0; i < objects.length; i++) {
                conditions.add(indexList.get(i) + "='" + objects[i].toString() + "'");
            }

            builder.append(String.join(" AND ", conditions));
            builder.append(" LIMIT 1");

            System.out.println(builder.toString());

            // Execute query and return result
            // For simplicity, returning empty string here
            return Flux.getWrapper().executeQuery(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
