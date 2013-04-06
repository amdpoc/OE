package ilink.service;

import ilink.dao.CustNotesDao;
import ilink.domain.*;
import ilink.webservices.dao.iLinkWSDaoInterface;
import ilink.utils.iLinkUtils;
import ilink.utils.TrackingManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class CustNotesManagerImpl implements CustNotesManager {

    private CustNotesDao custNotesDao;
    private iLinkWSDaoInterface creCustNoteDao;
    protected final Log logger = LogFactory.getLog(getClass());

    public void setCustNotesDao(CustNotesDao custNotesDao) {
        this.custNotesDao = custNotesDao;
    }

    public void setCreCustNoteDao(iLinkWSDaoInterface creCustNoteDao) {
        this.creCustNoteDao = creCustNoteDao;
    }

    public List<CustNotesListItem> getCustNotesByCustId(long custId) {

        List<CustNotesListItem> resultList = null;
        try {
            resultList = custNotesDao.getCustNotesByCustId(custId);
        } catch (Exception ex) {
            logger.error("################ CustNotesManagerImpl.getCustNotesByCustId - error: " + ex.getMessage());
        }
        return resultList;
    }

    public List<CustNotesTxListItem> getCustNotesTxByCustId(long custId) {

        List<CustNotesTxListItem> resultList = null;
        try {
            resultList = custNotesDao.getCustNotesTxByCustId(custId);
        } catch (Exception ex) {
            logger.error("################ CustNotesManagerImpl.getCustNotesTxByCustId - error: " + ex.getMessage());
        }
        return resultList;
    }

    public GeneralResponse createNewCustNote(long custId, String noteText) {
        GeneralResponse response;
        TrackingManager trackMngr = iLinkUtils.getTrackingMngrFromDWR();
        try {
            String repId = iLinkUtils.getEmployeeIdFromDWR();
            CustNotesTxDataType noteParams = new CustNotesTxDataType();
            noteParams.setCustomerId(custId);
            noteParams.setRepId(Integer.valueOf(repId));
            noteParams.setCreationDate(iLinkUtils.getLogicalCreationDate());
            noteParams.setNoteText(noteText);
            noteParams.setNoteType(iLinkUtils.DEFAULT_NOTE_TYPE);
            noteParams.setOfficeType(iLinkUtils.getEmployeeOfficeTypeFromDWR());

            trackMngr.logMessage(TrackingManager.TransType.PREWS, "createNewCustNote", TrackingManager.MsgType.TXT, noteParams.toString());

            response = creCustNoteDao.callService(noteParams);
            logger.info("################ createNewCustNote  for -> customerd: " + custId + " with result: " + response.toString());            

        } catch (Exception ex) {
            String errorMsg = iLinkUtils.getExceptionMessage(ex);
            response = iLinkUtils.handleError("ERROR_BACKEND_SERVICE_RESPONSE", new Object[]{errorMsg});
            logger.error("################ ERROR:  createNewCustNote exception occured :" + errorMsg);
        }
        trackMngr.logMessage(TrackingManager.TransType.PSTWS, "createNewCustNote", TrackingManager.MsgType.TXT, response.toString());
        return response;
    }

}
