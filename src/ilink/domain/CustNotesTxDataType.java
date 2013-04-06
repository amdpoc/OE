package ilink.domain;

import ilink.utils.iLinkUtils;

import java.io.Serializable;

public class CustNotesTxDataType implements Serializable {

    private long customerId;
    private String creationDate;
    private int repId;
    private String noteText;
    private String noteType;
    private String officeType;

    @Override
    public String toString() {
        return "{" +
                "customerId:\"" + customerId + "\"" +
                ", creationDate:\"" + creationDate + "\"" +
                ", repId:\"" + repId + "\"" +
                ", noteText:\"" + noteText + "\"" +
                ", noteType:\"" + noteType + "\"" +
                ", officeType:\"" + officeType + "\"" +
                "}";
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getRepId() {
        return repId;
    }

    public void setRepId(int repId) {
        this.repId = repId;
    }

    public String getNoteText() 
    {
        return noteText;
    }

    public void setNoteText(String noteText) 
    {
    	if (!iLinkUtils.isEmpty(noteText)){
    		this.noteText = iLinkUtils.formatStringToUnicode(noteText);
    	}
    }
    	
    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getOfficeType() {
        return officeType;
    }

    public void setOfficeType(String officeType) {
        this.officeType = officeType;
    }
}
