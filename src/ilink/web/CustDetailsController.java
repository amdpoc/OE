package ilink.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ilink.domain.StateDataType;
import ilink.utils.iLinkUtils;
import ilink.utils.StateManager;
import ilink.utils.NavigationHistoryHandler;
import ilink.utils.TitleManager;
import ilink.service.CustManagerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;


public class CustDetailsController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
    private CustManagerImpl custManager;
    private StateManager stateMngr;
    private static String PAGE_KEY = iLinkUtils.CUST_DETAILS;

    public void setCustManager(CustManagerImpl custManager) {
        this.custManager = custManager;
    }

    public void setStateMngr(StateManager stateMngr) {
        this.stateMngr = stateMngr;
    }

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
		logger.debug((new java.util.Date()).toString() + "############ Customer Detail Page - start");
        HttpSession session = request.getSession();
        //get state from previous page and set it in StateHolder
        String state = ServletRequestUtils.getStringParameter(request, "state");
        if (!iLinkUtils.isEmpty(state))
            stateMngr.setState(session, state);

        //get state for current Page from StateHolder
        StateDataType curPageState = stateMngr.getState(session, PAGE_KEY);

        String employeeId = iLinkUtils.getEmployeeIdFromSession(session);

        long custId = ServletRequestUtils.getLongParameter(request, "custId");
        Map<String, Object> entityModel = this.custManager.getCustDetailsScreenData(curPageState, custId, employeeId, session);
        TitleManager titleMngr = iLinkUtils.getTitleManager(request.getSession());
        entityModel.put("title", titleMngr.getTitleByKey(TitleManager.CUST_KEY, String.valueOf(custId)));

        NavigationHistoryHandler nHandler = iLinkUtils.getNavigationHistoryHandler(request.getSession());
        String prevPage = nHandler.setNavigationState(request);

        entityModel.put("prevPage", prevPage);
        entityModel.put("readOnly", iLinkUtils.isEmployeeProfileReadOnly(session));
        entityModel.put("generalInfo", iLinkUtils.getGeneralInfoFromSession(session));
        
        return new ModelAndView("customerDetails", "custDetailsModel", entityModel);
    }
}