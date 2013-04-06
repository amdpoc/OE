package ilink.service;

import ilink.domain.CustListItem;
import ilink.utils.PageContext;

import javax.servlet.http.HttpSession;
import java.util.List;


public interface CustListManager {

    public String getCustListPageDWR(String pageInd);
    public String getCustListDWR(String custName, String customerId, String custNPA, String custCOP, String custLineNo);
    public String getCustListByNameToStr(String custName, String pageInd, PageContext pageCtx);

}
