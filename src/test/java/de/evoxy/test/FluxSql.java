package de.evoxy.test;

import de.haizon.flux.sql.FluxSqlWrapper;

public class FluxSql {

    public static void main(String[] args) {
        FluxSqlWrapper wrapper = new FluxSqlWrapper().configuration().connect();
    }

}
