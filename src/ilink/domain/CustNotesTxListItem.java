package ilink.domain;

import java.io.Serializable;


public class CustNotesTxListItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private long noteId;
    private String creationDate;
    private int repId;
    private String repName;
    private String noteText;
    private String noteType;

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

    public int getRepId() {
        return repId;
    }

    public void setRepId(int repId) {
        this.repId = repId;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
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

    @Override
    public String toString() {
        return "{" +
                "noteId:\"" + noteId + "\"" +
                ", creationDate:\"" + creationDate + "\"" +
                ", repId:\"" + repId + "\"" +
                ", repName:\"" + repName + "\"" +
                ", noteText:\"" + noteText + "\"" +
                ", noteType:\"" + noteType + "\"" +
                "}";
    }
}
