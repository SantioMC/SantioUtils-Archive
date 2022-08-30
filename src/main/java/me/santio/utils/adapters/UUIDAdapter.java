package me.santio.utils.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.UUID;

public class UUIDAdapter implements JsonSerializer<UUID>, JsonDeserializer<UUID> {
    
    @Override
    public UUID deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return UUID.fromString(json.getAsString());
    }
    
    @Override
    public JsonElement serialize(UUID src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
    
}
