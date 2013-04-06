package ilink.service;

import ilink.dao.EsignEnvelopeDao;
import ilink.domain.CreditApplicationEnvelope;
import ilink.domain.EsignEnvelope;
import ilink.domain.GeneralResponse;
import ilink.utils.DocsignManager;
import ilink.utils.TrackingManager;
import ilink.utils.iLinkUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

public class SignatureManagerImpl implements SignatureManager {

    private EsignEnvelopeDao esignEnvelopeDao;
    protected final Log logger = LogFactory.getLog(getClass());

    public EsignEnvelopeDao getEsignEnvelopeDao() {
        return esignEnvelopeDao;
    }

    public void setEsignEnvelopeDao(EsignEnvelopeDao esignEnvelopeDao) {
        this.esignEnvelopeDao = esignEnvelopeDao;
    }

    public GeneralResponse sendDocumentToSignDWR(String requestId, String contactName,
                                                 String contactEmail, boolean inPersonInd) {
        GeneralResponse response;
        double envelopeId = 0;
        TrackingManager trackMngr = iLinkUtils.getTrackingMngrFromDWR();
        try {
            EsignEnvelope envelope = iLinkUtils.getLastEsignEnvelopeFromSessionDWR();
            if (envelope != null && envelope.getPdfRequestId().equals(requestId)) {
                envelopeId = envelope.getEsignEnvelopeId();
                envelope.setContactEmail(contactEmail);
                envelope.setContactName(contactName);
                envelope.setInPersonInd(inPersonInd);
                esignEnvelopeDao.updateEsignEnvelopeContactInfo(envelopeId, contactName, contactEmail);
                // user session to store details
                trackMngr.logMessage(TrackingManager.TransType.PREWS, "sendDocumentToSign", TrackingManager.MsgType.TXT, envelope.toString());
                DocsignManager mnr = iLinkUtils.getDocsignManagerFromDWR();
                response = mnr.executeDocuSignService(envelope);
                if (response.getStatus().equals(iLinkUtils.TransStatus.SUCCESS.toString())) {
                    esignEnvelopeDao.updateEnvelopeDocusignInfo(envelopeId, (String) response.getResult(), EsignEnvelope.DocusignEnvelopeStatus.SENT);
                } else {
                    esignEnvelopeDao.updateEsignEnvelopeDocusignStatusWithError(envelopeId, EsignEnvelope.DocusignEnvelopeStatus.FAILED, response.getErrorMessage());
                }
            } else {
                String error = "Wrong input params -> envelopeRequestId=" + envelope.getPdfRequestId() + "  inputRequestId=" + requestId;
                trackMngr.logMessage(TrackingManager.TransType.ERRWS, "sendDocumentToSign", TrackingManager.MsgType.TXT, error );
                response = iLinkUtils.handleError("DOCUSIGN_ERROR",  new Object[]{error});
            }
        } catch (Exception ex) {
            String error = iLinkUtils.getExceptionMessage(ex);
            response = iLinkUtils.handleError("DOCUSIGN_ERROR",  new Object[]{error});
            logger.error("################ ERROR sendDocumentToSign for envelope : " + envelopeId + " exception occured :" + error);
            response.setTrackingError(error);
            trackMngr.logMessage(TrackingManager.TransType.ERRWS, "sendDocumentToSign", TrackingManager.MsgType.TXT, error);
        }
        trackMngr.logMessage(TrackingManager.TransType.PSTWS, "sendDocumentToSign", TrackingManager.MsgType.TXT, response.toString());
        logger.info("################ sendDocumentToSign for envelope : " + envelopeId + " response :" + response.toString());
        return response;
    }

    public GeneralResponse sendTemplateRequestToSignDWR(String requestId, String contactName, String contactEmail, boolean inPersonInd, boolean isJoint)
    {
        GeneralResponse response;
        double envelopeId = 0;
        TrackingManager trackMngr = iLinkUtils.getTrackingMngrFromDWR();
        try
        {
            CreditApplicationEnvelope envelope = new CreditApplicationEnvelope();

            String dateInLongFmt = new String(""+new Date().getTime());
            envelope.transactionId = dateInLongFmt;
            envelope.emailSubject = "This is first Email - "+dateInLongFmt;
            envelope.emailContent = "This is Email Content";
            //envelope.templateId = "4E50A561-9D4F-4546-8EFE-61314AA35EDC";

            envelope.roleSignerOne = "Signer 1";
            envelope.recipientIdSignerOne = 1338155411;
            envelope.nameSignerOne = "Cynthia Wilson";
            envelope.userNameSignerOne = "Cynthia Wilson";
            envelope.emailSignerOne = "natkot@gmail.com";

            envelope.roleSignerTwo = "Signer 2";
            envelope.recipientIdSignerTwo = 2;
            envelope.nameSignerTwo = "Customer Name 2";
            envelope.userNameSignerTwo = "Csutomer Name 2";
            envelope.emailSignerTwo = "ctest2do@gmail.com";

            envelope.customerId = "1338155411";
            envelope.consultantName = "Cynthia Wilson";
            envelope.consultantPhone = "919-461-9126";
            envelope.consultantFax = "919-461-1878";
            envelope.directoryName = "DexOne Yellow Page March 2011, Morrisville";
            envelope.totalMonthlySpending = "$2314";
            envelope.setJointSigner(isJoint);

            envelopeId = envelope.getCreditAppEnvelopeId();
            envelope.setContactEmail(contactEmail);
            envelope.setContactName(contactName);
            envelope.setEnvelopeSigningLocation_InPerson(inPersonInd);
            //esignEnvelopeDao.updateEsignEnvelopeContactInfo(envelopeId, contactName, contactEmail);
            // user session to store details
            trackMngr.logMessage(TrackingManager.TransType.PREWS, "sendTemplateRequestToSign", TrackingManager.MsgType.TXT, envelope.toString());
            DocsignManager mnr = iLinkUtils.getDocsignManagerFromDWR();
            response = mnr.executeDocuSignTemplateService(envelope);

            /*
            if (response.getStatus().equals(iLinkUtils.TransStatus.SUCCESS.toString()))
            {
                esignEnvelopeDao.updateEnvelopeDocusignInfo(envelopeId, (String) response.getResult(), EsignEnvelope.DocusignEnvelopeStatus.SENT);
            }
            else
            {
                esignEnvelopeDao.updateEsignEnvelopeDocusignStatusWithError(envelopeId, EsignEnvelope.DocusignEnvelopeStatus.FAILED, response.getErrorMessage());
            }
            */
        }
        catch (Exception ex)
        {
            String error = iLinkUtils.getExceptionMessage(ex);
            response = iLinkUtils.handleError("DOCUSIGN_ERROR",  new Object[]{error});
            logger.error("################ ERROR sendTemplateRequestToSignDWR for envelope : " + envelopeId + " exception occured :" + error);
            response.setTrackingError(error);
            trackMngr.logMessage(TrackingManager.TransType.ERRWS, "sendTemplateRequestToSignDWR", TrackingManager.MsgType.TXT, error);
        }

        trackMngr.logMessage(TrackingManager.TransType.PSTWS, "sendTemplateRequestToSignDWR", TrackingManager.MsgType.TXT, response.toString());
        logger.info("################ sendTemplateRequestToSignDWR for envelope : " + envelopeId + " response :" + response.toString());
        return response;
    }
}
