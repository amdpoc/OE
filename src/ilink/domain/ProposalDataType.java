package ilink.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProposalDataType implements Serializable {

	protected long customerId;
	protected String businessName;
	protected String billingAccountId;
    protected String billingAccountName;
    protected String billingPhoneNo;
	protected String billingAddress;
    protected String productName;
    protected String productCode;
    protected String productIssueDate;
    protected String productIssueDateDisplay;
    protected String barcodePhoneNo;
    protected String productBillingMonthly;
    protected String productStatus;
    protected String repId;
    protected String internetStartDate;
    protected String internetEndDate;
    protected String emailAddress;
    protected String issueRelatedInd;
    protected String productIssueNumber;

    public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessName() {
		return businessName;
	}

  	public void setBillingAccountId(String billingAccountId) {
		this.billingAccountId = billingAccountId;
	}

	public String getBillingAccountId() {
		return billingAccountId;
	}

	public void setBillingAccountName(String billingAccountName) {
		this.billingAccountName = billingAccountName;
	}

	public String getBillingAccountName() {
		return billingAccountName;
	}

	public void setBillingPhoneNo(String billingPhoneNo) {
		this.billingPhoneNo = billingPhoneNo;
	}

	public String getBillingPhoneNo() {
		return billingPhoneNo;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductCode() {
		return productCode;
	}

    public String getProductIssueDateDisplay() {
        return productIssueDateDisplay;
    }

    public void setProductIssueDateDisplay(String productIssueDateDisplay) {
        this.productIssueDateDisplay = productIssueDateDisplay;
    }

    public void setProductIssueDate(String productIssueDate) {
		this.productIssueDate = productIssueDate;
	}

	public void setBarcodePhoneNo(String barcodePhoneNo) {
		this.barcodePhoneNo = barcodePhoneNo;
	}

	public String getBarcodePhoneNo() {
		return barcodePhoneNo;
	}

	public String getProductIssueDate() {
		return productIssueDate;
	}

	public void setProductBillingMonthly(String productBillingMonthly) {
		this.productBillingMonthly = productBillingMonthly;
	}

	public String getProductBillingMonthly() {
		return productBillingMonthly;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setRepId(String repId) {
		this.repId = repId;
	}

	public String getRepId() {
		return repId;
	}

	public void setInternetStartDate(String internetStartDate) {
		this.internetStartDate = internetStartDate;
	}

	public String getInternetStartDate() {
		return internetStartDate;
	}

	public void setInternetEndDate(String internetEndDate) {
		this.internetEndDate = internetEndDate;
	}

	public String getInternetEndDate() {
		return internetEndDate;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setIssueRelatedInd(String issueRelatedInd) {
		this.issueRelatedInd = issueRelatedInd;
	}

	public String getIssueRelatedInd() {
		return issueRelatedInd;
	}

	public void setProductIssueNumber(String productIssueNumber) {
		this.productIssueNumber = productIssueNumber;
	}

	public String getProductIssueNumber() {
		return productIssueNumber;
	}

    @Override
    public String toString() {
        return "{" +
                "customerId:\"" + customerId + "\"" +
                ", businessName:\"" + businessName + "\"" +
                ", billingAccountId:\"" + billingAccountId + "\"" +
                ", billingAccountName:\"" + billingAccountName + "\"" +
                ", billingPhoneNo:\"" + billingPhoneNo + "\"" +
                ", billingAddress:\"" + billingAddress + "\"" +
                ", productName:\"" + productName + "\"" +
                ", productCode:\"" + productCode + "\"" +
                ", productIssueDate:\"" + productIssueDate + "\"" +
                ", productIssueDateDisplay:\"" + productIssueDateDisplay + "\"" +
                ", barcodePhoneNo:\"" + barcodePhoneNo + "\"" +
                ", productBillingMonthly:\"" + productBillingMonthly + "\"" +
                ", productStatus:\"" + productStatus + "\"" +
                ", repId:\"" + repId + "\"" +
                ", internetStartDate:\"" + internetStartDate + "\"" +
                ", internetEndDate:\"" + internetEndDate + "\"" +
                ", emailAddress:\"" + emailAddress + "\"" +
                ", issueRelatedInd:\"" + issueRelatedInd + "\"" +
                ", productIssueNumber:\"" + productIssueNumber + "\"" +
                "}";
    }
}

