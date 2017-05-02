package io.github.core55.config;

import io.github.core55.user.User;
import io.github.core55.meetup.Meetup;
import io.github.core55.location.Location;
import org.springframework.validation.Validator;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RestConfig extends RepositoryRestConfigurerAdapter {

    /**
     * Use Java MVC validator for validation.
     */
    @Autowired
    @Qualifier("mvcValidator")
    private Validator validator;

    /**
     * The entities can be validated by the back-end by means of special annotations. It is possible to specify in the
     * configureValidatingRepositoryEventListener method when the validation should occur.
     */
    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }

    /**
     * Spring Framework does not expose the ID of an entity by default because it is considered an information that
     * should be used only inside the back-end API and never exposed to the API consumer. List an entity class inside
     * configureRepositoryRestConfiguration in order to expose its ID field.
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Meetup.class);
        config.exposeIdsFor(Location.class);
    }
}