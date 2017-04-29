package io.github.core55.core;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import io.github.core55.tokens.MagicLinkToken;
import org.springframework.stereotype.Component;
import io.github.core55.tokens.MagicLinkTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class ScheduledTask {

    @Autowired
    public MagicLinkTokenRepository magicLinkTokenRepository;

    /**
     * This scheduled task (cron job) is expected to run every hour and it checks the creation time of every
     * magicLinkToken. If a token has been alive for more than 24 hours it is removed from the database.
     */
    @Scheduled(fixedDelay = 3600000)
    public void validateAuthToken() throws Exception {

        Date currentTime = new Date();

        for (MagicLinkToken magicLinkToken : magicLinkTokenRepository.findAll()) {
            Date magicLinkTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").parse(magicLinkToken.getCreatedAt());

            long difference = currentTime.getTime() - magicLinkTime.getTime();
            long differenceInHours = TimeUnit.MILLISECONDS.toHours(difference);

            if (differenceInHours > 24) {
                magicLinkTokenRepository.delete(magicLinkToken);
            }
        }
    }
}
