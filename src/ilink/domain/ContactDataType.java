package ilink.domain;

import java.io.Serializable;

public class ContactDataType implements Serializable {

    private String contactName = "";
    private String contactTitle = "";
    private String contactAddressId = "";
    private String contactNpa = "";
    private String contactCop = "";
    private String contactLineNo = "";
    private String contactCellNpa = "";
    private String contactCellCop = "";
    private String contactCellLineNo = "";
    private String contactEmailAddress = "";
    private String contactInternetAddress = "";

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

    public String getContactAddressId() {
        return contactAddressId;
    }

    public void setContactAddressId(String contactAddressId) {
        this.contactAddressId = contactAddressId;
    }

    public String getContactNpa() {
        return contactNpa;
    }

    public void setContactNpa(String contactNpa) {
        this.contactNpa = contactNpa;
    }

    public String getContactCop() {
        return contactCop;
    }

    public void setContactCop(String contactCop) {
        this.contactCop = contactCop;
    }

    public String getContactLineNo() {
        return contactLineNo;
    }

    public void setContactLineNo(String contactLineNo) {
        this.contactLineNo = contactLineNo;
    }

    public String getContactCellNpa() {
        return contactCellNpa;
    }

    public void setContactCellNpa(String contactCellNpa) {
        this.contactCellNpa = contactCellNpa;
    }

    public String getContactCellCop() {
        return contactCellCop;
    }

    public void setContactCellCop(String contactCellCop) {
        this.contactCellCop = contactCellCop;
    }

    public String getContactCellLineNo() {
        return contactCellLineNo;
    }

    public void setContactCellLineNo(String contactCellLineNo) {
        this.contactCellLineNo = contactCellLineNo;
    }

    public String getContactEmailAddress() {
        return contactEmailAddress;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getContactInternetAddress() {
        return contactInternetAddress;
    }

    public void setContactInternetAddress(String contactInternetAddress) {
        this.contactInternetAddress = contactInternetAddress;
    }

    @Override
    public String toString() {
        return "{" +
                "contactName:\"" + contactName + "\"" +
                ", contactTitle:\"" + contactTitle + "\"" +
                ", contactAddressId:\"" + contactAddressId + "\"" +
                ", contactNpa:\"" + contactNpa + "\"" +
                ", contactCop:\"" + contactCop + "\"" +
                ", contactLineNo:\"" + contactLineNo + "\"" +
                ", contactCellNpa:\"" + contactCellNpa + "\"" +
                ", contactCellCop:\"" + contactCellCop + "\"" +
                ", contactCellLineNo:\"" + contactCellLineNo + "\"" +
                ", contactEmailAddress:\"" + contactEmailAddress + "\"" +
                ", contactInternetAddress:\"" + contactInternetAddress + "\"" +
                "}";
    }
}
