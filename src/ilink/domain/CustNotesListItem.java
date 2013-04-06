package ilink.domain;

import java.io.Serializable;


public class CustNotesListItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private long noteId;
    private String creationDate;
    private String salesRep;
    private String office;
    private String noteText;
    private String noteType;
    private String callResults;

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getSalesRep() {
        return salesRep;
    }

    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getCallResults() {
        return callResults;
    }

    public void setCallResults(String callResults) {
        this.callResults = callResults;
    }

    @Override
    public String toString() {
        return "{" +
                "noteId:\"" + noteId + "\"" +
                ", creationDate:\"" + creationDate + "\"" +
                ", salesRep:\"" + salesRep + "\"" +
                ", office:\"" + office + "\"" +
                ", noteText:\"" + noteText + "\"" +
                ", noteType:\"" + noteType + "\"" +
                ", callResults:\"" + callResults + "\"" +
                "}";
    }
}
