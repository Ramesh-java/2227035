package affordmed_creation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final LoggingService loggingService;

    public MyController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping("/test-log")
    public String testLogging() {
        // Example from the prompt
        loggingService.log(
                LogStack.BACKEND,
                LogLevel.ERROR,
                LogPackage.HANDLER,
                "received string, expected bool"
        );

        // Another example
        loggingService.log(
                LogStack.BACKEND,
                LogLevel.FATAL,
                LogPackage.DB,
                "Critical database connection failure."
        );

        return "Logs have been sent!";
    }
}