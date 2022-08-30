package me.santio.utils.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.UUID;

public class CleanedUUIDAdapter implements JsonSerializer<UUID>, JsonDeserializer<UUID> {
    
    @Override
    public UUID deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return UUID.fromString(json.getAsString().replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }
    
    @Override
    public JsonElement serialize(UUID src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString().replace("-", ""));
    }
    
}
