package com.bigkevmcd.katas.webhook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeploymentStatusEvent {
    private Repository repository;
    private DeploymentStatus deploymentStatus;
    private Deployment deployment;

    @JsonProperty("repository")
    public Repository getRepository() {
        return repository;
    }

    @JsonProperty("deployment_status")
    public DeploymentStatus getDeploymentStatus() {
        return deploymentStatus;
    }

    @JsonProperty("deployment")
    public Deployment getDeployment() {
        return deployment;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class Repository {
        private Long id;
        private String fullName;

        @JsonProperty
        public Long getId() {
            return id;
        }

        @JsonProperty("full_name")
        public String getFullName() {
            return fullName;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class DeploymentStatus {
        private String state;
        private String targetUrl;
        private String description;

        @JsonProperty
        public String getState() {
            return state;
        }

        @JsonProperty("target_url")
        public String getTargetUrl() {
            return targetUrl;
        }

        @JsonProperty("description")
        public String getDescription() {
            return description;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class Deployment {
        private String ref;
        private String sha;
        private String task;
        private String environment;

        @JsonProperty
        public String getRef() {
            return ref;
        }

        @JsonProperty
        public String getSha() {
            return sha;
        }

        @JsonProperty
        public String getTask() {
            return task;
        }

        @JsonProperty
        public String getEnvironment() {
            return environment;
        }
    }
}
