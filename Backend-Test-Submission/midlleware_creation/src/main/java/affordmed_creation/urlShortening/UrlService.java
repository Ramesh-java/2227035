package affordmed_creation.urlShortening;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlService {

    private final Map<String, UrlMapping> store = new ConcurrentHashMap<>();

    private final int DEFAULT_VALIDITY_MINUTES = 30;

    public UrlMapping createShortUrl(String originalUrl, Integer validity, String customCode) {
        String shortcode = customCode != null ? customCode : generateUniqueCode();
        if (store.containsKey(shortcode)) {
            throw new IllegalArgumentException("Shortcode already exists.");
        }

        int validMinutes = validity != null ? validity : DEFAULT_VALIDITY_MINUTES;
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(validMinutes);

        UrlMapping mapping = new UrlMapping(originalUrl, shortcode, expiry);
        store.put(shortcode, mapping);

        return mapping;
    }

    public UrlMapping getOriginalUrl(String shortcode) {
        UrlMapping mapping = store.get(shortcode);
        if (mapping == null) return null;
        if (mapping.getExpiry().isBefore(LocalDateTime.now())) return null; // expired
        return mapping;
    }

    public void recordClick(String shortcode, String referrer, String location) {
        UrlMapping mapping = store.get(shortcode);
        if (mapping != null) {
            mapping.recordClick(new ClickData(referrer, location));
        }
    }

    public UrlMapping getStats(String shortcode) {
        return store.get(shortcode);
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 6);
        } while (store.containsKey(code));
        return code;
    }
}
