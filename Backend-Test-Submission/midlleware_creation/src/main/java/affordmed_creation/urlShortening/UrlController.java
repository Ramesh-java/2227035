package affordmed_creation.urlShortening;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shorturls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<?> createShortUrl(@RequestBody Map<String, Object> request) {
        try {
            String url = (String) request.get("url");
            Integer validity = request.get("validity") != null ? (Integer) request.get("validity") : null;
            String shortcode = (String) request.get("shortcode");

            if (url == null || url.isEmpty())
                return ResponseEntity.badRequest().body(Map.of("error", "URL is required"));

            UrlMapping mapping = urlService.createShortUrl(url, validity, shortcode);

            Map<String, Object> response = new HashMap<>();
            response.put("shortLink", "http://localhost:8080/shorturls/" + mapping.getShortcode());
            response.put("expiry", mapping.getExpiry().toString());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{shortcode}")
    public void redirectToOriginal(@PathVariable String shortcode, HttpServletResponse response,
                                   @RequestHeader(value = "Referer", required = false) String referer) throws IOException {
        UrlMapping mapping = urlService.getOriginalUrl(shortcode);
        if (mapping == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short URL not found or expired");
            return;
        }

        urlService.recordClick(shortcode, referer, "Unknown"); // location can be improved
        response.sendRedirect(mapping.getOriginalUrl());
    }

    @GetMapping("/stats/{shortcode}")
    public ResponseEntity<?> getStats(@PathVariable String shortcode) {
        UrlMapping mapping = urlService.getStats(shortcode);
        if (mapping == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Short URL not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("originalUrl", mapping.getOriginalUrl());
        response.put("createdAt", mapping.getCreatedAt().toString());
        response.put("expiry", mapping.getExpiry().toString());
        response.put("totalClicks", mapping.getTotalClicks());
        response.put("clicks", mapping.getClicks());

        return ResponseEntity.ok(response);
    }
}
