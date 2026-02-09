package de.haizon.flux.test.stores;

import de.haizon.flux.annotations.Entity;
import de.haizon.flux.annotations.Index;

@Entity(tableName = "test")
public class TestStore {

    @Index(id = true)
    public String uuid;

    @Index
    public int health;

}
