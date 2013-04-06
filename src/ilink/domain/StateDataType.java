package ilink.domain;

import ilink.utils.iLinkUtils;

import java.io.Serializable;
import java.util.*;


public class StateDataType implements Serializable {

    private HashMap stateHolder = new ExtendedMap();

    public String getStateItemByKey(String key) {
        return (String) stateHolder.get(key);
    }
    public void setStateItem(String key, String stateItem) {
        stateHolder.put(key, stateItem);       
    }

    @Override
    public String toString() {
        return stateHolder.toString();
    }
}
