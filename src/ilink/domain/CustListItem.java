package ilink.domain;

import java.io.Serializable;


public class CustListItem implements Serializable {
    
    private long customerId;
    private String customerName;
    private String contactName;
    private String formattedPhone;
    private String address;
    private String bots;
    private String nisd;
    private String prospectCode;


    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getBots() {
        return bots;
    }

    public void setBots(String bots) {
        this.bots = bots;
    }

    public String getNisd() {
        return nisd;
    }

    public void setNisd(String nisd) {
        this.nisd = nisd;
    }

    public String getProspectCode() {
        return prospectCode;
    }

    public void setProspectCode(String prospectCode) {
        this.prospectCode = prospectCode;
    }

    public String getFormattedPhone() {
        return formattedPhone;
    }

    public void setFormattedPhone(String formattedPhone) {
        this.formattedPhone = formattedPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long custId) {
        this.customerId = custId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String custName) {
        this.customerName = custName;
    }

    @Override
    public String toString() {
        return "{" +
                "customerId:\"" + customerId + "\"" +
                ", customerName:\"" + customerName + "\"" +
                ", contactName:\"" + contactName + "\"" +
                ", formattedPhone:\"" + formattedPhone + "\"" +
                ", address:\"" + address + "\"" +
                ", bots:\"" + bots + "\"" +
                ", nisd:\"" + nisd + "\"" +
                ", prospectCode:\"" + prospectCode + "\"" +
                "}";
    }
}