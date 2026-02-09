package de.haizon.flux.test;

import de.haizon.flux.Flux;
import de.haizon.flux.query.Query;
import de.haizon.flux.sql.FluxSqlWrapper;
import de.haizon.flux.stores.DataStore;
import de.haizon.flux.test.stores.TestStore;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestFlux {

    public static void main(String[] args) {

        FluxSqlWrapper wrapper = new FluxSqlWrapper().configuration("5.231.29.121", "fluxsql", "admin", "(MrGR{g§5jRk4HeT", 5307).connect();
        DataStore dataStore = Flux.createDatastore(wrapper).setDefaultTypes();

        dataStore.ensureIndex(TestStore.class);

        // Save example: Create and save a new TestStore object
        TestStore newStore = new TestStore();
        newStore.uuid = UUID.randomUUID().toString();
        newStore.health = 75;

        dataStore.save(newStore);

    }

}
