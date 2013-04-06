package ilink.service;

import ilink.domain.CustBillingInfoType;
import ilink.domain.CustDataType;
import ilink.domain.StateDataType;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Map;

public interface CustManager extends Serializable {

	public String getCustNameById(String custId)throws Exception;

    public CustDataType getCustDetails(long custId);
    public CustBillingInfoType getCustBillingDetails(long custId);

    public Map<String, Object> getCustDetailsDWR(long custId);

    public Map<String, Object> getCustDetailsScreenData(StateDataType pageState, long inCustId, String employeeId,HttpSession session);
}
