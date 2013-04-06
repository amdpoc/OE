package ilink.docuSign;

import ilink.domain.CreditApplicationEnvelope;
import ilink.domain.EsignEnvelope;
import ilink.domain.GeneralResponse;
import ilink.utils.DocsignManager;
import ilink.utils.iLinkUtils;
import net.docusign.www.API._3_0.*;
import org.apache.axis.types.PositiveInteger;
import org.apache.axis.types.UnsignedShort;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DocuSignManager implements DocsignManager {

    private DocuSignService docsignService;
    //private DSApiService dsApiService = null;
    private boolean docuTestInd;
    private String repEmail;
    private String responseUrl;

    // Anchor Tag for attached document
    public static final String signatureAnchorTag = "AuthorizedCustomerSignature";
    public static final String signatureDateAnchorTag = "SignatureDate";
    public static final String fullNameAnchorTag = "PrintName";
    public static final String titleAnchorTag = "TitleEmail";
    public static final String emailAnchorTag = "EmailMarketingConsultant";
    public static final String initialHereAnchorTag = "INITIALHERE";

    //constants
    public static final String documentFileExtension = "pdf";

    protected final Log logger = LogFactory.getLog(getClass());

    public void init(javax.servlet.http.HttpSession session) {
        repEmail = docuTestInd ? repEmail : iLinkUtils.getEmployeeEmailFromSession(session);
    }

    public GeneralResponse executeDocuSignService(EsignEnvelope envelope) {
        //  For InPersonSigner:
        //  The Email element should be that of the 'Signing Host'
        // (the person who will receive the email and assist the signer with the in-person signing process.)
        //  The UserName element should be that of the 'Signing Host'
        // (the Email and UserName combination must match an active DocuSign user).
        GeneralResponse response = iLinkUtils.prepareRequestResponse(envelope.getEsignEnvelopeId(),
                Long.valueOf(envelope.getPdfRequestId()));
        try {
            boolean inPersonInd = envelope.isInPersonInd();
            String recipentUserName = envelope.getContactName();
            String recipentEmail = envelope.getContactEmail();
            String recipentClientId = envelope.getCustomerId();

            Recipient[] recipients = getRecipientsInfo(recipentClientId, recipentUserName, recipentEmail, inPersonInd);

            String pdfPath = envelope.getGeneratedPdfFile();
            String requestId = envelope.getPdfRequestId();
            String docDesc = iLinkUtils.getMessage("DOCUSIGN_DOC_DESC", new Object[]{});
            String docName = iLinkUtils.getMessage("DOCUSIN_DOC_NAME", new Object[]{});
            Document[] documents = getDocumentsInfo(requestId, docName, docDesc, pdfPath);

            Map<String, String> customParams = getCustomParams(envelope);

            //Creating docusign Envelope Object
            Envelope docusignEnvelope = null;
            if (envelope.getEnvelopeType().equals("ESIGN"))
            {
                docusignEnvelope = createEnvelope(recipients, documents, customParams);
            }
            else
            {
                docusignEnvelope = createEnvelopeROP(recipients, documents, customParams);
            }
            //set email subject and Blurb
            String emailSubject = iLinkUtils.getMessage("DOCUSIGN_EMAIL_SUBJECT", new Object[]{recipentClientId, envelope.getBusinessName()});
            String emailBlurb = iLinkUtils.getMessage("DOCUSIGN_EMAIL_BLURB", new Object[]{});

            docusignEnvelope.setSubject(emailSubject);
            docusignEnvelope.setEmailBlurb(emailBlurb);

            String returnUrl = buildReturnUrl(envelope);
            logger.info("################ before executeDocuSignService for envelope : " + envelope.getEsignEnvelopeId() +
                    " with params : inPersonInd=" + inPersonInd + " repEmail=" + repEmail + " recipentClientId=" + recipentClientId +
                    " recipentUserName=" + recipentUserName + " recipentEmail=" + recipentEmail + " returnUrl=" + returnUrl);

            String url = docsignService.executeDocuSignService(inPersonInd, repEmail, docusignEnvelope, recipentClientId,
                    recipentUserName, recipentEmail, returnUrl);

            response.setResult(url);
            logger.info("################ after executeDocuSignService for envelope : " + envelope.getEsignEnvelopeId() +
                    " with response :" + response.toString());

        } catch (Exception ex) {
            String error = iLinkUtils.getExceptionMessage(ex);
            response = iLinkUtils.handleError("DOCUSIGN_ERROR", new Object[]{error});
            logger.error("################ ERROR executeDocuSignService for envelope : " + envelope.getEsignEnvelopeId() +
                    " exception occured :" + error);
            response.setTrackingError(error);
        }
        return response;
    }

    public GeneralResponse executeDocuSignTemplateService(CreditApplicationEnvelope envelope)
    {
        //  For InPersonSigner:
        //  The Email element should be that of the 'Signing Host'
        // (the person who will receive the email and assist the signer with the in-person signing process.)
        //  The UserName element should be that of the 'Signing Host'
        // (the Email and UserName combination must match an active DocuSign user).
        GeneralResponse response = iLinkUtils.prepareRequestResponse(envelope.getCreditAppEnvelopeId(), 0);
        try
        {
            boolean inPersonInd = envelope.isEnvelopeSigningLocation_InPerson();
            String recipientUserName = envelope.getContactName();
            String recipientEmail = envelope.getContactEmail();
            String recipientClientId = envelope.getCustomerId();
            String returnUrl = buildCreditAppReturnUrl(envelope);

            Recipient[] recipients = createJointRecipients (envelope);
            TemplateReference [] templateReferences = createTemplateReference (envelope);

            //String pdfPath = envelope.getGeneratedPdfFile();
            //String requestId = envelope.getPdfRequestId();
            //String docDesc = iLinkUtils.getMessage("DOCUSIGN_DOC_DESC", new Object[]{});
            //String docName = iLinkUtils.getMessage("DOCUSIN_DOC_NAME", new Object[]{});
            //Document[] documents = getDocumentsInfo(requestId, docName, docDesc, pdfPath);
            EnvelopeInformation envelopeInformation = createCreditAppEnvelopeInformation(envelope);
            //Map<String, String> customParams = getCustomParams(envelope);

            //String returnUrl = buildReturnUrl(envelope);
            logger.info("################ before executeDocuSignTemplateService for envelope : " + envelope.getCreditAppEnvelopeId() +
                    " with params : inPersonInd=" + inPersonInd + " repEmail=" + repEmail + " recipentClientId=" + recipientClientId +
                    " recipentUserName=" + recipientUserName + " recipentEmail=" + recipientEmail + " returnUrl=" + returnUrl);

            String url =  docsignService.executeDocuSignTemplateService(envelope, inPersonInd, repEmail, recipientClientId,  recipientUserName, recipientEmail,
                                                                                            returnUrl, templateReferences, recipients, envelopeInformation, true);

            response.setResult(url);
            logger.info("################ after executeDocuSignTemplateService for envelope : " + envelope.getCreditAppEnvelopeId() +
                    " with response :" + response.toString());

        } catch (Exception ex) {
            String error = iLinkUtils.getExceptionMessage(ex);
            response = iLinkUtils.handleError("DOCUSIGN_ERROR", new Object[]{error});
            logger.error("################ ERROR executeDocuSignTemplateService for envelope : " + envelope.getCreditAppEnvelopeId() +
                    " exception occured :" + error);
            response.setTrackingError(error);
        }
        return response;
    }

    protected String buildReturnUrl(EsignEnvelope envelope) {
        return responseUrl + "&envelopeId=" + envelope.getEsignEnvelopeId() + "&docusignState=";
    }

    protected String buildCreditAppReturnUrl(CreditApplicationEnvelope envelope)
    {
        return responseUrl + "&envelopeId=" + envelope.getCreditAppEnvelopeId() + "&docusignState=";
    }

    protected Map<String, String> getCustomParams(EsignEnvelope envelope) throws Exception {

        String customerId = envelope.getCustomerId();
        String phoneNumber = envelope.getPhoneNumber();
        String requestId = envelope.getPdfRequestId();

        Map<String, String> customParams = new HashMap<String, String>();
        // Later can be use for archiving to DI
        customParams.put("customer_id", customerId);// Customer ID of generated PDF
        customParams.put("telephone_number", phoneNumber);// Customer telephone number
        customParams.put("requestId", requestId);//requestId

        return customParams;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    protected Document[] getDocumentsInfo(String requestId, String docName,
                                          String docDesc, String pdfPath) throws Exception {
        Document[] documents = new Document[1];
        documents[0] = new Document();
        documents[0].setID(new PositiveInteger(requestId));
        documents[0].setName(docName);
        documents[0].setFileExtension(documentFileExtension);
        documents[0].setAttachmentDescription(docDesc);
        documents[0].setPDFBytes(convertFileInByteArray(pdfPath));

        return documents;
    }

    protected Recipient[] getRecipientsInfo(String recipientClientId, String userName, String email, boolean inPersonInd) {

        Recipient[] recipients = new Recipient[1];
        recipients[0] = new Recipient();
        recipients[0].setID(new PositiveInteger(recipientClientId));
        recipients[0].setUserName(userName);
        recipients[0].setEmail(email);
        recipients[0].setType(RecipientTypeCode.Signer);
        recipients[0].setRequireIDLookup(false);
        recipients[0].setRoutingOrder(new UnsignedShort(1));

        // If variable is true then setting following object for in-person signature.
        // Same recipentClientID use for requestRecipentToken Service.
        if (inPersonInd) {
            RecipientCaptiveInfo captiveInfo = new RecipientCaptiveInfo();
            captiveInfo.setClientUserId(recipientClientId);
            recipients[0].setCaptiveInfo(captiveInfo);
        }
        return recipients;
    }

    protected Recipient[] createJointRecipients(CreditApplicationEnvelope envelope)
    {
        Recipient[] recipient = new Recipient [1];

        if (envelope.isJointSigner())	{
            recipient = new Recipient [2];
            recipient[1] = new Recipient();
            recipient[1].setID (new PositiveInteger(envelope.getRecipientIdStringSignerTwo()));
            recipient[1].setUserName (envelope.getNameSignerTwo());
            recipient[1].setSignerName (envelope.getNameSignerTwo());
            recipient[1].setEmail (envelope.getEmailSignerTwo());
            recipient[1].setRequireIDLookup (false);
            recipient[1].setRoutingOrder (new UnsignedShort("2"));
            recipient[1].setRoleName (envelope.getRoleSignerTwo());

            if (envelope.isEnvelopeSigningLocation_InPerson())	{
                recipient[1].setType (RecipientTypeCode.InPersonSigner);
                RecipientCaptiveInfo captiveInfo = new RecipientCaptiveInfo();
                captiveInfo.setClientUserId (envelope.getRecipientIdStringSignerTwo());
                recipient[1].setCaptiveInfo (captiveInfo);
            }
            else {
                recipient[1].setType (RecipientTypeCode.Signer);
            }
        }

        recipient[0] = new Recipient();
        recipient[0].setID (new PositiveInteger(envelope.getRecipientIdStringSignerOne()));
        recipient[0].setUserName (envelope.getNameSignerOne());
        recipient[0].setSignerName (envelope.getNameSignerOne());
        recipient[0].setEmail (envelope.getEmailSignerOne());
        recipient[0].setRequireIDLookup (false);
        recipient[0].setRoutingOrder (new UnsignedShort("1"));
        recipient[0].setRoleName (envelope.getRoleSignerOne());

        if (envelope.isEnvelopeSigningLocation_InPerson())	{
            recipient[0].setType (RecipientTypeCode.InPersonSigner);
            RecipientCaptiveInfo captiveInfo = new RecipientCaptiveInfo();
            captiveInfo.setClientUserId (envelope.getRecipientIdStringSignerOne());
            recipient[0].setCaptiveInfo (captiveInfo);
        }
        else {
            recipient[0].setType (RecipientTypeCode.Signer);
        }
        return recipient;
    }

    protected TemplateReference[] createTemplateReference(CreditApplicationEnvelope envelope)
    {
        TemplateReference[] templateReferences = new TemplateReference[1];
        templateReferences[0] = new TemplateReference();
        templateReferences[0].setTemplateLocation(TemplateLocationCode.Server);
        templateReferences[0].setTemplate(envelope.getTemplateID());

        TemplateReferenceRoleAssignment[] roleAssignments = new TemplateReferenceRoleAssignment[1];
        roleAssignments[0] = new TemplateReferenceRoleAssignment();
        roleAssignments[0].setRoleName("Signer 1");
        roleAssignments[0].setRecipientID(new PositiveInteger(envelope.getRecipientIdStringSignerOne()));

        TemplateReferenceFieldDataDataValue dataVales[] = new TemplateReferenceFieldDataDataValue[6];

        int i=0;
        dataVales[i] = new TemplateReferenceFieldDataDataValue ();
        dataVales[i].setTabLabel("Local Marketing Consultant");
        dataVales[i].setValue(envelope.getConsultantName());

        i++;
        dataVales[i] = new TemplateReferenceFieldDataDataValue ();
        dataVales[i].setTabLabel("LMC Phone #");
        dataVales[i].setValue(envelope.getConsultantPhone());

        i++;
        dataVales[i] = new TemplateReferenceFieldDataDataValue ();
        dataVales[i].setTabLabel("Consultant Fax #");
        dataVales[i].setValue(envelope.getConsultantFax());

        i++;
        dataVales[i] = new TemplateReferenceFieldDataDataValue ();
        dataVales[i].setTabLabel("Directory");
        dataVales[i].setValue(envelope.getDirectoryName());

        i++;
        dataVales[i] = new TemplateReferenceFieldDataDataValue ();
        dataVales[i].setTabLabel("Total Monthly Spending");
        dataVales[i].setValue(envelope.getTotalMonthlySpending());

        i++;
        dataVales[i] = new TemplateReferenceFieldDataDataValue ();
        dataVales[i].setTabLabel("Customer ID");
        dataVales[i].setValue(envelope.getCustomerId());

        TemplateReferenceFieldData templateReferenceFieldData = new TemplateReferenceFieldData();
        templateReferenceFieldData.setDataValues(dataVales);
        templateReferences[0].setFieldData(templateReferenceFieldData);
        templateReferences[0].setRoleAssignments(roleAssignments);

        return templateReferences;
    }

    protected EnvelopeInformation createCreditAppEnvelopeInformation(CreditApplicationEnvelope envelope)
    {
        EnvelopeInformation envelopeInformation = new EnvelopeInformation();

        String dateInLongFmt = new String("" + new Date().getTime());
        envelopeInformation.setTransactionID(dateInLongFmt);
        String recipentClientId = envelope.getCustomerId();
        //envelopeInformation.setAccountId(Const.DexOneAPIAccountId_NONPROD);
        //envelopeInformation.setAccountId(Const.DexOneAPIAccountId_DEV);

        String emailSubject = iLinkUtils.getMessage("DOCUSIGN_EMAIL_SUBJECT", new Object[]{recipentClientId, envelope.getBusinessName()});
        String emailBlurb = iLinkUtils.getMessage("DOCUSIGN_EMAIL_BLURB", new Object[]{});

        envelopeInformation.setSubject(envelope.getEmailSubject());
        envelopeInformation.setEmailBlurb(envelope.getEmailSubject());
        if (envelope.isEnvelopeSigningLocation_InPerson())	{
            envelopeInformation.setSigningLocation(SigningLocationCode.InPerson); //SigningLocationCode.Online
        }
        else	{
            envelopeInformation.setSigningLocation(SigningLocationCode.Online); //SigningLocationCode.Online
        }
        envelopeInformation.setAutoNavigation(true);
        return envelopeInformation;
    }

    protected Envelope createEnvelope(Recipient[] recipients, Document[] documents, Map<String, String> customParams) throws Exception {

        PositiveInteger docId = documents[0].getID();
        PositiveInteger recipientId = recipients[0].getID();

        Tab[] tabs = new Tab[6];
        AnchorTab[] anchorTab = new AnchorTab[6];

        int i = 0;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.SignHere);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString(signatureAnchorTag);
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(0.0);
        anchorTab[i].setYOffset(-5.0);
        anchorTab[i].setIgnoreIfNotPresent(true);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.DateSigned);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString(signatureDateAnchorTag);
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(0.0);
        anchorTab[i].setYOffset(-5.0);
        anchorTab[i].setIgnoreIfNotPresent(true);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.FullName);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString(fullNameAnchorTag);
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(0.0);
        anchorTab[i].setYOffset(-5.0);
        anchorTab[i].setIgnoreIfNotPresent(true);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Title);
        tabs[i].setCustomTabHeight(10);
        tabs[i].setCustomTabWidth(130);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString(titleAnchorTag);
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-20.0);
        anchorTab[i].setYOffset(-6.0);
        anchorTab[i].setIgnoreIfNotPresent(true);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setCustomTabType(CustomTabType.Email);
        //tabs[i].setValue(envelopeDetails.get("recipentEmail"));     //check if it can be pre populated
        tabs[i].setCustomTabWidth(170);
        tabs[i].setCustomTabHeight(20);
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString(emailAnchorTag);
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-25.0);
        anchorTab[i].setYOffset(-6.0);
        anchorTab[i].setIgnoreIfNotPresent(true);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.InitialHere);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString(initialHereAnchorTag);
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(13.0);
        anchorTab[i].setYOffset(4.0);
        anchorTab[i].setIgnoreIfNotPresent(true);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        Envelope envelope = new Envelope();
        envelope.setAsynchronous(false);
        envelope.setRecipients(recipients);
        envelope.setDocuments(documents);
        envelope.setTabs(tabs);

        // Adding Custom Fields to Envelop
        if (customParams != null && customParams.size() > 0) {
            CustomField fields[] = new CustomField[customParams.size()];
            int j = 0;
            for (Map.Entry<String, String> entry : customParams.entrySet()) {
                fields[j] = new CustomField();
                fields[j].setCustomFieldType(CustomFieldType.Text);
                fields[j].setName(entry.getKey());
                fields[j].setValue(entry.getValue());
                fields[j].setRequired("false");
                fields[j].setShow("true");
                j++;
            }
            envelope.setCustomFields(fields);
        }
        return envelope;
    }

    // *** CREATE ENVELOPE FOR ROP (RECORD OF PAYMENT)
    //public static Envelope createEnvelope(EnvelopeDetails envelopeDetails ) throws Exception	{
    protected Envelope createEnvelopeROP(Recipient[] recipients, Document[] documents, Map<String, String> customParams) throws Exception
    {
        //PositiveInteger docID = new PositiveInteger(envelopeDetails.getDocumentID());
        //PositiveInteger recipientID = new PositiveInteger(envelopeDetails.getRecipientIDStringSignerOne());
        PositiveInteger docId = documents[0].getID();
        PositiveInteger recipientId = recipients[0].getID();

        Tab [] tabs = new Tab[24];
        AnchorTab [] anchorTab = new AnchorTab [24];

        int i=0;
        tabs[i] = new Tab();
        tabs[i].setTabLabel("Payment Type");
        tabs[i].setName("Check");
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setCustomTabType(CustomTabType.Radio);


        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("Check1");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-9.0);
        anchorTab[i].setYOffset(-2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("CheckNumber");
        tabs[i].setName("Check Number");
        tabs[i].setCustomTabType(CustomTabType.Number);
        tabs[i].setCustomTabWidth(new Integer(200));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Check");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("Check#");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(15.0);
        anchorTab[i].setYOffset(-3.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setCustomTabType(CustomTabType.Number);
        tabs[i].setCustomTabWidth(new Integer(100));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Check");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("2Amount:$");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(20.0);
        anchorTab[i].setYOffset(-2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setTabLabel("Payment Type");
        tabs[i].setName("Credit Card");
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setCustomTabType(CustomTabType.Radio);


        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("CreditCard/DebitCard");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-9.0);
        anchorTab[i].setYOffset(0.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setTabLabel("Select One Credit Card Type");
        tabs[i].setName("American Express");
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setCustomTabType(CustomTabType.Radio);
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("AmericanExpress");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-7.0);
        anchorTab[i].setYOffset(-2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setTabLabel("Select One Credit Card Type");
        tabs[i].setName("Discover");
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setCustomTabType(CustomTabType.Radio);
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("DiscoverMaster");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-7.0);
        anchorTab[i].setYOffset(-2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setTabLabel("Select One Credit Card Type");
        tabs[i].setName("MasterCard");
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setCustomTabType(CustomTabType.Radio);
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("MasterCard");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-7.0);
        anchorTab[i].setYOffset(-2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setTabLabel("Select One Credit Card Type");
        tabs[i].setName("Visa");
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setCustomTabType(CustomTabType.Radio);

        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("Visa");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-7.0);
        anchorTab[i].setYOffset(-2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("ChargeAmountCreditCard");
        tabs[i].setName("Amount to Charge");
        tabs[i].setCustomTabType(CustomTabType.Number);
        tabs[i].setCustomTabWidth(new Integer(100));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("3ChargeAmount:$");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(37.0);
        anchorTab[i].setYOffset(-2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("NameonCard");
        tabs[i].setName("Name on Credit Card");
        tabs[i].setCustomTabType(CustomTabType.Text);
        tabs[i].setCustomTabWidth(new Integer(210));
        tabs[i].setCustomTabListItems("NameonCard;");
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("NameonCard:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(10.0);
        anchorTab[i].setYOffset(2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("CardNumber");
        tabs[i].setName("Credit Card Number");
        tabs[i].setCustomTabType(CustomTabType.Number);
        tabs[i].setCustomTabWidth(new Integer(150));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("CardNumber:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(27.0);
        anchorTab[i].setYOffset(-3.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);


        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("ExpirationDate");
        tabs[i].setName("Credit Card Expiration Date (MM/YYYY)");
        tabs[i].setCustomTabType(CustomTabType.Text);
        tabs[i].setCustomTabWidth(new Integer(60));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("ExpirationDate:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(12.0);
        anchorTab[i].setYOffset(2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("DailyLimit");
        tabs[i].setName("Credit Card Daily Limit");
        tabs[i].setCustomTabType(CustomTabType.Number);
        tabs[i].setCustomTabWidth(new Integer(60));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("DailyLimit:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(12.0);
        anchorTab[i].setYOffset(2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("BillingAddress");
        tabs[i].setName("Credit Card Billing Address");
        tabs[i].setCustomTabType(CustomTabType.Text);
        tabs[i].setCustomTabWidth(new Integer(145));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("BillingAddress:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(28.0);
        anchorTab[i].setYOffset(-3.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("BillingAddress1");
        tabs[i].setName("Credit Card Billing Address");
        tabs[i].setCustomTabType(CustomTabType.Text);
        tabs[i].setCustomTabWidth(new Integer(250));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(false);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("BillingAddress:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-2.0);
        anchorTab[i].setYOffset(5.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("BillingAddress2");
        tabs[i].setName("Credit Card Billing Address");
        tabs[i].setCustomTabType(CustomTabType.Text);
        tabs[i].setCustomTabWidth(new Integer(250));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(false);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("BillingAddress:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-2.0);
        anchorTab[i].setYOffset(13.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);


        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.SignHere);
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Credit Card");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("4Signature:X");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(30.0);
        anchorTab[i].setYOffset(1.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setTabLabel("Payment Type");
        tabs[i].setName("Direct Debit");
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setCustomTabType(CustomTabType.Radio);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("DirectDebit5");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(-9.0);
        anchorTab[i].setYOffset(-2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("ChargeAmountCreditCard");
        tabs[i].setName("Amount to Charge");
        tabs[i].setCustomTabType(CustomTabType.Number);
        tabs[i].setCustomTabWidth(new Integer(100));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Direct Debit");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("6ChargeAmount:$");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(37.0);
        anchorTab[i].setYOffset(-2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("NameonBankAccount");
        tabs[i].setName("Name on Bank Account");
        tabs[i].setCustomTabType(CustomTabType.Text);
        tabs[i].setCustomTabWidth(new Integer(190));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Direct Debit");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("NameonBankAccount:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(16.0);
        anchorTab[i].setYOffset(2.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("BankName");
        tabs[i].setName("Bank Name");
        tabs[i].setCustomTabType(CustomTabType.Text);
        tabs[i].setCustomTabWidth(new Integer(170));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Direct Debit");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("BankName:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(22.0);
        anchorTab[i].setYOffset(-3.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("ATBTransitRouting#");
        tabs[i].setName("ATB Transit Routing Number of Bank");
        tabs[i].setCustomTabType(CustomTabType.Number);
        tabs[i].setCustomTabWidth(new Integer(110));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Direct Debit");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("ATBTransitRouting#:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(40.0);
        anchorTab[i].setYOffset(-3.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.Custom);
        tabs[i].setTabLabel("BankAccount#");
        tabs[i].setName("Bank Account Number");
        tabs[i].setCustomTabType(CustomTabType.Number);
        tabs[i].setCustomTabWidth(new Integer(143));
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Direct Debit");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("BankAccount#:");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(31.0);
        anchorTab[i].setYOffset(-3.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);

        i++;
        tabs[i] = new Tab();
        tabs[i].setDocumentID(docId);
        tabs[i].setRecipientID(recipientId);
        tabs[i].setType(TabTypeCode.SignHere);
        tabs[i].setConditionalParentLabel("Payment Type");
        tabs[i].setConditionalParentValue("Direct Debit");
        tabs[i].setCustomTabRequired(true);

        anchorTab[i] = new AnchorTab();
        anchorTab[i].setAnchorTabString("7Signature:X");
        anchorTab[i].setUnit(UnitTypeCode.Mms);
        anchorTab[i].setXOffset(30.0);
        anchorTab[i].setYOffset(1.0);
        anchorTab[i].setIgnoreIfNotPresent(false);
        tabs[i].setAnchorTabItem(anchorTab[i]);


        Envelope envelope = new Envelope();
        envelope.setAsynchronous(false);
        //envelope.setAccountId(APIAccountId);
        //envelope.setSubject(envelopeDetails.getEnvelopeEmailSubject());
        //envelope.setEmailBlurb(envelopeDetails.getEnvelopeEmailContent());
        envelope.setRecipients(recipients);
        envelope.setDocuments(documents);
        envelope.setTabs(tabs);

        // Adding Custom Fields to Envelop
        if (customParams != null && customParams.size() > 0) {
            CustomField fields[] = new CustomField[customParams.size()];
            int j = 0;
            for (Map.Entry<String, String> entry : customParams.entrySet()) {
                fields[j] = new CustomField();
                fields[j].setCustomFieldType(CustomFieldType.Text);
                fields[j].setName(entry.getKey());
                fields[j].setValue(entry.getValue());
                fields[j].setRequired("false");
                fields[j].setShow("true");
                j++;
            }
            envelope.setCustomFields(fields);
        }        return envelope;
    }

    public byte[] convertFileInByteArray(String sourcePath) throws Exception {

        File file = new File(sourcePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
                // Writes len bytes from the specified byte array starting at
                // offset off to this byte array output stream.
            }
        } catch (IOException ex) {
            logger.error("################ ERROR DocuSignManager: convertFileInByteArray() -> exception occured :" + ex.getMessage());
            throw ex;
        }
        return bos.toByteArray();
    }

    public DocuSignService getDocsignService() {
        return docsignService;
    }

    public void setDocsignService(DocuSignService docsignService) {
        this.docsignService = docsignService;
    }

    public boolean isDocuTestInd() {
        return docuTestInd;
    }

    public void setDocuTestInd(boolean docuTestInd) {
        this.docuTestInd = docuTestInd;
    }

    public String getRepEmail() {
        return repEmail;
    }

    public void setRepEmail(String repEmail) {
        this.repEmail = repEmail;
    }
}
