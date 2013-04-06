package ilink.utils;

public class DummyTrackingManager implements TrackingManager {


    public void init(javax.servlet.http.HttpSession session) {
    }

    public void logMessage(TransType transType, String transName, MsgType msgType, String message) {
    }

    public void logMessage(String callerId, TransType transType, String transName, MsgType msgType, String message) {
    }
}
