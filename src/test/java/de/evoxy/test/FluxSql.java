package de.evoxy.test;

import de.haizon.flux.Flux;
import de.haizon.flux.sql.FluxSqlWrapper;
import de.haizon.flux.stores.DataStore;
import de.haizon.flux.update.Update;
import de.haizon.flux.update.UpdateResult;
import de.haizon.flux.update.UpdateResultType;

public class FluxSql {

    public static void main(String[] args) {
        FluxSqlWrapper wrapper = new FluxSqlWrapper().configuration("5.231.29.121", "fluxsql", "admin", "(MrGR{g§5jRk4HeT", 5307).connect();

        DataStore dataStore = Flux.createDatastore(wrapper);

        dataStore.ensureIndex(TestStore.class);

        TestStore testStore = new TestStore();
        testStore.name = "Test";
        testStore.money = 1000;

        dataStore.save(testStore);

        new Update(TestStore.class)
                .where("name", "Test")
                .set("money", 2000)
                .execute(updateResult -> {
                    if(updateResult.)
                });

    }

}
