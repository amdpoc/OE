package ilink.docuSign;

import ilink.domain.CreditApplicationEnvelope;
import net.docusign.ws.DSApiService;
import net.docusign.www.API._3_0.APIServiceSoap;

import net.docusign.www.API._3_0.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DocuSignService {

    public String systemUserName;
    public String systemUserPassword;
    public String domain;
    public String apiAccountId;
    public String templateID;
    public String systemUserIntegratorKey;
    public String docusignURL;

    public static enum DocusignServiceEnvelopeStatus {
        COMPLETE, VIEWING_COMPLETE, CANCEL, DECLINE, SESSION_TIMEOUT, TTL_EXPIERED,
        EXCEPTION, ACCESS_CODE_FAILED, ID_CHECK_FAILED,  FAX_PENDING
    }

    protected final Log logger = LogFactory.getLog(getClass());

    public APIServiceSoap getAPIService(String userName) throws Exception {
        APIServiceSoap service = null;
        try {
            DSApiService dsApiService = new DSApiService(docusignURL, apiAccountId, "[" + systemUserIntegratorKey + "]" +
                    "[" + userName + "]" + systemUserName, systemUserPassword);
            service = dsApiService.getApiService();
        } catch (Exception ex) {
            logger.error("################ ERROR DocuSignService: getAPIService() -> exception occured :" + ex.getMessage());
            throw ex;
        }
        return service;
    }

    public String executeDocuSignService(boolean inPersonInd, String userName, Envelope envelope, String recipientClientId,
                                         String recipientUserName, String recipientEmail, String returnUrl)throws Exception {
        String requestRecipientToken = null;

        try {
            APIServiceSoap service = getAPIService(userName);
            envelope.setAccountId(apiAccountId);

            //Creating Envelope at DocuSign and Sending it for In-Person Signature (Embedded)
            EnvelopeStatus envelopeStatus = service.createAndSendEnvelope(envelope);
            String envelopeId = envelopeStatus.getEnvelopeID();
            logger.info("################ DocuSignService: executeDocuSignService() -> createAndSendEnvelope -> " + envelopeId);
            requestRecipientToken = envelopeId;
            //need to check status and generate error otherwise
            if (inPersonInd) {
                RequestRecipientTokenAuthenticationAssertion authenticationAssertion = initRequestRecipientTokenAuthenticationAssertion(userName, domain);
                RequestRecipientTokenClientURLs clientURLs = initRequestRecipientTokenClientURLs(returnUrl);
                requestRecipientToken = service.requestRecipientToken(envelopeId, recipientClientId, recipientUserName,
                        recipientEmail, authenticationAssertion, clientURLs);
                logger.info("################ DocuSignService: executeDocuSignService() -> Request Recipient Token-> " + requestRecipientToken);
            }
        } catch (Exception ex) {
            logger.error("################ ERROR DocuSignService: executeDocuSignService() -> exception occured :" + ex.getMessage());
            throw ex;
        }
        return requestRecipientToken;
    }

    public String executeDocuSignTemplateService(CreditApplicationEnvelope envelope, boolean inPersonInd, String userName, String recipientClientId, String recipientUserName, String recipientEmail, String returnUrl,
                                                 TemplateReference[] templateReferences, Recipient[] recipients, EnvelopeInformation envelopeInformation,
                                                 boolean bActivateEnvelope) throws Exception
    {
        String requestRecipientToken = null;

        try
        {
            APIServiceSoap service = getAPIService(userName);

            //Creating Envelope at DocuSign and Sending it for In-Person Signature (Embedded)
            envelopeInformation.setAccountId(apiAccountId);
            envelope.setTemplateID(templateID);
            templateReferences[0].setTemplate(envelope.getTemplateID());

            EnvelopeStatus envelopeStatus =  service.createEnvelopeFromTemplates(templateReferences, recipients, envelopeInformation, bActivateEnvelope);
            String envelopeId = envelopeStatus.getEnvelopeID();
            logger.info("################ DocuSignService: executeDocuSignTemplateService() -> createEnvelopeFromTemplates -> " + envelopeId);
            requestRecipientToken = envelopeId;
            //need to check status and generate error otherwise
            if (inPersonInd)
            {
                RequestRecipientTokenAuthenticationAssertion authenticationAssertion = initRequestRecipientTokenAuthenticationAssertion(userName, domain);
                RequestRecipientTokenClientURLs clientURLs = initRequestRecipientTokenClientURLs(returnUrl);
                requestRecipientToken = service.requestRecipientToken(envelopeId, recipientClientId, recipientUserName, recipientEmail, authenticationAssertion, clientURLs);
                logger.info("################ DocuSignService: executeDocuSignTemplateService() -> Request Recipient Token-> " + requestRecipientToken);
            }
        }
        catch (Exception ex)
        {
            logger.error("################ ERROR DocuSignService: executeDocuSignTemplateService() -> exception occured :" + ex.getMessage());
            throw ex;
        }
        return requestRecipientToken;
    }

    public RequestRecipientTokenClientURLs initRequestRecipientTokenClientURLs(String returnUrl) {

        RequestRecipientTokenClientURLs clientURLs = new RequestRecipientTokenClientURLs();
        clientURLs.setOnSigningComplete(returnUrl + DocusignServiceEnvelopeStatus.COMPLETE.toString());
        clientURLs.setOnViewingComplete(returnUrl + DocusignServiceEnvelopeStatus.VIEWING_COMPLETE.toString());
        clientURLs.setOnCancel(returnUrl + DocusignServiceEnvelopeStatus.CANCEL.toString());
        clientURLs.setOnDecline(returnUrl + DocusignServiceEnvelopeStatus.DECLINE.toString());
        clientURLs.setOnSessionTimeout(returnUrl + DocusignServiceEnvelopeStatus.SESSION_TIMEOUT.toString());
        clientURLs.setOnTTLExpired(returnUrl + DocusignServiceEnvelopeStatus.TTL_EXPIERED.toString());
        clientURLs.setOnException(returnUrl + DocusignServiceEnvelopeStatus.EXCEPTION.toString());
        clientURLs.setOnAccessCodeFailed(returnUrl + DocusignServiceEnvelopeStatus.ACCESS_CODE_FAILED.toString());
        clientURLs.setOnIdCheckFailed(returnUrl + DocusignServiceEnvelopeStatus.ID_CHECK_FAILED.toString());
        clientURLs.setOnFaxPending(returnUrl + DocusignServiceEnvelopeStatus.FAX_PENDING.toString());
        return  clientURLs;
    }

    public RequestRecipientTokenAuthenticationAssertion initRequestRecipientTokenAuthenticationAssertion(String userName, String domain) {
        RequestRecipientTokenAuthenticationAssertion authenticationAssertion = new RequestRecipientTokenAuthenticationAssertion();
        authenticationAssertion.setAssertionID(userName);
        Calendar authenticationInstant = GregorianCalendar.getInstance();
        authenticationInstant.setTime(new Date());
        authenticationAssertion.setAuthenticationInstant(authenticationInstant);
        authenticationAssertion.setAuthenticationMethod(RequestRecipientTokenAuthenticationAssertionAuthenticationMethod.Password);
        authenticationAssertion.setSecurityDomain(domain);
        return authenticationAssertion;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSystemUserName() {
        return systemUserName;
    }

    public void setSystemUserName(String systemUserName) {
        this.systemUserName = systemUserName;
    }

    public String getSystemUserPassword() {
        return systemUserPassword;
    }

    public void setSystemUserPassword(String systemUserPassword) {
        this.systemUserPassword = systemUserPassword;
    }

    public String getApiAccountId() {
        return apiAccountId;
    }

    public void setApiAccountId(String apiAccountId) {
        this.apiAccountId = apiAccountId;
    }

    public String getTemplateID()
    {
        return templateID;
    }

    public void setTemplateID(String templateID)
    {
        this.templateID = templateID;
    }

    public String getSystemUserIntegratorKey() {
        return systemUserIntegratorKey;
    }

    public void setSystemUserIntegratorKey(String systemUserIntegratorKey) {
        this.systemUserIntegratorKey = systemUserIntegratorKey;
    }

    public String getDocusignURL() {
        return docusignURL;
    }

    public void setDocusignURL(String docusignURL) {
        this.docusignURL = docusignURL;
    }
}
