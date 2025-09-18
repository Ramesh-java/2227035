package affordmed_creation;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LogPackage {
    // Backend Packages
    CACHE("cache"),
    CONTROLLER("controller"),
    CRON_JOB("cron_job"),
    DB("db"),
    DOMAIN("domain"),
    HANDLER("handler"),
    REPOSITORY("repository"),
    ROUTE("route"),
    SERVICE("service"),

    // Frontend Packages
    API("api"),
    COMPONENT("component"),
    HOOK("hook"),
    PAGE("page"),
    STATE("state"),
    STYLE("style"),

    // Shared Packages
    AUTH("auth"),
    CONFIG("config"),
    MIDDLEWARE("middleware"),
    UTILS("utils");


    private final String value;

    LogPackage(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}