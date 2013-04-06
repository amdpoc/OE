package ilink.domain;

import java.io.Serializable;

public class CustDataType implements Serializable {

    private long customerId;
    private long contactId;
    private long addressId;
    private String businessName;
    private String listingPhone;
    private String formattedListingPhone;
    private String contactName;
    private String contactTitle;
    private String contactPhone;
    private String formattedContactPhone;
    private String formattedAddress;
    private double latitude;
    private double longitude;
    private String emailAddress;
    private String internetAddress;
    private String webPresence;
    private String prospectCode;
    private String hbd;
    private String headingName;
    private String respRep;
    private String latestNote;
    private String creditClass;
    private String recentClaimDate;
    private String oldestDueDate;
    private String pastDueDate;
    private String pastDueAmt;
    private String nisdAmt;
    private String botsAmt;
    private String forcastedAmt;
    private String oldestDeptAge;
    private String lockoutInd;
    private String disconectedInd;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getListingPhone() {
        return listingPhone;
    }

    public void setListingPhone(String listingPhone) {
        this.listingPhone = listingPhone;
    }

    public String getFormattedListingPhone() {
        return formattedListingPhone;
    }

    public void setFormattedListingPhone(String formattedListingPhone) {
        this.formattedListingPhone = formattedListingPhone;
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

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getFormattedContactPhone() {
        return formattedContactPhone;
    }

    public void setFormattedContactPhone(String formattedContactPhone) {
        this.formattedContactPhone = formattedContactPhone;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getInternetAddress() {
        return internetAddress;
    }

    public void setInternetAddress(String internetAddress) {
        this.internetAddress = internetAddress;
    }

    public String getWebPresence() {
        return webPresence;
    }

    public void setWebPresence(String webPresence) {
        this.webPresence = webPresence;
    }

    public String getProspectCode() {
        return prospectCode;
    }

    public void setProspectCode(String prospectCode) {
        this.prospectCode = prospectCode;
    }

    public String getHbd() {
        return hbd;
    }

    public void setHbd(String hbd) {
        this.hbd = hbd;
    }

    public String getRespRep() {
        return respRep;
    }

    public void setRespRep(String respRep) {
        this.respRep = respRep;
    }

    public String getLatestNote() {
        return latestNote;
    }

    public void setLatestNote(String latestNote) {
        this.latestNote = latestNote;
    }

    public String getCreditClass() {
        return creditClass;
    }

    public void setCreditClass(String creditClass) {
        this.creditClass = creditClass;
    }

    public String getRecentClaimDate() {
        return recentClaimDate;
    }

    public void setRecentClaimDate(String recentClaimDate) {
        this.recentClaimDate = recentClaimDate;
    }

    public String getOldestDueDate() {
        return oldestDueDate;
    }

    public void setOldestDueDate(String oldestDueDate) {
        this.oldestDueDate = oldestDueDate;
    }

    public String getPastDueDate() {
        return pastDueDate;
    }

    public void setPastDueDate(String pastDueDate) {
        this.pastDueDate = pastDueDate;
    }

    public String getPastDueAmt() {
        return pastDueAmt;
    }

    public void setPastDueAmt(String pastDueAmt) {
        this.pastDueAmt = pastDueAmt;
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

    public String getOldestDeptAge() {
        return oldestDeptAge;
    }

    public void setOldestDeptAge(String oldestDeptAge) {
        this.oldestDeptAge = oldestDeptAge;
    }

    public String getLockoutInd() {
        return lockoutInd;
    }

    public void setLockoutInd(String lockoutInd) {
        this.lockoutInd = lockoutInd;
    }

    public String getHeadingName() {
        return headingName;
    }

    public void setHeadingName(String headingName) {
        this.headingName = headingName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getForcastedAmt() {
        return forcastedAmt;
    }

    public void setForcastedAmt(String forcastedAmt) {
        this.forcastedAmt = forcastedAmt;
    }

    public String getDisconectedInd() {
        return disconectedInd;
    }

    public void setDisconectedInd(String disconectedInd) {
        this.disconectedInd = disconectedInd;
    }

    @Override
    public String toString() {
        return "{" +
                "customerId:\"" + customerId + "\"" +
                ", contactId:\"" + contactId + "\"" +
                ", addressId:\"" + addressId + "\"" +
                ", businessName:\"" + businessName + "\"" +
                ", listingPhone:\"" + listingPhone + "\"" +
                ", formattedListingPhone:\"" + formattedListingPhone + "\"" +
                ", contactName:\"" + contactName + "\"" +
                ", contactTitle:\"" + contactTitle + "\"" +
                ", contactPhone:\"" + contactPhone + "\"" +
                ", formattedContactPhone:\"" + formattedContactPhone + "\"" +
                ", formattedAddress:\"" + formattedAddress + "\"" +
                ", latitude:\"" + latitude + "\"" +
                ", longitude:\"" + longitude + "\"" +
                ", emailAddress:\"" + emailAddress + "\"" +
                ", internetAddress:\"" + internetAddress + "\"" +
                ", webPresence:\"" + webPresence + "\"" +
                ", prospectCode:\"" + prospectCode + "\"" +
                ", hbd:\"" + hbd + "\"" +
                ", headingName:\"" + headingName + "\"" +
                ", respRep:\"" + respRep + "\"" +
                ", latestNote:\"" + latestNote + "\"" +
                ", creditClass:\"" + creditClass + "\"" +
                ", recentClaimDate:\"" + recentClaimDate + "\"" +
                ", oldestDueDate:\"" + oldestDueDate + "\"" +
                ", pastDueDate:\"" + pastDueDate + "\"" +
                ", pastDueAmt:\"" + pastDueAmt + "\"" +
                ", nisdAmt:\"" + nisdAmt + "\"" +
                ", botsAmt:\"" + botsAmt + "\"" +
                ", forcastedAmt:\"" + forcastedAmt + "\"" +
                ", oldestDeptAge:\"" + oldestDeptAge + "\"" +
                ", lockoutInd:\"" + lockoutInd + "\"" +
                ", disconectedInd:\"" + disconectedInd + "\"" +
                "}";
    }
}
