package ilink.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class EmailSender {
    private String smtpHost;

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String sendEmailMessage(String mailFrom, List<String> mailTo, List<String> mailCc, List<String> mailBcc,
                                   String mailSubject, String mailBody) {

        String status = iLinkUtils.SUCCESS;

        // Create some properties and get the default Session.
        Properties props = System.getProperties();
        props.put("mail.host", smtpHost);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.transport.protocol", "smtp");
        Session mailSession = Session.getDefaultInstance(props, null);


        //Properties props = new Properties();
        // props.put("mail.smtp.host", mailHost);
        // Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(mailSession);

        try {
            msg.setFrom(new InternetAddress(mailFrom));

            msg.setRecipients(Message.RecipientType.TO, strArray2AddrArray(mailTo));
            msg.setRecipients(Message.RecipientType.CC, strArray2AddrArray(mailCc));
            msg.setRecipients(Message.RecipientType.BCC, strArray2AddrArray(mailBcc));

            msg.setSubject(mailSubject);
            msg.setSentDate(new Date());

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailBody);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
        } catch (Exception e) {
            System.out.println("############### EmailSender exception:  " + e.getMessage());
            status = iLinkUtils.ERROR;
        }
        return status;
    }

    private static Address[] strArray2AddrArray(List<String> addrList) throws AddressException {
        if (addrList == null || addrList.isEmpty())
            return null;
        Address addrArray[] = new Address[addrList.size()];
        int i = 0;
        for (String it : addrList) {
            if (!iLinkUtils.isEmpty(it)) {
                addrArray[i++] = new InternetAddress(it);
            }
        }
        return addrArray;
    }
}
