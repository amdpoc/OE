package ilink.utils;

import java.util.List;


public interface Sender {

    public void init();
    
    public String sendEmailToManager(String mailFrom, String mailSubject, String mailBody);

    public String sendEmailToManagerFrom(String mailSubject, String mailBody);

    public String sendEmail(String mailFrom, List<String> mailTo, List<String> mailCc, List<String> mailBcc,
                            String mailSubject, String mailBody);

}
