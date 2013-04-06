package ilink.service;

import ilink.domain.GeneralResponse;
import ilink.domain.EsignEnvelope;
import ilink.domain.AdocsPdfEntity;
import ilink.utils.iLinkUtils;
import ilink.utils.TrackingManager;
import ilink.dao.EsignEnvelopeDao;
import ilink.dao.PdfGeneratorDao;
import ilink.webservices.dao.iLinkWSDaoInterface;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AdocsPdfGeneratorManagerImpl implements PdfGeneratorManager {
    private EsignEnvelopeDao esignEnvelopeDao;
    private PdfGeneratorDao pdfGeneratorDao;
    private iLinkWSDaoInterface genAdocsPdfDao;
    private boolean testInd;
    private String localTestPdfFilePath;
    private String localTestPdfFilePathROP;
    private String localPdfFolderPath;
    private String pdfFolderPath;

    protected final Log logger = LogFactory.getLog(getClass());

    public String getPdfFolderPath()
    {
        return pdfFolderPath;
    }

    public void setPdfFolderPath(String pdfFolderPath)
    {
        this.pdfFolderPath = pdfFolderPath;
    }

    public String getLocalPdfFolderPath() {
        return localPdfFolderPath;
    }

    public void setLocalPdfFolderPath(String localPdfFolderPath) {
        this.localPdfFolderPath = localPdfFolderPath;
    }

    public iLinkWSDaoInterface getGenAdocsPdfDao() {
        return genAdocsPdfDao;
    }

    public void setGenAdocsPdfDao(iLinkWSDaoInterface genAdocsPdfDao) {
        this.genAdocsPdfDao = genAdocsPdfDao;
    }

    public EsignEnvelopeDao getEsignEnvelopeDao() {
        return esignEnvelopeDao;
    }

    public void setEsignEnvelopeDao(EsignEnvelopeDao esignEnvelopeDao) {
        this.esignEnvelopeDao = esignEnvelopeDao;
    }

    public PdfGeneratorDao getPdfGeneratorDao() {
        return pdfGeneratorDao;
    }

    public void setPdfGeneratorDao(PdfGeneratorDao pdfGeneratorDao) {
        this.pdfGeneratorDao = pdfGeneratorDao;
    }

    public String getLocalTestPdfFilePath() {
        return localTestPdfFilePath;
    }

    public void setLocalTestPdfFilePath(String localTestPdfFilePath) {
        this.localTestPdfFilePath = localTestPdfFilePath;
    }

    public String getLocalTestPdfFilePathROP()
    {
        return localTestPdfFilePathROP;
    }

    public void setLocalTestPdfFilePathROP(String localTestPdfFilePathROP)
    {
        this.localTestPdfFilePathROP = localTestPdfFilePathROP;
    }

    public boolean isTestInd() {
        return testInd;
    }

    public void setTestInd(boolean testInd) {
        this.testInd = testInd;
    }

    public GeneralResponse getContractPdfPathDWR(long requestId, double envelopeId) {
        return getContractPdfPath(requestId, envelopeId);
    }

    protected GeneralResponse getContractPdfPath(long requestId, double envelopeId) {
        GeneralResponse response = iLinkUtils.prepareRequestResponse(envelopeId, requestId);
        String generatedPdfPath = "";
        String logMsg = " EnvelopeId : " + envelopeId + " RequestId: " + requestId;
        TrackingManager trackMngr = iLinkUtils.getTrackingMngrFromDWR();

        try {
            trackMngr.logMessage(TrackingManager.TransType.PREWS, "getGeneratedPdfPath", TrackingManager.MsgType.TXT, logMsg);

            AdocsPdfEntity pdfEntity = pdfGeneratorDao.getGeneratedPdfPath(requestId);
            if (pdfEntity.isCompleted() && !iLinkUtils.isEmpty(pdfEntity.getLetterFileIndex())) {
                String pdfFolder = iLinkUtils.getServerName().equals("localhost") ? localPdfFolderPath : pdfFolderPath;
                generatedPdfPath = pdfFolder + pdfEntity.getLetterFileIndex();
                response.setResult(generatedPdfPath);
                esignEnvelopeDao.updateEnvelopeStatusAndPdfPath(envelopeId, EsignEnvelope.EsignEnvelopeStatus.COMPLETED, generatedPdfPath);
                EsignEnvelope envelope = iLinkUtils.getLastEsignEnvelopeFromSessionDWR();
                if (envelope != null && envelope.getEsignEnvelopeId() == envelopeId) {
                    envelope.setGeneratedPdfFile(generatedPdfPath);
                    envelope.setEsignEnvelopeStatus(EsignEnvelope.EsignEnvelopeStatus.COMPLETED);
                }
            } else if (pdfEntity.isFailed()) {
                String errorMsg = pdfEntity.getErrorMessage();
                if (pdfEntity.isFailed() && !iLinkUtils.isEmpty(errorMsg)) {
                    esignEnvelopeDao.updateEsignEnvelopeWithError(envelopeId, errorMsg);
                    response.setErrorMessage(errorMsg);
                    response.setStatus(iLinkUtils.TransStatus.ERROR);
                    response.setErrorCode(iLinkUtils.ERROR);
                }
            }
        } catch (Exception ex) {
            response = iLinkUtils.handleError("ERROR_GENERATE_CONTRACT", null);
            String error = " getContractPdfPathDWR for " + logMsg + " exception occured :" + iLinkUtils.getExceptionMessage(ex);
            logger.error("################ ERROR " + error);
            response.setTrackingError(error);
            trackMngr.logMessage(TrackingManager.TransType.ERRWS, "getGeneratedPdfPath", TrackingManager.MsgType.TXT, error);            
            try {
                esignEnvelopeDao.updateEsignEnvelopeWithError(envelopeId, error);
            } catch (Exception e) {
            }
        }
        logger.info("################ getGeneratedPdfPath for " + logMsg + " with result :" + response.toString());
        trackMngr.logMessage(TrackingManager.TransType.PSTWS, "getGeneratedPdfPath", TrackingManager.MsgType.TXT, response.toString());        
        return response;
    }

    public GeneralResponse generateContractDWR(EsignEnvelope envelope) {
        GeneralResponse response = null;
        TrackingManager trackMngr = iLinkUtils.getTrackingMngrFromDWR();
        double envelopeId = 0;
        try {
            trackMngr.logMessage(TrackingManager.TransType.PREWS, "generateContract", TrackingManager.MsgType.TXT, envelope.toString());
            String validationError = validateInputEnvelope(envelope);
            if (iLinkUtils.isEmpty(validationError)) {

                //create envelope in DB tables
                esignEnvelopeDao.createEsignEnvelope(envelope);
                envelopeId = envelope.getEsignEnvelopeId();
                response = genAdocsPdfDao.callService(envelope);

                if (response.getStatus().equals(iLinkUtils.TransStatus.SUCCESS.toString())) {
                    //get letterRequestId.
                    long requestId = response.getRequestId();
                    envelope.setPdfRequestId(String.valueOf(requestId));
                    iLinkUtils.setLastEsignEnvelopeToSession(envelope);
                    response = getContractPdfPath(requestId, envelopeId);
                    String serverName = iLinkUtils.getServerName();
                    if (serverName.equals("localhost"))
                    { //in test case  of local host - ftp file to local host
                        String testFilePath = envelope.getEnvelopeType().equals("ESIGN") ?  localTestPdfFilePath : localTestPdfFilePathROP;
                        response.setResult(localPdfFolderPath + testFilePath);
                        envelope.setGeneratedPdfFile(localPdfFolderPath + testFilePath);
                    }
                } else {
                    if (envelopeId > 0)
                        esignEnvelopeDao.updateEsignEnvelopeWithError(envelopeId, response.getTrackingError());
                }
            } else {
                response = iLinkUtils.handleError(validationError, null);
            }
        } catch (Exception ex) {
            response = iLinkUtils.handleError("ERROR_GENERATE_CONTRACT", null);
            String error = " Customer : " + envelope.getCustomerId() + " envelopeId: " + envelopeId + " exception occured :" + iLinkUtils.getExceptionMessage(ex);
            logger.error("################ ERROR generateContract : " + error);
            response.setTrackingError(error);
            trackMngr.logMessage(TrackingManager.TransType.ERRWS, "generateContract", TrackingManager.MsgType.TXT, error);
            try {
                if (envelopeId > 0)
                    esignEnvelopeDao.updateEsignEnvelopeWithError(envelopeId, error);
            } catch (Exception e) {
            }
        }
        trackMngr.logMessage(TrackingManager.TransType.PSTWS, "generateContract", TrackingManager.MsgType.TXT, response.toString());
        logger.info("################ generateContractDWR for envelope:" + envelopeId + " with result :" + response.toString());
        return response;
    }

    protected String validateInputEnvelope(EsignEnvelope envelope) {

        String validationError = null;
        if (iLinkUtils.isEmpty(envelope.getBusinessName())) {
            validationError = "ERROR_BUSINESS_NAME";
        } //else if (iLinkUtils.isEmpty(envelope.getContactName())) {
        //    validationError = "ERROR_CONTACT_NAME";
      //  }
        if (iLinkUtils.isEmpty(envelope.getContactEmail())) {
            envelope.setContactEmail(" ");
        }
        if (iLinkUtils.isEmpty(validationError)) {
            envelope.setEsignEnvelopeStatus(EsignEnvelope.EsignEnvelopeStatus.IN_PROCESS);
            envelope.setEsignEnvelopeName(envelope.getBusinessName());
            envelope.setRepIdAll(iLinkUtils.getEmployeeIdFromDWR());
            envelope.setSignatureDateAll(iLinkUtils.getCurrentLogicalDate());
        }
        return validationError;
    }
}