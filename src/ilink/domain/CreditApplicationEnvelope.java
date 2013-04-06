package ilink.domain;

public class CreditApplicationEnvelope
{
    private String envelopeId = null;
    double creditAppEnvelopeId;
    CreditAppEnvelopeStatus creditAppEnvelopeStatus;

    private String envelopeEmailSubject = null;
    private String envelopeEmailContent = null;
    String contactName;
    String contactEmail;
    String businessName;

    private String documentId = null;
    private String documentName = null;
    private String documentFileExt = null;
    private String documentAttachmentDesc = null;
    private String documentPath = null;


    public String transactionId = null;
    public String templateID = null;
    public String emailSubject = null;
    public String emailContent = null;

    public boolean jointSigner = false;
    public boolean envelopeSigningLocation_InPerson = true;

    public String customerId = null;
    public String consultantName = null;
    public String consultantPhone = null;
    public String consultantFax = null;
    public String directoryName = null;
    public String totalMonthlySpending = null;

    public String roleSignerOne = null;
    public int    recipientIdSignerOne = 0;
    public String nameSignerOne = null;
    public String userNameSignerOne = null;
    public String emailSignerOne = null;

    public String roleSignerTwo = null;
    public int    recipientIdSignerTwo = 0;
    public String nameSignerTwo = null;
    public String userNameSignerTwo = null;
    public String emailSignerTwo = null;

    public CreditApplicationEnvelope() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static enum CreditAppEnvelopeStatus {
        NEW, DRAFT, IN_PROCESS, COMPLETED, FAILED
    }
    public static enum DocusignEnvelopeStatus {
        NEW, SENT, IN_PROCESS, COMPLETED, FAILED
    }

