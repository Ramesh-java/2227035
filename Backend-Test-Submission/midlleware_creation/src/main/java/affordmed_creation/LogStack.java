package affordmed_creation;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LogStack {
    BACKEND("backend"),
    FRONTEND("frontend");

    private final String value;

    LogStack(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}