package ilink.web;

import ilink.service.CustNotesManager;
import ilink.utils.StateManager;
import ilink.utils.iLinkUtils;
import ilink.utils.NavigationHistoryHandler;
import ilink.utils.TitleManager;
import ilink.domain.CustNotesTxListItem;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class NewCustNoteController implements Controller {

    CustNotesManager custNotesManager;
    StateManager stateMngr;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //get state from previous page and set it in StateHolder
        String state = ServletRequestUtils.getStringParameter(request, "state");
        if (!iLinkUtils.isEmpty(state))
            stateMngr.setState(request.getSession(), state);

		long custId = ServletRequestUtils.getLongParameter(request, "custId");

        List<CustNotesTxListItem> resultList = custNotesManager.getCustNotesTxByCustId(custId);
        if(resultList == null){
            resultList = new ArrayList<CustNotesTxListItem>();
        }
        
		Map<String, Object> entityModel = new HashMap<String, Object>();
        entityModel.put("custId", custId);
        TitleManager titleMngr = iLinkUtils.getTitleManager(request.getSession());
        entityModel.put("title", titleMngr.getTitleByKey(TitleManager.CUST_KEY, String.valueOf(custId)));
        entityModel.put("custNotesList",resultList);

        NavigationHistoryHandler nHandler = iLinkUtils.getNavigationHistoryHandler(request.getSession());
        String prevPage = nHandler.setNavigationState(request);
        entityModel.put("prevPage", prevPage);

		return new ModelAndView( "newCustNote", "entityDetails", entityModel );
	}

    public void setCustNotesManager(CustNotesManager custNotesManager) {
        this.custNotesManager = custNotesManager;
    }

    public void setStateMngr(StateManager stateMngr) {
        this.stateMngr = stateMngr;
    }
}

