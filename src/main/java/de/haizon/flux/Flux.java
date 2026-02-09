package de.haizon.flux;

import de.haizon.flux.sql.FluxSqlWrapper;
import de.haizon.flux.stores.DataStore;

public class Flux {

    private static FluxSqlWrapper wrapper;

    public static DataStore createDatastore(FluxSqlWrapper wrapper){
        Flux.wrapper = wrapper;
        return new DataStore();
    }

    public static FluxSqlWrapper getWrapper() {
        return wrapper;
    }
}
