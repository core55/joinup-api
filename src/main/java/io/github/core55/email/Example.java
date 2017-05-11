package io.github.core55.email;

/**
 * Created by P. Gajland on 2017-05-11.
 */

import com.sendgrid.*;

import java.io.IOException;

public class Example {
    public static void main(String[] args) throws IOException {
        Email from = new Email("test@example.com");
        String subject = "Hello World from the SendGrid Java Library!";
        Email to = new Email("test@example.com");
        Content content = new Content("text/plain", "Hello, Email!");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
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