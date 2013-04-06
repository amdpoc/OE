package ilink.dao;

import ilink.domain.*;
import ilink.utils.iLinkUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class IbatisCustDetailsDao extends SqlMapClientDaoSupport implements CustDetailsDao {

    public CustDataType getCustDetails(long custId) {
        return  (CustDataType) getSqlMapClientTemplate().queryForObject("getCustDetails", custId);
    }

    public CustBillingInfoType getCustBillingDetails(long custId) {
        return  (CustBillingInfoType) getSqlMapClientTemplate().queryForObject("getCustBillingDetails", custId);
    }

    public List<CustListItem> checkExistingCust(String cop, String npa, String lineNo, String customerId) {
        Map<String, Object> entityModel = new HashMap<String, Object>();
        entityModel.put("cop", cop);
        entityModel.put("lineNo", lineNo);
        entityModel.put("npa", npa);
        entityModel.put("customerId", customerId);
        List<CustListItem> resultList = null;
        resultList = (List<CustListItem>) getSqlMapClientTemplate().queryForList("isCustomerExist", entityModel);
        
        return resultList;
    }

    public boolean isCustomerAssignedToRepCheck(String repId, String custId) {
        List<CustAssignDataType> resultList = isCustomerAssignedToRep(repId, custId);
        return (resultList != null && !resultList.isEmpty());
    }
    public List<CustAssignDataType> isCustomerAssignedToRep(String repId, String custId) {
        Map<String, Object> entityModel = new HashMap<String, Object>();
        entityModel.put("repId", repId);
        entityModel.put("custId", custId);
        return (List<CustAssignDataType>) getSqlMapClientTemplate().queryForList("isCustomerAssignedToRep", entityModel);
    }


    public String getCustNameById(String custId) {
        String result = (String) getSqlMapClientTemplate().queryForObject("getCustNameById", custId);
        if (iLinkUtils.isEmpty(result))
            result = "";
        return result;
    }

    public ContactDataType getCustContactInfo(String custId) {
        return (ContactDataType) getSqlMapClientTemplate().queryForObject("getCustContactInfo", custId);
    }

    public String getCustTitleById(String custId){
        String result = (String) getSqlMapClientTemplate().queryForObject("getCustTitleById", custId);
        if (iLinkUtils.isEmpty(result))
            result = "";
        return result;
    }
}
