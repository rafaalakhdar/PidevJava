// 
// Decompiled by Procyon v0.5.36
// 

package entities;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.Transport;
import java.util.Date;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
import java.util.Properties;

public class SendMail
{
    public void SendMail(final String host, final String port, final String userName, final String password, final String toAddress, final String subject, final String message) throws AddressException, MessagingException {
        final Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        final Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        final Session session = Session.getInstance(properties, auth);
        final Message msg = (Message)new MimeMessage(session);
        msg.setFrom((Address)new InternetAddress(userName));
        final InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, (Address[])toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);
        Transport.send(msg);
    }
}
