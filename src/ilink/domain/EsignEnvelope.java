package ilink.domain;

import ilink.utils.iLinkPropertiesLoader;
import ilink.utils.iLinkUtils;

import java.util.List;
import java.util.ArrayList;


public class EsignEnvelope {

    double esignEnvelopeId;
    String esignEnvelopeName;
    EsignEnvelopeStatus esignEnvelopeStatus;
    String customerId;
    String businessName;
    String docusignEnvelopeId;
    String docusignEnvelopeStatus;
    String contactName;
    String contactTitle;
    String contactEmail;
    boolean inPersonInd = true;
    String signatureDate;
    String proposalId;
    String pdfRequestId;
    String generatedPdfFile;
    String repId;
    String envelopeType;
    String streetAddress; 
    String city; 
    String stateAbbr;
    String zipFive;
    String zipFour;
    String newRenewalInd;
    List<EsignBillingAccount> billingAccounts;
    String action = (String) iLinkPropertiesLoader.getApplicationProperty("pdfAction");
    String envelopeKey = (String) iLinkPropertiesLoader.getApplicationProperty("envelopeKey");
    String templateCode = (String) iLinkPropertiesLoader.getApplicationProperty("templateCode");

    public static enum EsignEnvelopeStatus {
        NEW, DRAFT, IN_PROCESS, COMPLETED, FAILED
    }
    public static enum DocusignEnvelopeStatus {
        NEW, SENT, IN_PROCESS, COMPLETED, FAILED
    }

    public EsignEnvelope() {
        this.billingAccounts = new ArrayList<EsignBillingAccount>();
    }

    public EsignEnvelope(String esignEnvelopeName, String customerId, String businessName, String contactName,
                         String contactTitle, String contactEmail, String signatureDate, String proposalId, String repId) {
        esignEnvelopeStatus = EsignEnvelopeStatus.NEW;
        this.esignEnvelopeName = esignEnvelopeName;
        this.customerId = customerId;
        this.businessName = businessName;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.contactEmail = contactEmail;
        this.signatureDate = signatureDate;
        this.proposalId = proposalId;
        this.repId = repId;
        this.billingAccounts = new ArrayList<EsignBillingAccount>();
    }

    public double getEsignEnvelopeId() {
        return esignEnvelopeId;
    }

    public void setEsignEnvelopeId(double esignEnvelopeId) {
        this.esignEnvelopeId = esignEnvelopeId;
    }

    public String getEsignEnvelopeName() {
        return esignEnvelopeName;
    }

    public void setEsignEnvelopeName(String esignEnvelopeName) {
        this.esignEnvelopeName = esignEnvelopeName;
    }

    public String getEsignEnvelopeStatus() {
        return esignEnvelopeStatus.toString();
    }