    public CreditApplicationEnvelope(String envelopeEmailSubject,
                           String envelopeEmailContent, String documentId,
                           String documentName, String documentFileExt,
                           String documentAttachmentDesc, String documentPath,
                           String transactionId, String templateID, String emailSubject,
                           String emailContent, boolean jointSigner,
                           boolean envelopeSigningLocation_InPerson, String customerId,
                           String consultantName, String consultantPhone,
                           String consultantFax, String directoryName,
                           String totalMonthlySpending, String roleSignerOne,
                           int recipientIdSignerOne, String nameSignerOne,
                           String userNameSignerOne, String emailSignerOne,
                           String roleSignerTwo, int recipientIdSignerTwo,
                           String nameSignerTwo, String userNameSignerTwo,
                           String emailSignerTwo) {
        super();
        this.creditAppEnvelopeStatus = CreditAppEnvelopeStatus.NEW;
        this.envelopeEmailSubject = envelopeEmailSubject;
        this.envelopeEmailContent = envelopeEmailContent;
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentFileExt = documentFileExt;
        this.documentAttachmentDesc = documentAttachmentDesc;
        this.documentPath = documentPath;
        this.transactionId = transactionId;
        this.templateID = templateID;
        this.emailSubject = emailSubject;
        this.emailContent = emailContent;
        this.jointSigner = jointSigner;
        this.envelopeSigningLocation_InPerson = envelopeSigningLocation_InPerson;
        this.customerId = customerId;
        this.consultantName = consultantName;
        this.consultantPhone = consultantPhone;
        this.consultantFax = consultantFax;
        this.directoryName = directoryName;
        this.totalMonthlySpending = totalMonthlySpending;
        this.roleSignerOne = roleSignerOne;
        this.recipientIdSignerOne = recipientIdSignerOne;
        this.nameSignerOne = nameSignerOne;
        this.userNameSignerOne = userNameSignerOne;
        this.emailSignerOne = emailSignerOne;
        this.roleSignerTwo = roleSignerTwo;
        this.recipientIdSignerTwo = recipientIdSignerTwo;
        this.nameSignerTwo = nameSignerTwo;
        this.userNameSignerTwo = userNameSignerTwo;
        this.emailSignerTwo = emailSignerTwo;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public double getCreditAppEnvelopeId() {
        return creditAppEnvelopeId;
    }

    public void setCreditAppEnvelopeId(double creditAppEnvelopeId) {
        this.creditAppEnvelopeId = creditAppEnvelopeId;
    }

    /**
     * @return the envelopeEmailSubject
     */
    public String getEnvelopeEmailSubject() {
        return envelopeEmailSubject;
    }

    /**
     * @param envelopeEmailSubject the envelopeEmailSubject to set
     */
    public void setEnvelopeEmailSubject(String envelopeEmailSubject) {
        this.envelopeEmailSubject = envelopeEmailSubject;
    }

    /**
     * @return the envelopeEmailContent
     */
    public String getEnvelopeEmailContent() {
        return envelopeEmailContent;
    }

    /**
     * @param envelopeEmailContent the envelopeEmailContent to set
     */
    public void setEnvelopeEmailContent(String envelopeEmailContent) {
        this.envelopeEmailContent = envelopeEmailContent;
    }

    /**
     * @return the documentId
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * @param documentId the documentId to set
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * @return the documentName
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * @param documentName the documentName to set
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * @return the documentFileExt
     */
    public String getDocumentFileExt() {
        return documentFileExt;
    }

    /**
     * @param documentFileExt the documentFileExt to set
     */
    public void setDocumentFileExt(String documentFileExt) {
        this.documentFileExt = documentFileExt;
    }

    /**
     * @return the documentAttachmentDesc
     */
    public String getDocumentAttachmentDesc() {
        return documentAttachmentDesc;
    }

    /**
     * @param documentAttachmentDesc the documentAttachmentDesc to set
     */
    public void setDocumentAttachmentDesc(String documentAttachmentDesc) {
        this.documentAttachmentDesc = documentAttachmentDesc;
    }

    /**
     * @return the documentPath
     */
    public String getDocumentPath() {
        return documentPath;
    }

    /**
     * @param documentPath the documentPath to set
     */
    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    /**
     * @return the transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the templateID
     */
    public String getTemplateID() {
        return templateID;
    }

    /**
     * @param templateID the templateId to set
     */
    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    /**
     * @return the emailSubject
     */
    public String getEmailSubject() {
        return emailSubject;
    }

    /**
     * @param emailSubject the emailSubject to set
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    /**
     * @return the emailContent
     */
    public String getEmailContent() {
        return emailContent;
    }

    /**
     * @param emailContent the emailContent to set
     */
    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    /**
     * @return the jointSigner
     */
    public boolean isJointSigner() {
        return jointSigner;
    }

    /**
     * @param jointSigner the jointSigner to set
     */
    public void setJointSigner(boolean jointSigner) {
        this.jointSigner = jointSigner;
    }

    /**
     * @return the envelopeSigningLocation_InPerson
     */
    public boolean isEnvelopeSigningLocation_InPerson() {
        return envelopeSigningLocation_InPerson;
    }

    /**
     * @param envelopeSigningLocation_InPerson the envelopeSigningLocation_InPerson to set
     */
    public void setEnvelopeSigningLocation_InPerson(
            boolean envelopeSigningLocation_InPerson) {
        this.envelopeSigningLocation_InPerson = envelopeSigningLocation_InPerson;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the consultantName
     */
    public String getConsultantName() {
        return consultantName;
    }

    /**
     * @param consultantName the consultantName to set
     */
    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    /**
     * @return the consultantPhone
     */
    public String getConsultantPhone() {
        return consultantPhone;
    }

    /**
     * @param consultantPhone the consultantPhone to set
     */
    public void setConsultantPhone(String consultantPhone) {
        this.consultantPhone = consultantPhone;
    }

    /**
     * @return the consultantFax
     */
    public String getConsultantFax() {
        return consultantFax;
    }

    /**
     * @param consultantFax the consultantFax to set
     */
    public void setConsultantFax(String consultantFax) {
        this.consultantFax = consultantFax;
    }

    /**
     * @return the directoryName
     */
    public String getDirectoryName() {
        return directoryName;
    }

    /**
     * @param directoryName the directoryName to set
     */
    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    /**
     * @return the totalMonthlySpending
     */
    public String getTotalMonthlySpending() {
        return totalMonthlySpending;
    }

    /**
     * @param totalMonthlySpending the totalMonthlySpending to set
     */
    public void setTotalMonthlySpending(String totalMonthlySpending) {
        this.totalMonthlySpending = totalMonthlySpending;
    }

    /**
     * @return the roleSignerOne
     */
    public String getRoleSignerOne() {
        return roleSignerOne;
    }

    /**
     * @param roleSignerOne the roleSignerOne to set
     */
    public void setRoleSignerOne(String roleSignerOne) {
        this.roleSignerOne = roleSignerOne;
    }

    /**
     * @return the recipientIdSignerOne
     */
    public int getRecipientIdSignerOne() {
        return recipientIdSignerOne;
    }


    /**
     * @return the recipientIdSignerOne
     */
    public String getRecipientIdStringSignerOne() {
        return String.valueOf(recipientIdSignerOne);
    }

    /**
     * @param recipientIdSignerOne the recipientIdSignerOne to set
     */
    public void setRecipientIdSignerOne(int recipientIdSignerOne) {
        this.recipientIdSignerOne = recipientIdSignerOne;
    }

    /**
     * @return the nameSignerOne
     */
    public String getNameSignerOne() {
        return nameSignerOne;
    }

    /**
     * @param nameSignerOne the nameSignerOne to set
     */
    public void setNameSignerOne(String nameSignerOne) {
        this.nameSignerOne = nameSignerOne;
    }

    /**
     * @return the userNameSignerOne
     */
    public String getUserNameSignerOne() {
        return userNameSignerOne;
    }

    /**
     * @param userNameSignerOne the userNameSignerOne to set
     */
    public void setUserNameSignerOne(String userNameSignerOne) {
        this.userNameSignerOne = userNameSignerOne;
    }

    /**
     * @return the emailSignerOne
     */
    public String getEmailSignerOne() {
        return emailSignerOne;
    }

    /**
     * @param emailSignerOne the emailSignerOne to set
     */
    public void setEmailSignerOne(String emailSignerOne) {
        this.emailSignerOne = emailSignerOne;
    }

    /**
     * @return the roleSignerTwo
     */
    public String getRoleSignerTwo() {
        return roleSignerTwo;
    }

    /**
     * @param roleSignerTwo the roleSignerTwo to set
     */
    public void setRoleSignerTwo(String roleSignerTwo) {
        this.roleSignerTwo = roleSignerTwo;
    }

    /**
     * @return the recipientIdSignerTwo
     */
    public int getRecipientIdSignerTwo() {
        return recipientIdSignerTwo;
    }

    /**
     * @return the recipientIdSignerOne
     */
    public String getRecipientIdStringSignerTwo() {
        return String.valueOf(recipientIdSignerTwo);
    }

    /**
     * @param recipientIdSignerTwo the recipientIdSignerTwo to set
     */
    public void setRecipientIdSignerTwo(int recipientIdSignerTwo) {
        this.recipientIdSignerTwo = recipientIdSignerTwo;
    }

    /**
     * @return the nameSignerTwo
     */
    public String getNameSignerTwo() {
        return nameSignerTwo;
    }

    /**
     * @param nameSignerTwo the nameSignerTwo to set
     */
    public void setNameSignerTwo(String nameSignerTwo) {
        this.nameSignerTwo = nameSignerTwo;
    }

    /**
     * @return the userNameSignerTwo
     */
    public String getUserNameSignerTwo() {
        return userNameSignerTwo;
    }

    /**
     * @param userNameSignerTwo the userNameSignerTwo to set
     */
    public void setUserNameSignerTwo(String userNameSignerTwo) {
        this.userNameSignerTwo = userNameSignerTwo;
    }

    /**
     * @return the emailSignerTwo
     */
    public String getEmailSignerTwo() {
        return emailSignerTwo;
    }

    /**
     * @param emailSignerTwo the emailSignerTwo to set
     */
    public void setEmailSignerTwo(String emailSignerTwo) {
        this.emailSignerTwo = emailSignerTwo;
    }

    @Override
    public String toString()
    {
        return "CreditApplicationEnvelope{" +
                "envelopeId:\"" + envelopeId + "\"" +
                ", envelopeEmailSubject:\"" + envelopeEmailSubject + "\"" +
                ", envelopeEmailContent:\"" + envelopeEmailContent + "\"" +
                ", contactName:\"" + contactName + "\"" +
                ", contactEmail:\"" + contactEmail + "\"" +
                ", documentId:\"" + documentId + "\"" +
                ", documentName:\"" + documentName + "\"" +
                ", documentFileExt:\"" + documentFileExt + "\"" +
                ", documentAttachmentDesc:\"" + documentAttachmentDesc + "\"" +
                ", documentPath:\"" + documentPath + "\"" +
                ", transactionId:\"" + transactionId + "\"" +
                ", templateID:\"" + templateID + "\"" +
                ", emailSubject:\"" + emailSubject + "\"" +
                ", emailContent:\"" + emailContent + "\"" +
                ", jointSigner:\"" + jointSigner + "\"" +
                ", envelopeSigningLocation_InPerson:\"" + envelopeSigningLocation_InPerson + "\"" +
                ", customerId:\"" + customerId + "\"" +
                ", consultantName:\"" + consultantName + "\"" +
                ", consultantPhone:\"" + consultantPhone + "\"" +
                ", consultantFax:\"" + consultantFax + "\"" +
                ", directoryName:\"" + directoryName + "\"" +
                ", totalMonthlySpending:\"" + totalMonthlySpending + "\"" +
                ", roleSignerOne:\"" + roleSignerOne + "\"" +
                ", recipientIdSignerOne:\"" + recipientIdSignerOne + "\"" +
                ", nameSignerOne:\"" + nameSignerOne + "\"" +
                ", userNameSignerOne:\"" + userNameSignerOne + "\"" +
                ", emailSignerOne:\"" + emailSignerOne + "\"" +
                ", roleSignerTwo:\"" + roleSignerTwo + "\"" +
                ", recipientIdSignerTwo:\"" + recipientIdSignerTwo +  "\"" +
                ", nameSignerTwo:\"" + nameSignerTwo + "\"" +
                ", userNameSignerTwo:\"" + userNameSignerTwo + "\"" +
                ", emailSignerTwo:\"" + emailSignerTwo + "\"" +
                '}';
    }
}
