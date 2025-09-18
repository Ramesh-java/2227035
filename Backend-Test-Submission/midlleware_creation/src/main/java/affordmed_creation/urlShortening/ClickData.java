package affordmed_creation.urlShortening;

import java.time.LocalDateTime;

public class ClickData {
    private LocalDateTime timestamp;
    private String referrer;
    private String location;

    public ClickData(String referrer, String location) {
        this.timestamp = LocalDateTime.now();
        this.referrer = referrer;
        this.location = location;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public String getReferrer() { return referrer; }
    public String getLocation() { return location; }
}
