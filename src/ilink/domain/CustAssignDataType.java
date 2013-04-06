package ilink.domain;

import java.io.Serializable;

public class CustAssignDataType implements Serializable {

    private String employeeName;
    private String employeeId;
    private String project;
    private String stage;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "{" +
                "employeeName:\"" + employeeName + "\"" +
                ", employeeId:\"" + employeeId + "\"" +
                ", project:\"" + project + "\"" +
                ", stage:\"" + stage + "\"" +
                "}";
    }
}
