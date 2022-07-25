package org.example.serverbasket.utils;

import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.serverbasket.config.Configuration;

import java.util.Properties;


public class MandarMail {
    private final Configuration config;

    @Inject
    public MandarMail(Configuration config) {
        this.config = config;
    }

    public void generateAndSendEmail(String to, String msg, String subject) throws MessagingException {
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        // Step1

        mailServerProperties = System.getProperties();
        mailServerProperties.put(Constantes.MAIL_SMTP_PORT, Integer.parseInt(Constantes.VALUE_MAIL_SMT_PORT));
        mailServerProperties.put(Constantes.MAIL_SMTP_AUTH, Constantes.VALUE_MAIL_SMTP_AUTH);
        mailServerProperties.put(Constantes.MAIL_SMTP_SSL_TRUST, Constantes.SMTP_GMAIL_COM);
        mailServerProperties.put(Constantes.MAIL_SMTP_STARTTLS_ENABLE, Constantes.VALUE_MAIL_SMTP_AUTH);

        // Step2

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        String emailBody = msg;
        generateMailMessage.setContent(emailBody, Constantes.TEXT_HTML);


        // Step3

        Transport transport = getMailSession.getTransport(Constantes.SMTP);

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect(config.getHost(),
                config.getUser_email(),
                config.getPassword_email());
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
