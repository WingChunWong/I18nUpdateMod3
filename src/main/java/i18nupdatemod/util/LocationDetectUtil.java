package i18nupdatemod.util;

import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LocationDetectUtil {
    private static final String GEO_API_URL = "https://mips.kugou.com/check/iscn";
    private static Boolean cached = null;

    public static boolean isMainlandChina() {
        if (cached != null) {
            return cached;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(GEO_API_URL).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int code = conn.getResponseCode();
            
            if (code == 200) {
                String response = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8).trim();
                cached = "1".equals(response) || "true".equalsIgnoreCase(response);
                Log.info("Location Detected: " + (cached ? "Inside mainland China" : "Outside mainland China"));
                return cached;
            }
        } catch (Exception e) {
            Log.debug("Location detection failed: " + e.getMessage());
        }
        
        cached = false;
        return false;
    }

    public static void resetCache() {
        cached = null;
    }
}
