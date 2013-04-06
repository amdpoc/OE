package ilink.service;

import ilink.dao.CustDetailsDao;
import ilink.dao.CustNotesDao;
import ilink.dao.ProposalDao;
import ilink.domain.*;
import ilink.utils.TitleManager;
import ilink.utils.TrackingManager;
import ilink.utils.iLinkUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustManagerImpl implements CustManager {

    private CustDetailsDao custDetailsDao;
    private CustNotesDao custNotesDao;
    private ProposalDao proposalDao;
    private boolean testContactEmailInd;
    private String testContactEmail;

    protected final Log logger = LogFactory.getLog(getClass());

    public boolean isTestContactEmailInd() {
        return testContactEmailInd;
    }

    public void setTestContactEmailInd(boolean testContactEmailInd) {
        this.testContactEmailInd = testContactEmailInd;
    }

    public String getTestContactEmail() {
        return testContactEmail;
    }

    public void setTestContactEmail(String testContactEmail) {
        this.testContactEmail = testContactEmail;
    }

    public void setCustNotesDao(CustNotesDao custNotesDao) {
        this.custNotesDao = custNotesDao;
    }

    public void setCustDetailsDao(CustDetailsDao custDetailsDao) {
        this.custDetailsDao = custDetailsDao;
    }

    public String getCustNameById(String custId) throws Exception {
        return custDetailsDao.getCustNameById(custId);
    }

    public CustDataType getCustDetails(long custId) {
        return custDetailsDao.getCustDetails(custId);
    }

    public CustBillingInfoType getCustBillingDetails(long custId)
    {
        return custDetailsDao.getCustBillingDetails(custId);
    }

    public ProposalDao getProposalDao() {
        return proposalDao;
    }

    public void setProposalDao(ProposalDao proposalDao) {
        this.proposalDao = proposalDao;
    }

    public  Map<String, Object> getCustDetailsDWR(long custId) {
        Map<String, Object> entityModel = new HashMap<String, Object>();
        TrackingManager trackMngr = iLinkUtils.getTrackingMngrFromDWR();
        trackMngr.logMessage(TrackingManager.TransType.PREWS, "getCustDetailsDWR", TrackingManager.MsgType.TXT,
                             "customerId : " + String.valueOf(custId));

        CustDataType panelObj =  custDetailsDao.getCustDetails(custId);
        entityModel.put("panelId", iLinkUtils.PANEL_1);
        entityModel.put("panelObj", panelObj);
        entityModel.put("custId", custId);
        TitleManager titleMngr = iLinkUtils.getTitleManagerDWR();
        entityModel.put("title", titleMngr.getTitleByKey(TitleManager.CUST_KEY, String.valueOf(custId)));
        trackMngr.logMessage(TrackingManager.TransType.PSTWS, "getCustDetailsDWR", TrackingManager.MsgType.TXT, panelObj.toString());

        return entityModel;
    }

    public Map<String, Object> getCustDetailsScreenData(StateDataType pageState, long inCustId, String employeeId, HttpSession session) {

        Map<String, Object> entityModel = new HashMap<String, Object>();
        TrackingManager trackMngr = iLinkUtils.getTrackingMngrFromSession(session);
        trackMngr.logMessage(TrackingManager.TransType.PREWS, "getCustDetailsScreenData", TrackingManager.MsgType.TXT,
                             "customerId : " + String.valueOf(inCustId) + " repId: " + employeeId);

        int panelId = iLinkUtils.PANEL_5;
        long custId = 0;
        int proposalIDeSign = 0;
        int proposalIDrecord = 0;
        String custIdStr = pageState.getStateItemByKey("custId");

        if (!iLinkUtils.isEmpty(custIdStr))
        {
            custId = Long.parseLong(custIdStr);
        }

        if (inCustId > 0 && inCustId != custId)
        {
            custId = inCustId;
        }
        else
        {
            String panelIdStr = pageState.getStateItemByKey("panelId");
            if (!iLinkUtils.isEmpty(panelIdStr))
            {
                panelId = Integer.valueOf(panelIdStr);
            }
        }

        String topTabIDeSignFromSession = pageState.getStateItemByKey("proposalIDeSign");
        if (!iLinkUtils.isEmpty(topTabIDeSignFromSession))
        {
            proposalIDeSign = Integer.valueOf(topTabIDeSignFromSession);
        }
        else
        {
            proposalIDeSign = iLinkUtils.PROPOSAL_A;
        }
        String topTabIDrecordFromSession = pageState.getStateItemByKey("proposalIDrecord");
        if (!iLinkUtils.isEmpty(topTabIDrecordFromSession))
        {
            proposalIDrecord = Integer.valueOf(topTabIDrecordFromSession);
        }
        else
        {
            proposalIDrecord = iLinkUtils.PROPOSAL_A;
        }

        Object curStateObj = "null";
        Object curStateObjFromSession = "null";
        boolean isRepAssignToCust = false;
        String contactName = "";
        String contactEmail = "";
        String type = "";
        if (custId > 0) {
            switch (panelId) {
                case iLinkUtils.PANEL_1:
                    curStateObj = custDetailsDao.getCustDetails(custId);
                    break;
                case iLinkUtils.PANEL_3:
                    curStateObj = custNotesDao.getCustNotesByCustId(custId);
                    break;
                case iLinkUtils.PANEL_5:
                    type = "esign";
                    curStateObjFromSession = iLinkUtils.getProposalByCustomerIdFromSession(type, custId,proposalIDeSign,session);
                    if (curStateObjFromSession != null)
                    {
                        curStateObj = curStateObjFromSession;
                    }
                    else
                    {
                        curStateObj = proposalDao.getProposalByCustomerId(custId, proposalIDeSign, employeeId);
                        iLinkUtils.setProposalByCustomerIdToSession(type, custId, proposalIDeSign, (List<ProposalDataType>) curStateObj, session);
                    }
                    break;
                case iLinkUtils.PANEL_6:
                    type = "record";
                    curStateObjFromSession = iLinkUtils.getROPProposalByCustomerIdFromSession(type, custId,proposalIDrecord,session);
                    if (curStateObjFromSession != null)
                    {
                        curStateObj = curStateObjFromSession;
                    }
                    else
                    {
                        curStateObj = proposalDao.getROPProposalByCustomerId(custId, proposalIDrecord, employeeId);
                        iLinkUtils.setROPProposalByCustomerIdToSession(type, custId, proposalIDrecord, (List<ProposalDataTypeROP>) curStateObj, session);
                    }
                    break;
            }
            isRepAssignToCust = custDetailsDao.isCustomerAssignedToRepCheck(employeeId, String.valueOf(custId));
            ContactDataType contactInfo = custDetailsDao.getCustContactInfo(String.valueOf(custId));
            contactName = contactInfo.getContactName();
            contactEmail = contactInfo.getContactEmailAddress();
        }
        pageState.setStateItem("panelId", String.valueOf(panelId));
        pageState.setStateItem("custId", String.valueOf(custId));

        entityModel.put("pageId", pageState.getStateItemByKey("pageId"));
        entityModel.put("panelId", pageState.getStateItemByKey("panelId"));
        entityModel.put("panelObj", curStateObj);
        entityModel.put("custId", pageState.getStateItemByKey("custId"));
        entityModel.put("proposalIDrecord", proposalIDrecord);
        entityModel.put("proposalIDeSign", proposalIDeSign);
        entityModel.put("contactName", contactName);
        entityModel.put("contactEmail",  testContactEmailInd? testContactEmail : contactEmail);
        entityModel.put("enableTransFlag", isRepAssignToCust);
        trackMngr.logMessage(TrackingManager.TransType.PSTWS, "getCustDetailsScreenData", TrackingManager.MsgType.TXT, curStateObj.toString());

        return entityModel;
    }

}
