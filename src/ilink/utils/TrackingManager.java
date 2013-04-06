package ilink.utils;

public interface TrackingManager {
    public static enum TransType {
        PREWS, PSTWS, ERRWS, PREHTTP, PSTHTTP
    }

    public static enum MsgType {
        TXT, XML, OBJ
    }
    public void init(javax.servlet.http.HttpSession session);
    public void logMessage(TransType transType, String transName, MsgType msgType, String message);
    public void logMessage(String callerId, TransType transType, String transName, MsgType msgType, String message);
}
