package ilink.domain;

import ilink.utils.iLinkUtils;
import java.io.Serializable;


public class ProductDataType implements Serializable {

    private String productDisplay = "";
    private String productCode = "";
    private String productName = "";
    private String productIssueNum = "";
    private String issueRelatedInd = "";
    private String issueDate = "";
    private String issueDateDisplay = "";
    private boolean newProdInd = true;

    public String getProductDisplay() {
        return productDisplay;
    }

    public void setProductDisplay(String productDisplay) {
        this.productDisplay = productDisplay;
    }

    public String getIssueDateDisplay() {
        return issueDateDisplay;
    }

    public void setIssueDateDisplay(String issueDateDisplay) {
        this.issueDateDisplay = issueDateDisplay;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueRelatedInd() {
        return issueRelatedInd;
    }

    public void setIssueRelatedInd(String issueRelatedInd) {
        this.issueRelatedInd = issueRelatedInd;
    }

    public boolean getNewProdInd() {
        return newProdInd;
    }

    public void setNewProdInd(boolean newProdInd) {
        this.newProdInd = newProdInd;
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

    public String getProductIssueNum() {
        return productIssueNum;
    }

    public void setProductIssueNum(String productIssueNum) {
        this.productIssueNum = productIssueNum;
    }

    public String getFormatedProduct() {
        String formatedProduct = "";
        if (!iLinkUtils.isEmpty(productIssueNum) && !iLinkUtils.isEmpty(productCode)) {
            formatedProduct = productCode + "-" + productIssueNum;
        }
        return formatedProduct;
    }

    @Override
    public String toString() {
        return "{" +
                "productDisplay:\"" + productDisplay + "\"" +
                ", productCode:\"" + productCode + "\"" +
                ", productName:\"" + productName + "\"" +
                ", productIssueNum:\"" + productIssueNum + "\"" +
                ", issueRelatedInd:\"" + issueRelatedInd + "\"" +
                ", issueDate:\"" + issueDate + "\"" +
                ", issueDateDisplay:\"" + issueDateDisplay + "\"" +
                ", newProdInd:\"" + newProdInd + "\"" +
                "}";
    }
}
