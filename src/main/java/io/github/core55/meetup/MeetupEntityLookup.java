/**
 * MeetupEntityLookup.java
 *
 * Created by S. Stefani on 2017-04-23.
 */

package io.github.core55.meetup;

import java.io.Serializable;
import org.springframework.stereotype.Component;
import org.springframework.data.rest.core.support.EntityLookupSupport;

@Component
public class MeetupEntityLookup extends EntityLookupSupport<Meetup> {

    private final MeetupRepository meetups;

    public MeetupEntityLookup(MeetupRepository meetups) {
        this.meetups = meetups;
    }

    @Override
    public Serializable getResourceIdentifier(Meetup meetup) {
        return meetup.getHash();
    }

    @Override
    public Object lookupEntity(Serializable hash) {
        return meetups.findByHash(hash.toString());
    }
}
