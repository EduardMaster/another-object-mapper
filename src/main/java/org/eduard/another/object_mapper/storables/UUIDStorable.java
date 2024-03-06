package org.eduard.another.object_mapper.storables;

import org.eduard.another.object_mapper.Storable;
import org.eduard.another.object_mapper.annotations.StorageAttributes;

import java.util.UUID;

@StorageAttributes(inline = true)
public class UUIDStorable implements Storable<UUID> {

	public UUID restore(String object) {
		try {
			return UUID.fromString(object);
		} catch (Exception e) {
			return null;
		}
	}

	public String store(UUID object) {
		return object.toString();
	}

}
