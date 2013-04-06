package ilink.domain;


public class EsignProduct {

    double esignEnvelopeId;
    double esignBAccountId;
    String productCode;
    String productName;
    String issueDate;
    String startDate;
    String endDate;
    double monthlyBilling;
    String issueRelatedInd;
    String productSource;
    String repId;
    String status;
    int numOfMonths;
    double amountToApply;

    public EsignProduct(){}
    public EsignProduct(double esignEnvelopeId, double esignBAccountId, String productCode,
                        String productName, String issueDate, String startDate,
                        String endDate, double monthlyBilling, String issueRelatedInd,
                        String productSource, String repId, String status) {
        this.esignEnvelopeId = esignEnvelopeId;
        this.esignBAccountId = esignBAccountId;
        this.productCode = productCode;
        this.productName = productName;
        this.issueDate = issueDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyBilling = monthlyBilling;
        this.issueRelatedInd = issueRelatedInd;
        this.productSource = productSource;
        this.repId = repId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "esignEnvelopeId:\"" + esignEnvelopeId + "\"" +
                ", esignBAccountId:\"" + esignBAccountId + "\"" +
                ", productCode:\"" + productCode + "\"" +
                ", productName:\"" + productName + "\"" +
                ", issueDate:\"" + issueDate + "\"" +
                ", startDate:\"" + startDate + "\"" +
                ", endDate:\"" + endDate + "\"" +
                ", monthlyBilling:\"" + monthlyBilling + "\"" +
                ", issueRelatedInd:\"" + issueRelatedInd + "\"" +
                ", productSource:\"" + productSource + "\"" +
                ", repId:\"" + repId + "\"" +
                ", status:\"" + status + "\"" +
                ", numOfMonths:\"" + numOfMonths + "\"" +
                ", amountToApply:\"" + amountToApply + "\"" +
                "}";

    }

    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }

    public double getEsignEnvelopeId() {
        return esignEnvelopeId;
    }

    public void setEsignEnvelopeId(double esignEnvelopeId) {
        this.esignEnvelopeId = esignEnvelopeId;
    }

    public double getEsignBAccountId() {
        return esignBAccountId;
    }

    public void setEsignBAccountId(double esignBAccountId) {
        this.esignBAccountId = esignBAccountId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getMonthlyBilling() {
        return monthlyBilling;
    }

    public void setMonthlyBilling(double monthlyBilling) {
        this.monthlyBilling = monthlyBilling;
    }

    public String getIssueRelatedInd() {
        return issueRelatedInd;
    }

    public void setIssueRelatedInd(String issueRelatedInd) {
        this.issueRelatedInd = issueRelatedInd;
    }

    public String getProductSource() {
        return productSource;
    }

    public void setProductSource(String productSource) {
        this.productSource = productSource;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumOfMonths()
    {
        return numOfMonths;
    }

    public void setNumOfMonths(int numOfMonths)
    {
        this.numOfMonths = numOfMonths;
    }

    public double getAmountToApply()
    {
        return amountToApply;
    }

    public void setAmountToApply(double amountToApply)
    {
        this.amountToApply = amountToApply;
    }
}
