package org.example;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.auth.AppAuthorizer;
import org.example.auth.AppBasicAuthenticator;
import org.example.auth.User;
import org.example.resources.HelloDropWizardResource;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class DropwizardAuthApplication extends Application<DropwizardAuthConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropwizardAuthApplication().run(args);
    }

    @Override
    public String getName() {
        return "DropwizardAuth";
    }

    @Override
    public void initialize(final Bootstrap<DropwizardAuthConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final DropwizardAuthConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        final HelloDropWizardResource resource = new HelloDropWizardResource(configuration.getTemplate(), configuration.getDefaultName());
        environment.jersey().register(resource);


        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new AppBasicAuthenticator())
                .setAuthorizer(new AppAuthorizer())
                .setRealm("BASIC-AUTH-REALM")
                .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

}
