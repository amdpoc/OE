package ilink.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ilink.domain.CustListItem;
import ilink.utils.iLinkUtils;
import ilink.utils.StateManager;
import ilink.utils.PageContext;
import ilink.utils.NavigationHistoryHandler;
import ilink.service.EsignEnvelopeManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


public class CustListController implements Controller {

    protected final Log logger = LogFactory.getLog(getClass());
    private StateManager stateMngr;
    private EsignEnvelopeManager esignEnvelopeManager;

    public void setEsignEnvelopeManager(EsignEnvelopeManager esignEnvelopeManager) {
        this.esignEnvelopeManager = esignEnvelopeManager;
    }

    public void setStateMngr(StateManager stateMngr) {
        this.stateMngr = stateMngr;
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.debug((new java.util.Date()).toString() + "########## CustListController:handleRequest - start");

        HttpSession session = request.getSession();

        //get state from previous page and set it in StateHolder
        String state = ServletRequestUtils.getStringParameter(request, "state");
        if (!iLinkUtils.isEmpty(state))
            stateMngr.setState(request.getSession(), state);

        PageContext pageCtx = (PageContext) session.getAttribute(iLinkUtils.CUST_LIST_PAGE_CTX);
        if (pageCtx == null) {
            pageCtx = new PageContext(iLinkUtils.CUST_LIST_PAGE_SIZE, iLinkUtils.CUST_LIST_ROW_NUM);
            session.setAttribute(iLinkUtils.CUST_LIST_PAGE_CTX, pageCtx);
        }
        List<CustListItem> custList = null;
        Map<String, Object> myCustListModel = new HashMap<String, Object>();

        try {
            custList = pageCtx.getCurrentPage();
        } catch (Exception e) {
            logger.error(" Error in returning Customer List Page " + e.getMessage());
            if (custList == null)
                custList = new ArrayList<CustListItem>();
        }
        myCustListModel.put("resultList", custList);
        myCustListModel.put("totalRecordsNum", pageCtx.getTotalRecordsNum());
        myCustListModel.put("pageSize", pageCtx.getPageSize());
        myCustListModel.put("currentPageNum", pageCtx.getCurrentPageNum());
        myCustListModel.put("totalPagesNum", pageCtx.getTotalPagesNum());
        myCustListModel.put("currentIndex", pageCtx.getCurrentIndex());
        myCustListModel.put("totalPagesNum", pageCtx.getTotlaPagesNum());

        myCustListModel.put("searchCriteria", pageCtx.getLastCriteriaStr());

        NavigationHistoryHandler nHandler = iLinkUtils.getNavigationHistoryHandler(request.getSession());
        String prevPage = nHandler.setNavigationState(request);
        myCustListModel.put("prevPage", prevPage);
        myCustListModel.put("docusignResponse", esignEnvelopeManager.getLastDocusignInfo(request));
        myCustListModel.put("generalInfo", iLinkUtils.getGeneralInfoFromSession(session));
        logger.debug((new java.util.Date()).toString() + "########## CustListController:handleRequest - finish");

        return new ModelAndView("customerList", "custListModel", myCustListModel);

    }

}