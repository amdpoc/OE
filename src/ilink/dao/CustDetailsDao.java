package ilink.dao;

import ilink.domain.ContactDataType;
import ilink.domain.CustAssignDataType;
import ilink.domain.CustBillingInfoType;
import ilink.domain.CustDataType;

import java.util.List;


public interface CustDetailsDao {
    
    public CustDataType getCustDetails(long custId);
    public List<CustAssignDataType> isCustomerAssignedToRep(String repId, String custId);
    public boolean isCustomerAssignedToRepCheck(String repId, String custId);
    public String getCustNameById(String custId);
    public String getCustTitleById(String custId);
    public ContactDataType getCustContactInfo(String custId);
    public CustBillingInfoType getCustBillingDetails(long custId);
}
