package com.bigkevmcd.katas.webhook.resources;

import com.bigkevmcd.katas.webhook.models.DeploymentStatusEvent;
import de.svenkubiak.jpushover.JPushover;
import de.svenkubiak.jpushover.JPushoverResponse;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class WebhookResource {
    private final String pushoverToken;
    private final String pushoverUser;
    private final Long repoID;

    public WebhookResource(String pushoverToken, String pushoverUser, Long repoID) {
        this.pushoverToken = pushoverToken;
        this.pushoverUser = pushoverUser;
        this.repoID = repoID;
    }

    @POST
    public String handleWebhook(@HeaderParam("X-GitHub-Event") String githubEvent,
                                DeploymentStatusEvent payload) throws IOException, InterruptedException {

        if (githubEvent.equals("deployment_status")) {
            if (payload.getRepository().getId().equals(repoID)) {
                return sendNotification(payload.getRepository().getFullName(),
                        payload.getDeployment().getRef(),
                        payload.getDeployment().getEnvironment(),
                        payload.getDeploymentStatus().getState());
            }
        }
        return "ignored";
    }

    private String sendNotification(String repoName, String ref, String environment, String state) throws IOException, InterruptedException {
        final String message = String.format("There has been a new deployment of %s to %s ref '%s' with state %s", repoName, environment, ref, state);
        JPushoverResponse response = JPushover.build()
                .withToken(pushoverToken)
                .withUser(pushoverUser)
                .withMessage(message)
                .push();
        return response.getResponse();
    }
}