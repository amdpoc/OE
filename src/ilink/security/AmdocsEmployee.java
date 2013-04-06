package ilink.security;

import java.io.Serializable;


public class AmdocsEmployee implements Serializable {

    private String firstName;
    private String lastName;
    private String officeCode;
    private String officeType;
    private String officeName;
    private String role;
    private String employeeNumber;
    private String securityProfile;
    private String activityCode;
    private String eMail;
    private String managerEmail;
    private String timezone;
    private boolean isReadOnly;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getOfficeType() {
        return officeType;
    }

    public void setOfficeType(String officeType) {
        this.officeType = officeType;
    }

    public String getOfficeName()
    {
        return officeName;
    }

    public void setOfficeName(String officeName)
    {
        this.officeName = officeName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getOfficeCode() {
        return officeCode;
    }
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    public void setSecurityProfile(String securityProfile) {
        this.securityProfile = securityProfile;
    }

    public String getSecurityProfile() {
        return securityProfile;
    }
    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("firstName: ").append(firstName).append(";");
        buffer.append("lastName: ").append(lastName);
        buffer.append("officeCode: ").append(officeCode);
        buffer.append("officeName: ").append(officeName);
        buffer.append("employeeNumber: ").append(employeeNumber);
        buffer.append("securityProfile: ").append(securityProfile);
        buffer.append("activityCode: ").append(activityCode);

    return buffer.toString();
    }
}