    public void setEsignEnvelopeStatus(EsignEnvelopeStatus esignEnvelopeStatus) {
        this.esignEnvelopeStatus = esignEnvelopeStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDocusignEnvelopeStatus() {
        return docusignEnvelopeStatus;
    }

    public void setDocusignEnvelopeStatus(String docusignEnvelopeStatus) {
        this.docusignEnvelopeStatus = docusignEnvelopeStatus;
    }

    public String getDocusignEnvelopeId() {
        return docusignEnvelopeId;
    }

    public void setDocusignEnvelopeId(String docusignEnvelopeId) {
        this.docusignEnvelopeId = docusignEnvelopeId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public boolean isInPersonInd() {
        return inPersonInd;
    }

    public void setInPersonInd(boolean inPersonInd) {
        this.inPersonInd = inPersonInd;
    }

    public String getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(String signatureDate) {
        this.signatureDate = signatureDate;
    }

    public void setSignatureDateAll(String signatureDate) {
        this.signatureDate = signatureDate;
        for (EsignBillingAccount it : billingAccounts) {
            it.setSignatureDateAll(signatureDate);
        }
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getGeneratedPdfFile() {
        return generatedPdfFile;
    }

    public void setGeneratedPdfFile(String generatedPdfFile) {
        this.generatedPdfFile = generatedPdfFile;
    }

    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }


    public String getEnvelopeType()
    {
        return envelopeType;
    }

    public void setEnvelopeType(String envelopeType)
    {
        this.envelopeType = envelopeType;
    }

    public String getStreetAddress()
    {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress)
    {
        this.streetAddress = streetAddress;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getStateAbbr()
    {
        return stateAbbr;
    }

    public void setStateAbbr(String stateAbbr)
    {
        this.stateAbbr = stateAbbr;
    }

    public String getZipFive()
    {
        return zipFive;
    }

    public void setZipFive(String zipFive)
    {
        this.zipFive = zipFive;
    }

    public String getZipFour()
    {
        return zipFour;
    }

    public void setZipFour(String zipFour)
    {
        this.zipFour = zipFour;
    }

    public String getNewRenewalInd()
    {
        return newRenewalInd;
    }

    public void setNewRenewalInd(String newRenewalInd)
    {
        this.newRenewalInd = newRenewalInd;
    }

    public void setRepIdAll(String repId) {
        this.repId = repId;
        for (EsignBillingAccount it : billingAccounts) {
            it.setBaRepIdAll(repId);
        }
    }

    public List<EsignBillingAccount> getBillingAccounts() {
        return billingAccounts;
    }

    public void setBillingAccounts(List<EsignBillingAccount> billingAccounts) {
        this.billingAccounts = billingAccounts;
    }

    public void addBillingAccount(EsignBillingAccount billingAccount) {
        this.billingAccounts.add(billingAccount);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEnvelopeKey() {
        return envelopeKey;
    }

    public void setEnvelopeKey(String envelopeKey) {
        this.envelopeKey = envelopeKey;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getPdfRequestId() {
        return pdfRequestId;
    }

    public void setPdfRequestId(String pdfRequestId) {
        this.pdfRequestId = pdfRequestId;
    }

    public String getPhoneNumber() {
        String phone = "";
        if (this.billingAccounts != null && !this.billingAccounts.isEmpty()) {
            EsignBillingAccount firstAccount = this.billingAccounts.get(0);
            if (!iLinkUtils.isEmpty(firstAccount.getBaTn())) {
                phone = firstAccount.getBaTn() + "-" + firstAccount.getBaCop() + "-" + firstAccount.getBaLineNo();
            }
        }
        return phone;
    }

    @Override
    public String toString() {
        String result = "{" +
            "esignEnvelopeId:\"" + esignEnvelopeId + "\"" +
            ", esignEnvelopeName:\"" + esignEnvelopeName + "\"" +
            ", esignEnvelopeStatus:\"" + esignEnvelopeStatus + "\"" +
            ", customerId:\"" + customerId + "\"" +
            ", businessName:\"" + businessName + "\"" +
            ", docusignEnvelopeId:\"" + docusignEnvelopeId + "\"" +
            ", docusignEnvelopeStatus:\"" + docusignEnvelopeStatus + "\"" +
            ", contactName:\"" + contactName + "\"" +
            ", contactTitle:\"" + contactTitle + "\"" +
            ", contactEmail:\"" + contactEmail + "\"" +
            ", signatureDate:\"" + signatureDate + "\"" +
            ", proposalId:\"" + proposalId + "\"" +
            ", generatedPdfFile:\"" + generatedPdfFile + "\"" +
            ", repId:\"" + repId + "\"" +
            ", envelopeType:\"" + envelopeType + "\"" +
            ", streetAddress:\"" + streetAddress + "\"" +
            ", city:\"" + city + "\"" +
            ", stateAbbr:\"" + stateAbbr + "\"" +
            ", zipFive:\"" + zipFive + "\"" +
            ", zipFour:\"" + zipFour+ "\"" +
            ", newRenewalInd:\"" + newRenewalInd+ "\"" +
            ", billingAccounts:[";
        if (billingAccounts != null) {
            for (EsignBillingAccount it : billingAccounts) {
                result += it.toString();
            }
        }
        result += "]}";
        return result;
    }
}
