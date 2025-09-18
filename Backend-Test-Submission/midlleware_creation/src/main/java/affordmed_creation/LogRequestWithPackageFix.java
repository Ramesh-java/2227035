package affordmed_creation;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LogRequestWithPackageFix(
        LogStack stack,
        LogLevel level,
        @JsonProperty("package") LogPackage aPackage, // <-- This is the important part
        String message
) {}
