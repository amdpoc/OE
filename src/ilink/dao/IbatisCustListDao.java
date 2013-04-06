package ilink.dao;

import ilink.domain.CustListItem;
import ilink.utils.iLinkUtils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


public class IbatisCustListDao extends SqlMapClientDaoSupport implements CustListDao {

    public String getNormalizedName(String name){
        return (String) getSqlMapClientTemplate().queryForObject("getNormalizedName", name);
    }
    public List<CustListItem> getCustListByName(String custName) {
        List<CustListItem> list = null;
        try {
            custName = getNormalizedName(custName);
            Map<String, Object> entityModel = new HashMap<String, Object>();
            entityModel.put("custName", custName);
            entityModel.put("rowNum", iLinkUtils.CUST_LIST_ROW_NUM + 1);

             list = (List<CustListItem>) getSqlMapClientTemplate().queryForList("getCustListByName", entityModel);
        }
        catch (Exception ex) {
            System.out.println("IbatisCustListDao.getCustListByName : exception ->" + ex.getMessage());
        }
        return list;
    }

    public List<CustListItem> getCustListByCustId(String custId) {
        List<CustListItem> list = null;
        try {
            list = (List<CustListItem>) getSqlMapClientTemplate().queryForList("getCustListByCustId", custId);
        }
        catch (Exception ex) {
            System.out.println("IbatisCustListDao.getCustListByCustId : exception ->" + ex.getMessage());
        }
        return list;
    }

    public List<CustListItem> getCustListByTN(String custNpa, String custCop, String custlineNo) {

        Map<String, Object> entityModel = new HashMap<String, Object>();
        entityModel.put("npa", custNpa);
        entityModel.put("cop", custCop);
        entityModel.put("lineNo", custlineNo);

        List<CustListItem> list = null;
        try {
            list = (List<CustListItem>) getSqlMapClientTemplate().queryForList("getCustListByTN", entityModel);
        }
        catch (Exception ex) {
            System.out.println("IbatisCustListDao.getCustListByTN : exception ->" + ex.getMessage());
        }
        return list;
    }

}
