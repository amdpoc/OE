package ilink.dao;

import ilink.domain.ProductDataType;
import ilink.domain.CommunityListItem;
import ilink.domain.AddressOmitIndListItem;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IbatisReferenceLoaderDao extends SqlMapClientDaoSupport implements ReferenceLoaderDao {

    public List<CommunityListItem> getCommunityList(String communityName) {
        return (List<CommunityListItem>) getSqlMapClientTemplate().queryForList("getCommunityList", communityName);
    }

    public List<AddressOmitIndListItem> getAddressOmitInd() {
        return (List<AddressOmitIndListItem>) getSqlMapClientTemplate().queryForList("getOmitAddrInd");
    }

    public List<ProductDataType> getProducts(String productName) {
        return (List<ProductDataType>) getSqlMapClientTemplate().queryForList("getProductsList", productName);
    }

    public String getLogicalDate() {
        return (String) getSqlMapClientTemplate().queryForObject("getLogicalDate");
    }
}
