package io.github.core55.email;

import com.sendgrid.*;
import io.github.core55.core.DataHolder;

import java.io.IOException;

/**
 * Created by P. Gajland on 2017-05-15.
 */
public class MailTemplate {


    public void prepareAndSend(String recipient, String subject, String link, String templateID, String regOrLog) {

        //TODO: change localhost
        String body = "<a class='button-mobile' href='http://localhost:8080" + link + "'>" + regOrLog + "</a>";
        Email from = new Email("service@joinup.nu");
        Email to = new Email(recipient);
        Content content = new Content("text/html", body);

        Mail mail = new Mail(from, subject, to, content);
        mail.setTemplateId(templateID);

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
