package ilink.domain;

public class AdocsPdfEntity {

    String letterFileIndex;
    String letterStatus;
    String errorMessage;
    public static final String STATUS_NEW = "N";
    public static final String STATUS_PRODUCED = "P";
    public static final String STATUS_READY = "R";
    public static final String STATUS_COMPLETED = "C";
    public static final String STATUS_IN_WORK = "I";
    public static final String FAILED_IN_PRODUCTION = "FP";
    public static final String FAILED_IN_DISTRIBUTION = "FD";
    public static final String FAILED_IN_ARCHIVE = "FA";

    public boolean isCompleted() {
        return (letterStatus != null && (letterStatus.equals(STATUS_PRODUCED) || letterStatus.equals(STATUS_COMPLETED)));
    }
    public boolean isInProcess() {
        return (letterStatus != null && (letterStatus.equals(STATUS_IN_WORK) || letterStatus.equals(STATUS_READY)));
    }
    public boolean isFailed() {
        return (letterStatus != null && (letterStatus.equals(FAILED_IN_PRODUCTION) || letterStatus.equals(FAILED_IN_DISTRIBUTION) ||
                                         letterStatus.equals(FAILED_IN_ARCHIVE)));
    }
    public String getLetterFileIndex() {
        return letterFileIndex;
    }

    public void setLetterFileIndex(String letterFileIndex) {
        this.letterFileIndex = letterFileIndex;
    }

    public String getLetterStatus() {
        return letterStatus;
    }

    public void setLetterStatus(String letterStatus) {
        this.letterStatus = letterStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "{" +
                "letterFileIndex:\"" + letterFileIndex + "\"" +
                ", letterStatus:\"" + letterStatus + "\"" +
                ", errorMessage:\"" + errorMessage + "\"" +
                "}";
    }
}
