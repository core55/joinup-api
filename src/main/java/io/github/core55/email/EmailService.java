package io.github.core55.email;

/**
 * Created by P. Gajland and S. Stefani.
 */

import com.sendgrid.*;
import io.github.core55.core.DataHolder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.io.IOException;

@Service
@EnableAutoConfiguration
public class EmailService {

    private JavaMailSender javaMailSender;
    private MailContentBuilder mailContentBuilder;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    public void prepareAndSend(String recipient, String subject, String link) {

        String body = "<a class='button-mobile' href='http://localhost:8080" + link + "'>Register</a>";
        Email from = new Email("hello@joinup.nu");
        Email to = new Email(recipient);
        Content content = new Content("text/html", body);

        Mail mail = new Mail(from, subject, to, content);
        mail.setTemplateId("415b79db-7c07-4539-8827-e3745e1d1ce6");

        SendGrid sg = new SendGrid(DataHolder.getInstance().getSendGridKey());
        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            Response response = sg.api(request);
            System.out.println(response.statusCode);
            System.out.println(response.body);
            System.out.println(response.headers);
        } catch (IOException ex) {

        }
    }
}