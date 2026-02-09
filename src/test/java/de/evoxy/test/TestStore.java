package de.evoxy.test;

import de.haizon.flux.annotations.Entity;
import de.haizon.flux.annotations.Index;

@Entity(tableName = "testtable")
public class TestStore {

    @Index
    public String name;

    @Index
    public int money;

}
