package ilink.dao;

import ilink.domain.ProductDataType;
import ilink.domain.CommunityListItem;
import ilink.domain.AddressOmitIndListItem;

import java.util.List;


public interface ReferenceLoaderDao {
  public String getLogicalDate();
  public List<CommunityListItem> getCommunityList(String communityName);
  public List<ProductDataType> getProducts(String productName);
  public List<AddressOmitIndListItem> getAddressOmitInd();
}
