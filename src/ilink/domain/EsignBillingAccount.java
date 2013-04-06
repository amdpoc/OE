package ilink.domain;

import java.util.List;
import java.util.ArrayList;

public class EsignBillingAccount {
    double esignEnvelopeId;
    double esignBAccountId;
    double finBAccountId;
    String baAccountName;
    String baTn;
    String baCop;
    String baLineNo;
    String baAddress;
    String baSource;
    String baContactName;
    String baContactTitle;
    String baContactEmail;
    String signatureDate;
    String baRepId;
    String paymentType;
    double amountToApply;
    List<EsignProduct> baProductList;
    public EsignBillingAccount(){this.baProductList = new ArrayList<EsignProduct>();}
    public EsignBillingAccount(double esignEnvelopeId, double finBAccountId, String baAccountName,
                               String baTn, String baCop, String baLineNo, String baSource,
                               String baAddress, String baContactName, String baContactEmail,
                               String baContactTitle, String signatureDate, String baRepId, String paymentType, double amountToApply) {
        this.esignEnvelopeId = esignEnvelopeId;
        this.finBAccountId = finBAccountId;
        this.baAccountName = baAccountName;
        this.baTn = baTn;
        this.baCop = baCop;
        this.baLineNo = baLineNo;
        this.baSource = baSource;
        this.baAddress = baAddress;
        this.baContactName = baContactName;
        this.baContactEmail = baContactEmail;
        this.baContactTitle = baContactTitle;
        this.signatureDate = signatureDate;
        this.baRepId = baRepId;
        this.paymentType = paymentType;
        this.amountToApply = amountToApply;
        this.baProductList = new ArrayList<EsignProduct>();
    }

    @Override
    public String toString() {
        String result = "{" +
                "esignEnvelopeId:\"" + esignEnvelopeId + "\"" +
                ", esignBAccountId:\"" + esignBAccountId + "\"" +
                ", finBAccountId:\"" + finBAccountId + "\"" +
                ", baAccountName:\"" + baAccountName + "\"" +
                ", baTn:\"" + baTn + "\"" +
                ", baCop:\"" + baCop + "\"" +
                ", baLineNo:\"" + baLineNo + "\"" +
                ", baAddress:\"" + baAddress + "\"" +
                ", baSource:\"" + baSource + "\"" +
                ", baContactName:\"" + baContactName + "\"" +
                ", baContactTitle:\"" + baContactTitle + "\"" +
                ", baContactEmail:\"" + baContactEmail + "\"" +
                ", signatureDate:\"" + signatureDate + "\"" +
                ", baRepId:\"" + baRepId + "\"" +
                ", paymentType:\"" + paymentType + "\"" +
                ", amountToApply:\"" + amountToApply + "\"" +
                ", baProductList:[";
        if (baProductList != null) {
            for (EsignProduct it : baProductList) {
                result += it.toString();
            }
        }
        result += "]}";
        return result;
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

    public double getFinBAccountId() {
        return finBAccountId;
    }

    public void setFinBAccountId(double finBAccountId) {
        this.finBAccountId = finBAccountId;
    }

    public String getBaAccountName() {
        return baAccountName;
    }

    public void setBaAccountName(String baAccountName) {
        this.baAccountName = baAccountName;
    }

    public String getBaTn() {
        return baTn;
    }

    public void setBaTn(String baTn) {
        this.baTn = baTn;
    }

    public String getBaCop() {
        return baCop;
    }

    public void setBaCop(String baCop) {
        this.baCop = baCop;
    }

    public String getBaLineNo() {
        return baLineNo;
    }

    public void setBaLineNo(String baLineNo) {
        this.baLineNo = baLineNo;
    }

    public String getBaAddress() {
        return baAddress;
    }

    public void setBaAddress(String baAddress) {
        this.baAddress = baAddress;
    }

    public String getBaSource() {
        return baSource;
    }

    public void setBaSource(String baSource) {
        this.baSource = baSource;
    }

    public String getBaContactName() {
        return baContactName;
    }

    public void setBaContactName(String baContactName) {
        this.baContactName = baContactName;
    }

    public String getBaContactTitle() {
        return baContactTitle;
    }

    public void setBaContactTitle(String baContactTitle) {
        this.baContactTitle = baContactTitle;
    }

    public String getBaContactEmail() {
        return baContactEmail;
    }

    public void setBaContactEmail(String baContactEmail) {
        this.baContactEmail = baContactEmail;
    }

    public String getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(String signatureDate) {
        this.signatureDate = signatureDate;
    }

    public void setSignatureDateAll(String signatureDate) {
        this.signatureDate = signatureDate;   
    }

    public String getBaRepId() {
        return baRepId;
    }

    public void setBaRepId(String baRepId) {
        this.baRepId = baRepId;
    }
    public void setBaRepIdAll(String baRepId) {
        this.baRepId = baRepId;
          for(EsignProduct it: baProductList){
             it.setRepId(baRepId);
         }        
    }

    public List<EsignProduct> getBaProductList() {
        return baProductList;
    }

    public void setBaProductList(List<EsignProduct> baProductList) {
        this.baProductList = baProductList;
    }

    public void addBaProduct(EsignProduct product) {
        this.baProductList.add(product);
    }

    public String getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
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
