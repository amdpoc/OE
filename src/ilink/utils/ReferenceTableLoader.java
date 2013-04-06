package ilink.utils;

import ilink.domain.ProductDataType;
import ilink.domain.CommunityListItem;
import ilink.domain.AddressOmitIndListItem;
import ilink.dao.ReferenceLoaderDao;

import java.util.List;

public class ReferenceTableLoader {

    private static ReferenceLoaderDao refDao;
    private static String logicalDate;
    private static List<AddressOmitIndListItem> addressOmitIndList;

    public void setRefDao(ReferenceLoaderDao refDao) {
        ReferenceTableLoader.refDao = refDao;
    }

    public void init() {
        if (logicalDate == null)
            logicalDate = refDao.getLogicalDate();

        if (addressOmitIndList == null)
            addressOmitIndList = refDao.getAddressOmitInd();
    }

    public static String getLogicalDate() {
        return logicalDate;
    }

    public static List<AddressOmitIndListItem> getAddressOmitInd() {
        return addressOmitIndList;
    }

    public static synchronized List<ProductDataType> getProducts(String productName) {
        return refDao.getProducts(productName);
    }

    public List<CommunityListItem> getCommunityList(String communityName){
        return refDao.getCommunityList(communityName);
    }
} //end of class definition
