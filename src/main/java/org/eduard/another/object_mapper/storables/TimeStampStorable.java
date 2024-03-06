package org.eduard.another.object_mapper.storables;

import com.google.gson.*;
import org.eduard.another.object_mapper.util.Extra;
import org.eduard.another.object_mapper.Storable;
import org.eduard.another.object_mapper.annotations.StorageAttributes;

import java.lang.reflect.Type;
import java.sql.Timestamp;

@StorageAttributes(inline = true)
public class TimeStampStorable implements Storable<Timestamp>, JsonDeserializer<Timestamp>, JsonSerializer<Timestamp> {

    public String store(Timestamp timestamp) {
        return "" + timestamp.getTime();
    }

    public Timestamp restore(Object string) {
        return new Timestamp(Extra.toLong(string));
    }

    @Override
    public Timestamp deserialize(JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new Timestamp(jsonElement.getAsLong());
    }

    @Override
    public JsonElement serialize(Timestamp timestamp, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(timestamp.getTime());
    }

}
