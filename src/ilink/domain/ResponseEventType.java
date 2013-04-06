package ilink.domain;

import ilink.utils.iLinkUtils;


public class ResponseEventType {
    String eventName;
    long customerId;
    double envelopeId;
    long  requestId;
    String businessName;
    String status;
    String errorCode;
    String errorMessage;
    String infoMessage;
    String result;

    public double getEnvelopeId() {
        return envelopeId;
    }

    public void setEnvelopeId(double envelopeId) {
        this.envelopeId = envelopeId;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseEventType() {
        this.eventName = "";
        this.customerId = 0;
        this.envelopeId = 0;
        this.requestId = 0;
        this.status = iLinkUtils.TransStatus.INFO.toString();
        this.errorCode = "";
        this.errorMessage = "";
        this.infoMessage = "";
        this.result = null;
    }

    public ResponseEventType(String errorCode, String errorMessage, long customerId, double envelopeId, long requestId,
                             iLinkUtils.TransStatus status, String result) {
        this.eventName = "";
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.customerId = customerId;
        this.customerId = requestId;
        this.envelopeId = envelopeId;
        this.status = status.toString();
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
                "eventName:\"" + eventName + "\"" +
                ", customerId:\"" + customerId + "\"" +
                ", envelopeId:\"" + envelopeId + "\"" +
                ", requestId:\"" + requestId + "\"" +
                ", businessName:\"" + businessName + "\"" +
                ", status:\"" + status + "\"" +
                ", errorCode:\"" + errorCode + "\"" +
                ", errorMessage:\"" + errorMessage + "\"" +
                ", infoMessage:\"" + infoMessage + "\"" +
                ", result:\"" + result + "\"" +
                "}";
    }
}