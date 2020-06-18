// 
// Decompiled by Procyon v0.5.36
// 

package utilitez;

import javax.mail.MessagingException;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
import java.util.Properties;

public class Mailing
{
    public static void send(final String to, final String sub, final String msg, final String user, final String pass) {
        final Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        final Session session = Session.getDefaultInstance(props, (Authenticator)new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        try {
            final Message message = (Message)new MimeMessage(session);
            message.setFrom((Address)new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse(to));
            message.setSubject(sub);
            message.setText(msg);
            Transport.send(message);
        }
        catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Something happened!");
            throw new RuntimeException((Throwable)e);
        }
    }
}
