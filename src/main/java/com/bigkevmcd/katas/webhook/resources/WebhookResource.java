package com.bigkevmcd.katas.webhook.resources;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import de.svenkubiak.jpushover.JPushover;

import com.bigkevmcd.katas.webhook.models.DeploymentStatusEvent;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
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
        var response = JPushover
                .newMessage()
                .withToken(pushoverToken)
                .withUser(pushoverUser)
                .withMessage(message)
                .push();
        return response.getResponse();
    }
}
