package affordmed_creation;

public record LogRequest(
        LogStack stack,
        LogLevel level,
        LogPackage aPackage, // 'package' is a reserved keyword in Java
        String message
) {}