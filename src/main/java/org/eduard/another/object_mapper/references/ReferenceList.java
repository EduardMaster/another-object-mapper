package org.eduard.another.object_mapper.references;

import org.eduard.another.object_mapper.StorageAPI;
import org.eduard.another.object_mapper.api.StorageInfo;

import java.util.List;

public class ReferenceList extends ReferenceBase<List<Object>> {
	private final List<Object> realList;

	public ReferenceList(StorageInfo info, List<Object> references, List<Object> realList) {
		super(info, null);
		setRestore(references);
		this.realList = realList;
	}

	@Override
	public void update() {
		for (Object key : getRestore()) {
			realList.add(StorageAPI.getObjectByKey(getInfo().getType(), key));
		}
	}

}
