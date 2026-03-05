package i18nupdatemod.util;

import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class LocationDetectUtil {
    private static Boolean cached = null;

    private static final List<GeoApi> GEO_APIS = Arrays.asList(
            new GeoApi("Kugou", "https://mips.kugou.com/check/iscn", 
                    response -> "1".equals(response.trim()) || "true".equalsIgnoreCase(response.trim())),
            new GeoApi("IP.SB", "https://api.ip.sb/geoip",
                    response -> response.contains("\"country_code\":\"CN\"") || response.contains("\"country_code\": \"CN\""))
    );

    public static boolean isMainlandChina() {
        if (cached != null) {
            return cached;
        }

        for (GeoApi api : GEO_APIS) {
            Boolean result = tryApi(api);
            if (result != null) {
                cached = result;
                return cached;
            }
        }
        
        // 所有 API 都失败，默认为 false
        cached = false;
        return false;
    }

    private static Boolean tryApi(GeoApi api) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(api.url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int code = conn.getResponseCode();
            
            if (code == 200) {
                String response = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);
                boolean isChina = api.parser.test(response);
                Log.info("Location Detected (" + api.name + " API): " + (isChina ? "Inside mainland China" : "Outside mainland China"));
                return isChina;
            }
        } catch (Exception e) {
            Log.debug(api.name + " API detection failed: " + e.getMessage());
        }
        return null;
    }

    private static class GeoApi {
        final String name;
        final String url;
        final Predicate<String> parser;

        GeoApi(String name, String url, Predicate<String> parser) {
            this.name = name;
            this.url = url;
            this.parser = parser;
        }
    }

    public static void resetCache() {
        cached = null;
    }
}
