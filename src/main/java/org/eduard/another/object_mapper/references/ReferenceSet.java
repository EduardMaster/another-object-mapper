package org.eduard.another.object_mapper.references;

import org.eduard.another.object_mapper.StorageAPI;
import org.eduard.another.object_mapper.api.StorageInfo;

import java.util.List;
import java.util.Set;

public class ReferenceSet extends ReferenceBase<Set<Object>> {
	private final Set<Object> realSet;

	public ReferenceSet(StorageInfo info, Set<Object> references, Set<Object> realSet) {
		super(info, null);
		setRestore(references);
		this.realSet = realSet;
	}

	@Override
	public void update() {
		for (Object key : getRestore()) {
			realSet.add(StorageAPI.getObjectByKey(getInfo().getType(), key));
		}
	}

}
