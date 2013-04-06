package ilink.webservices.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ilink.webservices.datatypes.*;
import ilink.domain.*;


public class WSCreCustNoteDao extends iLinkWsDao {

    protected final Log logger = LogFactory.getLog(getClass());

    protected ILinkMobileCreCstNoteParamsType prepareInputParams(Object paramsObj) throws Exception {

        CustNotesTxDataType noteParams = (CustNotesTxDataType) paramsObj;

        ILinkMobileCreCstNoteParamsType input = new ILinkMobileCreCstNoteParamsType();
        input.setEvent(new EventType());
        ILinkMobileCreCstNoteParamsDataType inputParams = new ILinkMobileCreCstNoteParamsDataType();
        input.setDATA(inputParams);

        if (noteParams != null) {
            try {
                inputParams.setCustomerId(noteParams.getCustomerId());
                inputParams.setRepId(noteParams.getRepId());
                inputParams.setCreationDate(noteParams.getCreationDate());
                inputParams.setNoteText(noteParams.getNoteText());
                inputParams.setNoteType(noteParams.getNoteType());
                inputParams.setOfficeType(noteParams.getOfficeType());
                
                logger.debug("WSCreCustNoteDao: Received Data for new Note :" + inputParams.toString());

            } catch (RuntimeException rte) {
                logger.error("########## ERROR:  WSCreCustNoteDao: Failed to prepare input params. Error: ", rte );
                throw new Exception("Failed to prepare input params. Error: ", rte);
            }
        } else {
            logger.error("########## ERROR:  WSCreCustNoteDao: Failed prepareInputParams: Recieved NULL params. ");
            throw new Exception("Failed to prepare input params. Error: NULL input params.");
        }
        return input;
    }
}
