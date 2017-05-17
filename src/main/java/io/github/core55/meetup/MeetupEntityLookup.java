/*
  Authors: S. Stefani
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

    /**
     * The entities handled by the API are indexed by default with the ID which is also used to build the routes. The
     * following getResourceIdentifier method allows to index the meetups by their hash values.
     */
    @Override
    public Serializable getResourceIdentifier(Meetup meetup) {
        return meetup.getHash();
    }

    /**
     * The following lookupEntity method provides Spring Framework with a method to search and index Meetup entities by
     * their hash values.
     */
    @Override
    public Object lookupEntity(Serializable hash) {
        return meetups.findByHash(hash.toString());
    }
}
