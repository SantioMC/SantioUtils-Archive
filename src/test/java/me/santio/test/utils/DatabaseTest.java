package me.santio.test.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.santio.utils.SantioUtils;
import me.santio.utils.database.Database;
import org.bson.Document;
import org.junit.Test;

import java.util.*;

public class DatabaseTest {
    
    private static final UUID uuid = UUID.fromString("a5c16faa-ab09-481e-bb84-9e2ee3a1777e");
    
    @Test
    public void uuidInJson() {
        UUIDHolder uuid = new UUIDHolder(DatabaseTest.uuid, Collections.emptyList());
        
        String json = SantioUtils.Companion.getGSON().toJson(uuid);
        UUIDHolder fetched = SantioUtils.Companion.getGSON().fromJson(json, UUIDHolder.class);
        
        assert Objects.equals(uuid, fetched);
    }
    
    @Test
    public void testSaving() {
        Database.memory().table("test").set("test", new UUIDHolder(uuid, Collections.emptyList()));
        assert true;
    }
    
    @Test
    public void testLoading() {
        UUIDHolder uuid = new UUIDHolder(DatabaseTest.uuid, Collections.emptyList());
        Database.memory().table("test").set("test", uuid);
        UUIDHolder fetched = Database.memory().table("test").get("test", UUIDHolder.class);
        System.out.println(fetched);
        assert Objects.equals(uuid, fetched);
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UUIDHolder {
        UUID uuid;
        List<UUID> list = new ArrayList<>();
    }
    
}
