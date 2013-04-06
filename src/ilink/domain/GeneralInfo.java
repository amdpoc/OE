package ilink.domain;

import java.io.Serializable;


public class GeneralInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private  String serverName;
    private  String logicalDate;
    private  String currentCCVersion;
    private  String currentDEXVersion;
    private  String dataBase;
    private String employeeName;
    private String employeeId;
    private String officeName;

    public String getServerName() {
        return serverName;
    }

    public  void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getLogicalDate() {
        return logicalDate;
    }

    public void setLogicalDate(String logicalDate) {
        this.logicalDate = logicalDate;
    }

    public String getCurrentCCVersion() {
        return currentCCVersion;
    }

    public void setCurrentCCVersion(String currentCCVersion) {
        this.currentCCVersion = currentCCVersion;
    }

    public String getCurrentDEXVersion() {
        return currentDEXVersion;
    }

    public void setCurrentDEXVersion(String currentDEXVersion) {
        this.currentDEXVersion = currentDEXVersion;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    @Override
    public String toString() {
        return "{" +
                "serverName:\"" + serverName + "\"" +
                ", logicalDate:\"" + logicalDate + "\"" +
                ", currentCCVersion:\"" + currentCCVersion + "\"" +
                ", currentDEXVersion:\"" + currentDEXVersion + "\"" +
                ", dataBase:\"" + dataBase + "\"" +
                ", employeeName:\"" + employeeName + "\"" +
                ", employeeId:\"" + employeeId + "\"" +
                ", officeName:\"" + officeName + "\"" +
                "}";
    }
}
