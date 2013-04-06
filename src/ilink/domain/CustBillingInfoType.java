package ilink.domain;

import java.io.Serializable;

public class CustBillingInfoType implements Serializable {

    private long customerId;
    private String contactName;
    private String contactTitle;
    private String contactPhoneNPA;
    private String contactPhoneCOP;
    private String contactPhoneLineNo;
    private String contactFaxNPA;
    private String contactFaxCOP;
    private String contactFaxLineNo;
    private String street;
    private String city;
    private String state;
    private String zipFive;
    private String zipFour;
    private String nisdAmt;
    private String botsAmt;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getContactName()
    {
        return contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    public String getContactFaxLineNo()
    {
        return contactFaxLineNo;
    }

    public void setContactFaxLineNo(String contactFaxLineNo)
    {
        this.contactFaxLineNo = contactFaxLineNo;
    }

    public String getContactFaxCOP()
    {
        return contactFaxCOP;
    }

    public void setContactFaxCOP(String contactFaxCOP)
    {
        this.contactFaxCOP = contactFaxCOP;
    }

    public String getContactFaxNPA()
    {
        return contactFaxNPA;
    }

    public void setContactFaxNPA(String contactFaxNPA)
    {
        this.contactFaxNPA = contactFaxNPA;
    }

    public String getContactPhoneLineNo()
    {
        return contactPhoneLineNo;
    }

    public void setContactPhoneLineNo(String contactPhoneLineNo)
    {
        this.contactPhoneLineNo = contactPhoneLineNo;
    }

    public String getContactPhoneCOP()
    {
        return contactPhoneCOP;
    }

    public void setContactPhoneCOP(String contactPhoneCOP)
    {
        this.contactPhoneCOP = contactPhoneCOP;
    }

    public String getContactPhoneNPA()
    {
        return contactPhoneNPA;
    }

    public void setContactPhoneNPA(String contactPhoneNPA)
    {
        this.contactPhoneNPA = contactPhoneNPA;
    }

    public String getContactTitle()
    {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle)
    {
        this.contactTitle = contactTitle;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
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

    public String getNisdAmt() {
        return nisdAmt;
    }

    public void setNisdAmt(String nisdAmt) {
        this.nisdAmt = nisdAmt;
    }

    public String getBotsAmt() {
        return botsAmt;
    }

    public void setBotsAmt(String botsAmt) {
        this.botsAmt = botsAmt;
    }


    @Override
    public String toString() {
        return "{" +
                "customerId:\"" + customerId + "\"" +
                ", contactName:\"" + contactName + "\"" +
                ", contactTitle:\"" + contactTitle + "\"" +
                ", contactPhoneNPA:\"" + contactPhoneNPA + "\"" +
                ", contactPhoneCOP:\"" + contactPhoneCOP + "\"" +
                ", contactPhoneLineNo:\"" + contactPhoneLineNo + "\"" +
                ", contactFaxNPA:\"" + contactFaxNPA + "\"" +
                ", contactFaxCOP:\"" + contactFaxCOP + "\"" +
                ", contactFaxLineNo:\"" + contactFaxLineNo + "\"" +
                ", street:\"" + street + "\"" +
                ", city:\"" + city + "\"" +
                ", state:\"" + state + "\"" +
                ", zipFive:\"" + zipFive + "\"" +
                ", zipFour:\"" + zipFour + "\"" +
                ", nisdAmt:\"" + nisdAmt + "\"" +
                ", botsAmt:\"" + botsAmt + "\"" +
                "}";
    }
}
