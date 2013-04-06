package ilink.utils;

import ilink.domain.StateDataType;

import javax.servlet.http.HttpSession;
import java.util.*;


public class StateManager {

    private Vector stateKeysArray;

    public Map<String, StateDataType> getStateMapHolder(HttpSession session) {
        Map<String, StateDataType> stateMapHolder = (Map<String, StateDataType>) session.getAttribute("STATE_MAP_HOLDER");
        if (stateMapHolder == null) {
            stateMapHolder = new HashMap<String, StateDataType>();
            session.setAttribute("STATE_MAP_HOLDER", stateMapHolder);
        }
        return stateMapHolder;
    }

    public Vector getStateKeysArray() {
        if (stateKeysArray == null)
            stateKeysArray = (Vector) iLinkPropertiesLoader.getListApplicationProperty("stateKeys");
        return stateKeysArray;
    }

    public StateDataType getState(HttpSession session, String pageId) {

        Map<String, StateDataType> stateMapHolder = getStateMapHolder(session);
        StateDataType state = stateMapHolder.get(pageId);
        if (state == null) {
            state = new StateDataType();
            state.setStateItem("pageId", pageId);
            stateMapHolder.put(pageId, state);
        }
        return state;
    }

    public void setState(HttpSession session, String inputStr) {

        if (!iLinkUtils.isEmpty(inputStr)) {
            String stateKey = getStateKey(inputStr);
            StateDataType stateHolder = getState(session, stateKey);
            parseInput(stateHolder, inputStr);
        }
    }

    public String getStateKey(String inputStr) {
        String stateKey = "";
        if (!iLinkUtils.isEmpty(inputStr)) {
            StringTokenizer inputStrArray = new StringTokenizer(inputStr, iLinkUtils.STATE_DELIMETER);
            stateKey = inputStrArray.nextToken();
        }
        return stateKey;
    }

    public void parseInput(StateDataType stateData, String inputStr) {
        if (!iLinkUtils.isEmpty(inputStr)) {
            StringTokenizer inputStrArray = new StringTokenizer(inputStr, iLinkUtils.STATE_DELIMETER);
            Iterator it = getStateKeysArray().iterator();
            while (it.hasNext()) {
                String stateKey = (String) it.next();
                String stateItem = inputStrArray.nextToken();
                if (iLinkUtils.isEmpty(stateItem))
                    stateItem = "";
                stateData.setStateItem(stateKey, stateItem);
            }
        }
    }

}
