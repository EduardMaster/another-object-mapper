package org.eduard.another.object_mapper.impl;

import org.eduard.another.object_mapper.StorageAPI;
import org.eduard.another.object_mapper.api.StorageBase;
import org.eduard.another.object_mapper.api.StorageInfo;
import org.eduard.another.object_mapper.references.ReferenceList;
import org.eduard.another.object_mapper.references.ReferenceSet;

import java.util.*;

final public class StorageSet extends StorageBase<Set<?>, Object> {
    public StorageSet() {
    }

    @Override
    public Set<?> restore(StorageInfo info, Object data) {
        StorageInfo setInfo = info.clone();
        setInfo.setType(info.getListType());
        setInfo.updateByType();
        setInfo.updateByStorable();
        setInfo.updateByField();
        debug(">> SET TYPE: " + setInfo.getAlias());

        if (setInfo.isReference()) {
            debug(">> BY REFERENCE SET");
            if (data instanceof Set) {
                Set<?> oldSet = (Set<?>) data;
                Set<Object> newSet = new HashSet<>();
                Set<Object> realSet = new HashSet<>();
                debug(">> SET SIZE " + oldSet.size());
                for (Object item : oldSet) {
                    Object key = StorageAPI.STORE_OBJECT.restore(setInfo, item);
                    debug(">> ITEM KEY " + key);
                    Object object = StorageAPI.getObjectByKey(setInfo.getType(), key);
                    if (object != null) {
                        debug(">> ITEM REFERENCE EXISTS");
                        realSet.add(object);
                    } else {
                        debug(">> ITEM REFERENCE NOT LOEADED");
                        newSet.add(key);
                    }
                }
                StorageAPI.newReference(new ReferenceSet(setInfo, newSet, realSet));
                return realSet;
            }
            return null;

        }
        Set<Object> newList = new HashSet<>();
        if (data instanceof Set) {
            Set<?> setOld = (Set<?>) data;
            debug(">> SET BY SET");
            for (Object item : setOld) {
                newList.add(StorageAPI.STORE_OBJECT.restore(setInfo, item));
            }
        } else if (data instanceof List) {
            List<?> listOld = (List<?>) data;
            debug(">> SET BY LIST");
            for (Object item : listOld) {
                newList.add(StorageAPI.STORE_OBJECT.restore(setInfo, item));
            }
        } else if (data instanceof Map) {
            Map<?, ?> mapOld = (Map<?, ?>) data;
            debug(">> SET BY MAP");
            for (Object item : mapOld.values()) {
                Object object = StorageAPI.STORE_OBJECT.restore(setInfo, item);
                newList.add(object);
            }

        }
        return newList;

    }

    public Object store(StorageInfo info, Set<?> setToSave) {
        StorageInfo listInfo = info.clone();
        listInfo.setType(info.getListType());
        if (listInfo.getType() == null) {
            listInfo.setType(String.class);
        }
        listInfo.updateByType();
        listInfo.updateByStorable();
        listInfo.updateByField();
        Set<Object> newSet = new HashSet<>();
        debug("<< SET TYPE: " + listInfo.getAlias());
        for (Object item : setToSave) {
            try {
                debug("<< AS ITEM FROM SET");
                Object dado = StorageAPI.STORE_OBJECT.store(listInfo, item);
                newSet.add(dado);
            } catch (Exception ex) {
                ex.printStackTrace();
                debug("<< SAVE ITEM FROM SET FAIL");
            }
        }
        return newSet;

    }

}
