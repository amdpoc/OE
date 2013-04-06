package ilink.utils;

import org.apache.log4j.Logger;
import java.util.List;
import java.util.ArrayList;


public class IlinkEmailSender extends EmailSender implements Sender {

    private boolean testInd = false;
    private String mngrEmail;
    private String mailFrom;

    private static Logger log = Logger.getLogger(IlinkEmailSender.class);

    public boolean isTestInd() {
        return testInd;
    }

    public void setTestInd(boolean testInd) {
        this.testInd = testInd;
    }

    public String getMngrEmail() {
        return mngrEmail;
    }

    public void setMngrEmail(String mngrEmail) {
        this.mngrEmail = mngrEmail;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public void init() {
        if (!testInd) {
            mngrEmail = iLinkUtils.getManagerEmailFromDWR();
            if (iLinkUtils.isEmpty(mngrEmail)) {
                log.debug("######### IlinkEmailSender : Manager's mail is not initialized in employee table for user:" + iLinkUtils.getEmployeeIdFromDWR());
            }
        }
    }

    public String sendEmail(String mailFrom, List<String> mailTo, List<String> mailCc, List<String> mailBcc,
                            String mailSubject, String mailBody) {
        return sendEmailMessage(mailFrom, mailTo, mailCc, mailBcc, mailSubject, mailBody);
    }

    public String sendEmailToManager(String mailFrom, String mailSubject, String mailBody) {
        String status;
        if (!iLinkUtils.isEmpty(mngrEmail)) {
            List<String> mailTo = new ArrayList<String>();
            mailTo.add(mngrEmail);
            status = sendEmailMessage(mailFrom, mailTo, null, null, mailSubject, mailBody);
        } else
            status = iLinkUtils.ERROR;

        return status;
    }

    public String sendEmailToManagerFrom(String mailSubject, String mailBody) {
        return sendEmailToManager(mailFrom, mailSubject, mailBody);
    }

}
