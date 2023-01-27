package com.epam.hospital.util;

import com.epam.hospital.model.User;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

public class EmailUtil {

    public static void sendRegistrationEmail(String password, User user){
        try {
            MimeMessage message = getMimeMessage(user.getEmail(), "Registration on hospital portal");
            VelocityEngine ve = getVelocityEngine();

            Template t = ve.getTemplate("email/registration.vm");
            VelocityContext context = new VelocityContext();
            context.put("name", user.getFirstName());
            context.put("role", user.getRole().name());
            context.put("email", user.getEmail());
            context.put("password", password);
            StringWriter out = new StringWriter();
            t.merge(context, out);

            message.setContent(out.toString(), "text/html");
            Transport.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Session getSession() {
        Properties prop = getProperties();
        return Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(prop.getProperty("mail.user"), prop.getProperty("mail.password"));
                    }
                });
    }

    private static Properties getProperties() {
        Properties prop = System.getProperties();
        try {
            prop.load(EmailUtil.class.getClassLoader().getResourceAsStream("email/mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    private static MimeMessage getMimeMessage(String to, String subject) throws MessagingException {
        Properties prop = getProperties();
        Session session = getSession();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(prop.getProperty("mail.user")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        return message;
    }

    private static VelocityEngine getVelocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
        velocityEngine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
        return velocityEngine;
    }

    private EmailUtil() {
    }
}
