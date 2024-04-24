package com.bigkevmcd.katas.webhook;

import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;

public class WebhookConfiguration extends Configuration {
    @NotEmpty
    private String pushoverUser;

    @NotEmpty
    private String pushoverToken;

    @NotNull
    private Long repoID;

    @JsonProperty
    public String getPushoverUser() {
        return pushoverUser;
    }

    public void setPushoverUser(String pushoverUser) {
        this.pushoverUser = pushoverUser;
    }

    @JsonProperty
    public String getPushoverToken() {
        return pushoverToken;
    }

    public void setPushoverToken(String pushoverToken) {
        this.pushoverToken = pushoverToken;
    }

    public Long getRepoID() {
        return repoID;
    }

    public void setRepoID(Long repoID) {
        this.repoID = repoID;
    }
}