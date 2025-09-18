package affordmed_creation.urlShortening;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrlMapping {
    private String originalUrl;
    private String shortcode;
    private LocalDateTime createdAt;
    private LocalDateTime expiry;
    private int totalClicks;
    private List<ClickData> clicks;

    public UrlMapping(String originalUrl, String shortcode, LocalDateTime expiry) {
        this.originalUrl = originalUrl;
        this.shortcode = shortcode;
        this.createdAt = LocalDateTime.now();
        this.expiry = expiry;
        this.totalClicks = 0;
        this.clicks = new ArrayList<>();
    }


    public void recordClick(ClickData click) {
        totalClicks++;
        clicks.add(click);
    }


    public String getOriginalUrl() { return originalUrl; }
    public String getShortcode() { return shortcode; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiry() { return expiry; }
    public int getTotalClicks() { return totalClicks; }
    public List<ClickData> getClicks() { return clicks; }
}
