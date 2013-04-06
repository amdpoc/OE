package ilink.utils;

import ilink.domain.ExtendedMap;
import ilink.dao.CustDetailsDao;


import java.util.HashMap;

public class TitleManager {

    public static final String CUST_KEY = "cust";
    public static final String OPPT_KEY = "oppt";

    // hash contained all data for corresponded title
    private HashMap titleHolder = new ExtendedMap();
    private CustDetailsDao custDetailsDao;


    public void setCustDetailsDao(CustDetailsDao custDetailsDao) {
        this.custDetailsDao = custDetailsDao;
    }


    public String getTitleByKey(String key, String id) {
        String keyId = key + id;
        String title = (String) titleHolder.get(keyId);
        if (iLinkUtils.isEmpty(title)) {
            if (key.equals(CUST_KEY)) {
                title = custDetailsDao.getCustTitleById(id);
            } else if (key.equals(OPPT_KEY)) {
                title = "";
            }
            titleHolder.put(keyId, title);
        }
        return title;
    }

}
