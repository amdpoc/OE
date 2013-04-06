package ilink.service;

import ilink.domain.CustListItem;
import ilink.domain.ExtendedMap;
import ilink.utils.iLinkUtils;
import ilink.utils.PageContext;
import ilink.utils.MessageLoader;
import ilink.dao.CustListDao;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CustListManagerImpl implements CustListManager {

    private CustListDao custListDao;
    protected final Log logger = LogFactory.getLog(getClass());

    public CustListDao getCustListDao() {
        return custListDao;
    }

    public void setCustListDao(CustListDao custListDao) {
        this.custListDao = custListDao;
    }

    public String getCustListPageDWR(String pageInd) {

        logger.debug("################ Start getCustListPageDWR ----> " + (new java.util.Date()).toString());
        String result = "";
        try {
            HttpSession session = iLinkUtils.getSessionFromDWR();
            PageContext pageCtx = (PageContext) session.getAttribute(iLinkUtils.CUST_LIST_PAGE_CTX);

            if (pageCtx == null) {
                pageCtx = new PageContext(iLinkUtils.CUST_LIST_PAGE_SIZE, iLinkUtils.CUST_LIST_ROW_NUM);
                session.setAttribute(iLinkUtils.CUST_LIST_PAGE_CTX, pageCtx);
            }
            if (!iLinkUtils.isEmpty(pageInd)) {
                if (pageInd.equals(iLinkUtils.NEXT_PAGE)) {
                    result = pageCtx.getNextPageStr();
                } else if (pageInd.equals(iLinkUtils.PREV_PAGE)) {
                    result = pageCtx.getPreviousPageStr();
                }
            } else {
                result = pageCtx.getCurrentPageStr();
            }
        } catch (Exception ie) {
            logger.error("################ Error getCustListPageDWR ----> " + ie.getMessage());
        }
        logger.debug("################ Finish getCustListPageDWR ----> " + (new java.util.Date()).toString());
        return result;
    }

    public String getCustListDWR(String custName, String customerId, String custNPA, String custCOP, String custLineNo) {

        logger.debug("################ Start getCustListDWR ----> " + (new java.util.Date()).toString());
        String result = "";
        String criteriaLogStr = " custName: " + custName + " customerId:" + customerId +
                                " custNPA:" + custNPA + " custCOP:" + custCOP + " custLineNo:" + custLineNo;
        try {
            HttpSession session = iLinkUtils.getSessionFromDWR();
            PageContext pageCtx = (PageContext) session.getAttribute(iLinkUtils.CUST_LIST_PAGE_CTX);

            if (pageCtx == null) {
                pageCtx = new PageContext(iLinkUtils.CUST_LIST_PAGE_SIZE, iLinkUtils.CUST_LIST_ROW_NUM);
                session.setAttribute(iLinkUtils.CUST_LIST_PAGE_CTX, pageCtx);
            }
            HashMap criteria = new ExtendedMap();
            criteria.put("custName", custName);
            criteria.put("custId", customerId);
            criteria.put("custNPA", custNPA);
            criteria.put("custCOP", custCOP);
            criteria.put("custLineNo", custLineNo);
           // if (pageCtx.isCriteriaChanged(criteria)) {    removed due to problem with search after new customer was created- since cache hold previous results 
                pageCtx.init(criteria);
                List<CustListItem> resultList = getCustList(custName, customerId, custNPA, custCOP, custLineNo);
                int resultNumber = resultList == null? 0: resultList.size();
                logger.info("################ getCustListDWR  with criteria->"  + criteriaLogStr + " -> resultNumber: " + resultNumber);
                pageCtx.setResultList(resultList);
            //}
            result = pageCtx.getCurrentPageStr();
        } catch (Exception ie) {
            logger.error("################ getCustListDWR with criteria -> " + criteriaLogStr + " with error ----> " + ie.getMessage());
        }
        logger.debug("################ Finish getCustListDWR ----> " + (new java.util.Date()).toString());
        return result;
    }

    protected List<CustListItem> getCustList(String custName, String custId, String custNPA, String custCOP, String custLineNo) {
        List<CustListItem> resultList = null;
        if (!iLinkUtils.isEmpty(custId) && !custId.trim().equals("0") && !custId.trim().equals("null")) {
            resultList = custListDao.getCustListByCustId(custId);
        } else if (!iLinkUtils.isEmpty(custNPA) && !iLinkUtils.isEmpty(custCOP) && !iLinkUtils.isEmpty(custLineNo)) {
            resultList = custListDao.getCustListByTN(custNPA, custCOP, custLineNo);
        } else if (!iLinkUtils.isEmpty(custName)) {
            resultList = custListDao.getCustListByName(custName);
        }
        return resultList;
    }

    public String getCustListByNameToStr(String custName, String pageInd, PageContext pageCtx) {

        logger.debug("################ Start getCustListByNameToStr ----> " + (new java.util.Date()).toString());
        String result = null;
        try {
            if (pageCtx.isCriteriaChanged(custName)) {
                pageCtx.init(custName);
                List<CustListItem> resultList = custListDao.getCustListByName(custName);
                pageCtx.setResultList(resultList);
            }
            if (iLinkUtils.isEmpty(pageInd)) {
                pageInd = iLinkUtils.CURRENT_PAGE;
            }
            if (pageInd.equals(iLinkUtils.NEXT_PAGE)) {
                result = pageCtx.getNextPageStr();
            } else if (pageInd.equals(iLinkUtils.PREV_PAGE)) {
                result = pageCtx.getPreviousPageStr();
            } else {
                result = pageCtx.getCurrentPageStr();
            }
        } catch (Exception ie) {
            logger.error("################ Error getCustListByNameToStr ----> " + ie.getMessage());
            result = MessageLoader.getMessage("ERROR_LOADING_CUSTOMERS", null);
        }
        logger.debug("################ Finish getCustListByNameToStr ----> " + (new java.util.Date()).toString());
        return result;
    }

}
