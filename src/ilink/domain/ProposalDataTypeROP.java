package ilink.domain;

@SuppressWarnings("serial")
public class ProposalDataTypeROP extends ProposalDataType
{
    private String paymentType;

    public String getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
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
                ", paymentType:\"" + paymentType + "\"" +
                "}";
    }
}

