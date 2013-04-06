package ilink.webservices.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ilink.webservices.datatypes.*;
import ilink.domain.EsignEnvelope;
import ilink.domain.GeneralResponse;
import ilink.utils.iLinkUtils;


public class WSGenerateAdocsPdfDao extends iLinkWsDao {

    protected final Log logger = LogFactory.getLog(getClass());

    protected AddDocumentRequestInput prepareInputParams(Object paramsObj) throws Exception {

        EsignEnvelope envelope = (EsignEnvelope) paramsObj;

        AddDocumentRequestInput input = new AddDocumentRequestInput();
        input.setEvent(new EventType());
        ExtLetterRequestEntAttributesWrapper inputParams = new ExtLetterRequestEntAttributesWrapper();
        input.setDATA(inputParams);

        if (envelope != null) {
            try {
                inputParams.setCustomerId(envelope.getCustomerId());
                inputParams.setOperatorId(envelope.getRepId());
                inputParams.setTemplateCode(envelope.getTemplateCode());
                inputParams.setUserId(envelope.getRepId());
                inputParams.setAction(ActionValue.fromValue(envelope.getAction()));
                Parameters params = new Parameters();
                ParameterData[] paramDataArray = new ParameterData[1];
                ParameterData paramData = new ParameterData();
                paramData.setParameterName(envelope.getEnvelopeKey());
                paramData.setParameterValue(String.valueOf(envelope.getEsignEnvelopeId()));
                paramDataArray[0] = paramData;
                params.setParameterData(paramDataArray);
                inputParams.setParameters(params);

                logger.debug("WSGenerateAdocsPdfDao: Received Data for pdf :" + inputParams.toString());

            } catch (RuntimeException rte) {
                logger.error("########## ERROR:  WSGenerateAdocsPdfDao: Failed to prepare input params. Error: ", rte);
                throw new Exception("Failed to prepare input params. Error: ", rte);
            }
        } else {
            logger.error("########## ERROR:  WSGenerateAdocsPdfDao: Failed prepareInputParams: Recieved NULL params. ");
            throw new Exception("Failed to prepare input params. Error: NULL input params.");
        }
        return input;
    }

    protected GeneralResponse prepareResponse(Object outObj) {

        AddDocumentRequestOutput outputDetails = (AddDocumentRequestOutput) outObj;
        if (outputDetails != null) {
            String status = outputDetails.getDATA().getRequestStatus();
            if (status.toUpperCase().contains("ERROR") || status.toUpperCase().contains("FAILED")) {
                response.setStatus(iLinkUtils.TransStatus.ERROR);
                response.setErrorCode(iLinkUtils.ERROR);
                if (outputDetails.getDATA().getErrorMessage() != null) {
                    String msgDesc = iLinkUtils.getMessage("ERROR_BACKEND_SERVICE_RESPONSE",
                            new Object[]{outputDetails.getDATA().getErrorMessage()});
                    response.setErrorMessage(msgDesc);
                    response.setTrackingError(msgDesc);
                }
            } else {
                response.setStatus(iLinkUtils.TransStatus.SUCCESS);
                response.setErrorCode(iLinkUtils.SUCCESS);
                response.setRequestId(Long.valueOf(outputDetails.getDATA().getRequestID()));
            }
        }
        logger.debug("################ WSGenerateAdocsPdfDao prepareResponse: setStatus:" + response.getStatus());
        logger.debug("################ WSGenerateAdocsPdfDao prepareResponse: setErrorCode:" + response.getErrorCode());
        logger.debug("################ WSGenerateAdocsPdfDao prepareResponse: setErrorMessage:" + response.getErrorMessage());
        return response;
    }
}


