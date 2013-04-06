package ilink.security;

import org.springframework.security.userdetails.User;
import org.springframework.security.GrantedAuthority;


public class AmdocsUserDetails extends User
{
    private AmdocsEmployee employee;

    public AmdocsUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked,GrantedAuthority[] authorities,
                             AmdocsEmployee employee)throws IllegalArgumentException
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.employee = employee;
    }

    public AmdocsUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked,GrantedAuthority[] authorities,
                             String employeeNumber, String securityProfile, String activityCode,
                             String firstName, String lastName, String officeCode,
                             String officeType, String officeName, String role)throws IllegalArgumentException
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        setEmployeeNumber(employeeNumber);
        setSecurityProfile(securityProfile);
        setActivityCode(activityCode);
        setFirstName(firstName);
        setLastName(lastName);
        setOfficeCode(officeCode);
        setOfficeType(officeType);
        setOfficeName(officeName);
        setRole(role);
    }
    public String getTimezone() {
        return employee.getTimezone();
    }

    public void setTimezone(String timezone) {
        this.employee.setTimezone(timezone);
    }
    public boolean isReadOnly() {
        return employee.isReadOnly();
    }

    public void setReadOnly(boolean readOnly) {
        employee.setReadOnly(readOnly);
    }

    public String getOfficeType() {
        return employee.getOfficeType();
    }

    public void setOfficeType(String officeType) {
        this.employee.setOfficeType(officeType);
    }

    public void setOfficeName(String officeName) {
        this.employee.setOfficeName(officeName);
    }

    public String getOfficeName() {
        return employee.getOfficeName();
    }

    public String getRole() {
        return employee.getRole();
    }

    public void setRole(String role) {
        employee.setRole(role);
    }
    public String getEMail() {
        return employee.getEMail();
    }
    public void setEMail(String eMail) {
        this.employee.setEMail(eMail);
    }
     public String getManagerEmail() {
        return this.employee.getManagerEmail();
    }

    public void setManagerEmail(String managerEmail) {
        this.employee.setManagerEmail(managerEmail);
    }
    public void setFirstName(String firstName) {
        employee.setFirstName(firstName);
    }
    public String getFirstName() {
        return employee.getFirstName();
    }
    public void setLastName(String lastName) {
        employee.setLastName(lastName);
    }
    public String getLastName() {
        return employee.getLastName();
    }
    public void setOfficeCode(String officeCode) {
        employee.setOfficeCode(officeCode);
    }

    public String getOfficeCode() {
        return employee.getOfficeCode();
    }
    public void setEmployeeNumber(String employeeNumber) {
        employee.setEmployeeNumber(employeeNumber);
    }
    public String getEmployeeNumber() {
        return employee.getEmployeeNumber();
    }
    public void setSecurityProfile(String securityProfile) {
        employee.setSecurityProfile(securityProfile);
    }

    public String getSecurityProfile() {
        return employee.getSecurityProfile();
    }
     public void setActivityCode(String activityCode) {
        employee.setActivityCode(activityCode);
    }

    public String getActivityCode() {
        return employee.getActivityCode();
    }
   
}
