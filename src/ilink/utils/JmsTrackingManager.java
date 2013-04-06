package ilink.utils;

import amdocs.ipad.tracking.TrackingMsg;

import java.util.Date;
import java.rmi.server.UID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JmsTrackingManager implements TrackingManager {

    private TrackingMsg trackingMsgr;
    private String currentServer;
    private String serviceServer = ""; //External Service URL / path
    private String callerIP = "";  //IP address of logged in user if you can get it, otherwise blank
    private String callerId; //_iLink_ipad_mobile
    private String transId;
    protected final Log logger = LogFactory.getLog(getClass());

    public void init(javax.servlet.http.HttpSession session) {

        String loginName = iLinkUtils.getLoginNameFromSession(session);
        callerId = loginName + callerId;
    }

    public String getTransactionId() {
        return new UID().toString();
    }

    public void logMessage(TransType transType, String transName, MsgType msgType, String message) {
        try {
            if (transType.equals(transType.PREWS)) {
                transId = getTransactionId();
            }
            trackingMsgr.trackingLog(transId, new Date(), transType.toString(), msgType.toString(),
                    transName, message, callerId, callerIP, currentServer, serviceServer);
        } catch (Exception ex) {
            logger.error("################ ERROR JmsTrackingManager:logMessage exception occured :" + iLinkUtils.getExceptionMessage(ex));
        }
    }

     public void logMessage(String callerId, TransType transType, String transName, MsgType msgType, String message) {
        try {
            String curTransId = getTransactionId();
            String currentCallerId = callerId + this.callerId;
            trackingMsgr.trackingLog(curTransId, new Date(), transType.toString(), msgType.toString(),
                                     transName, message, currentCallerId, callerIP, currentServer, serviceServer);
        } catch (Exception ex) {
            logger.error("################ ERROR JmsTrackingManager:logMessage exception occured :" + iLinkUtils.getExceptionMessage(ex));
        }
    }

    public TrackingMsg getTrackingMsgr() {
        return trackingMsgr;
    }

    public void setTrackingMsgr(TrackingMsg trackingMsgr) {
        this.trackingMsgr = trackingMsgr;
    }

    public String getCurrentServer() {
        return currentServer;
    }

    public void setCurrentServer(String currentServer) {
        this.currentServer = currentServer;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }
}
