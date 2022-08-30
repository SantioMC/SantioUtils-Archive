package me.santio.utils.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class EnumAdapter<T extends Enum<T>> implements JsonDeserializer<T> {
    
    Class<T> clazz;
    public EnumAdapter(Class<T> enumClass) {
        this.clazz = enumClass;
    }
    
    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return Enum.valueOf(clazz, jsonElement.getAsJsonPrimitive().getAsString().toUpperCase());
    }
    
}
