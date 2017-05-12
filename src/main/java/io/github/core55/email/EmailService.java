package io.github.core55.email;

/**
 * Created by P. Gajland and S. Stefani.
 */

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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

//    @Value("${spring.sendgrid.api-key}")
//    private String springApiKey;

    @Autowired
    private Environment environment;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    public void prepareAndSend(String recipient, String subject, String link) {
        Email from = new Email("app67253195@heroku.com");
        Email to = new Email(recipient);
        Content content = new Content("text/plain", "Hello, Email! \n Here is your login link: " + link);
        Mail mail = new Mail(from, subject, to, content);

        String springApiKey = environment.getProperty("spring.sendgrid.api-key");

        System.out.println("API key: " + springApiKey);
        SendGrid sg = new SendGrid(springApiKey);
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
