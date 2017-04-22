/**
 * RestConfig.java
 *
 * Created by S. Stefani on 2017-04-22.
 */

package io.github.core55.core;

import org.springframework.validation.Validator;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RestConfig extends RepositoryRestConfigurerAdapter {

    @Autowired
    @Qualifier("mvcValidator")
    private Validator validator;

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }

}
