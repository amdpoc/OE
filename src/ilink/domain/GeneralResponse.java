package ilink.domain;

import ilink.utils.iLinkUtils;

import java.util.List;
import java.util.ArrayList;

public class GeneralResponse {

    ResponseEventType mainEvent;
    List<ResponseEventType> subEventsArray;
    List relatedObjects;
    String trackingError;

    public String getTrackingError() {
        return trackingError;
    }

    public void setTrackingError(String trackingError) {
        this.trackingError = trackingError;
    }

    public List getRelatedObjects() {
        return relatedObjects;
    }

    public void setRelatedObjects(List relatedObjects) {
        this.relatedObjects = relatedObjects;
    }

    public List<ResponseEventType> getSubEventsArray() {
        return subEventsArray;
    }

    public void setSubEventsArray(List<ResponseEventType> subEventsArray) {
        this.subEventsArray = subEventsArray;
    }

    public GeneralResponse() {
        this.mainEvent = new ResponseEventType();
    }

    public GeneralResponse(String errorCode, String errorMessage, long customerId, double envelopeId, long requestId,
                           iLinkUtils.TransStatus status, String result) {
        this.mainEvent = new ResponseEventType(errorCode, errorMessage,  customerId,  envelopeId, requestId, status, result);
    }

    public void setSubEvent(String eventName, long customerId, iLinkUtils.TransStatus status, String message) {
        if (subEventsArray == null) {
            subEventsArray = new ArrayList();
        }
        ResponseEventType subEvent = new ResponseEventType();
        subEvent.setEventName(eventName);
        subEvent.setCustomerId(customerId);
        subEvent.setStatus(status.toString());
        if (status.equals(iLinkUtils.TransStatus.SUCCESS.toString()) || status.equals(iLinkUtils.TransStatus.INFO.toString())) {
            subEvent.setInfoMessage(message);
        } else {
            subEvent.setErrorMessage(message);
        }
        subEventsArray.add(subEvent);
    }

    public String getBusinessName() {
        return mainEvent.businessName;
    }

    public void setBusinessName(String businessName) {
        this.mainEvent.businessName = businessName;
    }

    public Object getResult() {
        return mainEvent.result;
    }

    public void setResult(String result) {
        this.mainEvent.result = result;
    }

    public Object getEventName() {
        return mainEvent.eventName;
    }

    public void setEventName(String eventName) {
        this.mainEvent.eventName = eventName;
    }

    public String getErrorCode() {
        return mainEvent.errorCode;
    }

    public void setErrorCode(String errorCode) {
        mainEvent.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return mainEvent.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        mainEvent.errorMessage = errorMessage;
    }

    public long getCustomerId() {
        return mainEvent.customerId;
    }

    public void setCustomerId(long customerId) {
        mainEvent.customerId = customerId;
    }
    public double getEnvelopeId() {
        return mainEvent.envelopeId;
    }

    public void setEnvelopeId(double envelopeId) {
        mainEvent.envelopeId = envelopeId;
    }

    public long getRequestId() {
        return mainEvent.requestId;
    }

    public void setRequestId(long requestId) {
        mainEvent.requestId = requestId;
    }

    public String getStatus() {
        return mainEvent.status;
    }

    public void setStatus(iLinkUtils.TransStatus status) {
        mainEvent.status = status.toString();
    }
   @Override
    public String toString() {
        String response = "{" +
                "mainEvent:" + mainEvent.toString() + 
                ", subEventsArray:\"";
        if (subEventsArray != null) {
            for (ResponseEventType subEvent : subEventsArray)
                response += subEvent.toString();
        }
        response += "\"" + ", relatedObjects:\"";
        if (relatedObjects != null) {
            for (Object relatedObject : relatedObjects)
                response += relatedObject.toString();
        }
        response += "\", trackingError:\"" + trackingError;
        response += "\"" + "}";
        return response;
    }
}
