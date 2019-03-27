package com.bigkevmcd.katas.webhook;

import com.bigkevmcd.katas.webhook.resources.WebhookResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WebhookApplication extends Application<WebhookConfiguration> {
    public static void main(String[] args) throws Exception {
        new WebhookApplication().run(args);
    }

    @Override
    public String getName() {
        return "webhook-application";
    }

    @Override
    public void initialize(Bootstrap<WebhookConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(WebhookConfiguration configuration,
                    Environment environment) {
        final WebhookResource resource = new WebhookResource(
                configuration.getPushoverToken(),
                configuration.getPushoverUser(),
                configuration.getRepoID()
        );
        environment.jersey().register(resource);
    }
}