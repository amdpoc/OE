package ilink.dao;

import ilink.domain.CustListItem;

import java.util.List;


public interface CustListDao {

    public String getNormalizedName(String name);
    public List<CustListItem> getCustListByName(String custName);
    public List<CustListItem> getCustListByTN(String custNpa, String custCop, String custlineNo);
    public List<CustListItem> getCustListByCustId(String custId);

}
