/*
  Authors: P. Gajland, S. Stefani
 */

package io.github.core55.core;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import io.github.core55.tokens.AuthToken;
import io.github.core55.tokens.MagicLinkToken;
import org.springframework.stereotype.Component;
import io.github.core55.tokens.AuthTokenRepository;
import io.github.core55.tokens.MagicLinkTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Cron jobs scheduler. Here are listed function that are automatically
 * triggered at constant intervals of time.
 */
@Component
public class ScheduledTask {

    public final MagicLinkTokenRepository magicLinkTokenRepository;
    public final AuthTokenRepository authTokenRepository;

    @Autowired
    public ScheduledTask(
            MagicLinkTokenRepository magicLinkTokenRepository,
            AuthTokenRepository authTokenRepository) {
        this.magicLinkTokenRepository = magicLinkTokenRepository;
        this.authTokenRepository = authTokenRepository;
    }

    /**
     * This scheduled task (cron job) is expected to run every hour and it
     * triggers the operation for removal of expired tokens.
     */
    @Scheduled(fixedDelay = 3600000)
    public void validateAuthToken() throws Exception {

        pruneMagicLinkTokens();
        pruneAuthenticationTokens();
    }

    /**
     * Checks the creation time of every magicLinkToken. If a token has been
     * alive for more than 24 hours it is removed from the database.
     */
    private void pruneMagicLinkTokens() throws ParseException {

        for (MagicLinkToken magicLinkToken : magicLinkTokenRepository.findAll()) {
            Date magicLinkTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                    .parse(magicLinkToken.getCreatedAt());

            long difference = new Date().getTime() - magicLinkTime.getTime();
            long differenceInHours = TimeUnit.MILLISECONDS.toHours(difference);

            if (differenceInHours > 24) {
                magicLinkTokenRepository.delete(magicLinkToken);
            }
        }
    }

    /**
     * Checks the creation time of every authenticationToken. If a token has
     * been alive for more than 24 hours it is removed from the database.
     */
    private void pruneAuthenticationTokens() throws ParseException {

        for (AuthToken authToken : authTokenRepository.findAll()) {
            Date authTokenTime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                    .parse(authToken.getCreatedAt());

            long difference = new Date().getTime() - authTokenTime.getTime();
            long differenceInHours = TimeUnit.MILLISECONDS.toHours(difference);

            if (differenceInHours > 24) {
                authTokenRepository.delete(authToken);
            }
        }
    }
}
