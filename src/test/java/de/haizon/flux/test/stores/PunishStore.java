package de.haizon.flux.test.stores;

import de.haizon.flux.annotations.Entity;
import de.haizon.flux.annotations.Index;

import java.util.UUID;

@Entity(tableName = "punishments")
public class PunishStore {

    @Index(id = true)
    public String playerUniqueId;

    @Index
    public String reason;

    @Index
    public long expiresAt;

}
