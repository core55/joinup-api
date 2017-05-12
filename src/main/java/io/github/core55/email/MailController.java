package io.github.core55.email;

/**
 * Created by P. Gajland on 2017-05-12.
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sendgrid.*;

import java.io.IOException;

@RestController
public class MailController {

    @RequestMapping(value = "/send")
    public void send(@RequestParam(value = "toEmail", required = true) String toEmail) throws IOException {
        //public void send() throws IOException {
        Email from = new Email("app67253195@heroku.com");
        String subject = "Hello World from the SendGrid Java Library!";
        Email to = new Email(toEmail);
        //Email to = new Email("philgajland@gmail.com");
        Content content = new Content("text/plain", "Hello, Email!");
        Mail mail = new Mail(from, subject, to, content);

        // TODO: Add SENDGRID_API_KEY
        SendGrid sg = new SendGrid(" ");
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
            throw ex;
        }
    }
}