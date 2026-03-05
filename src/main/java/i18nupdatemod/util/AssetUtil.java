package i18nupdatemod.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class AssetUtil {
    private static final String CFPA_ASSET_ROOT = "http://downloader1.meitangdehulu.com:22943/";
    private static final List<String> MIRRORS;

    static {
        // 镜像地址可以改成服务器下发
        MIRRORS = new ArrayList<>();
        MIRRORS.add("https://raw.githubusercontent.com/");
        // 此镜像源维护者：502y
        MIRRORS.add("http://8.137.167.65:64684/");
    }

    public static void download(String url, Path localFile) throws IOException, URISyntaxException {
        Log.info("Downloading: %s -> %s", url, localFile);
        FileUtils.copyURLToFile(new URI(url).toURL(), localFile.toFile(),
                (int) TimeUnit.SECONDS.toMillis(3), (int) TimeUnit.SECONDS.toMillis(33));
        Log.debug("Downloaded: %s -> %s", url, localFile);
    }

    public static String getString(String url) throws IOException, URISyntaxException {
        return IOUtils.toString(new URI(url).toURL(), StandardCharsets.UTF_8);
    }

    public static String getFastestUrl() {
        List<String> urls = new ArrayList<>();
        
        // 根据地理位置选择源列表
        if (LocationDetectUtil.isMainlandChina()) {
            // 中国大陆：测速选择最快的国内源
            urls.addAll(MIRRORS);
            urls.add(CFPA_ASSET_ROOT);
            Log.info("Inside mainland China: Testing mirrors...");
        } else {
            // 海外用户：直接使用 GitHub 源
            urls.add("https://raw.githubusercontent.com/");
            Log.info("Outside mainland China: Using GitHub source...");
        }

        ExecutorService executor = Executors.newFixedThreadPool(Math.max(urls.size(), 10));
        try {
            List<CompletableFuture<String>> futures = new ArrayList<>();
            for (String url : urls) {
                futures.add(CompletableFuture.supplyAsync(() -> {
                    try {
                        return testUrlConnection(url);
                    } catch (IOException e) {
                        return null;
                    }
                }, executor));
            }

            String fastest = null;
            while (!futures.isEmpty()) {
                fastest = (String) CompletableFuture.anyOf(futures.toArray(new CompletableFuture[0])).join();
                futures.removeIf(CompletableFuture::isDone);
                if (fastest != null) {
                    for (CompletableFuture<String> f : futures) {
                        f.cancel(true);
                    }
                    Log.info("Using fastest url: %s", fastest);
                    return fastest;
                }
            }

            Log.info("All sources unreachable, using CFPA_ASSET_ROOT as fallback");
            return CFPA_ASSET_ROOT;

        } finally {
            executor.shutdownNow();
        }
    }

    private static String testUrlConnection(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("HEAD");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(5000);
        conn.connect();
        int code = conn.getResponseCode();
        if (code >= 200 && code < 300) {
            return url;
        }
        Log.debug("URL unreachable: %s, code: %d", url, code);
        throw new IOException("URL unreachable: " + url);
    }

    @NotNull
    public static Map<String, String> getGitIndex() {
        try {
            URL index_url = new URL("https://raw.githubusercontent.com/CFPAOrg/Minecraft-Mod-Language-Package/refs/heads/index/version-index.json");
            HttpURLConnection httpConn = (HttpURLConnection) index_url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(5000);
            httpConn.setReadTimeout(5000);

            try (InputStreamReader reader = new InputStreamReader(httpConn.getInputStream())) {
                Type mapType = new TypeToken<Map<String, String>>() {
                }.getType();
                return new Gson().fromJson(reader, mapType);
            } finally {
                httpConn.disconnect();
            }
        } catch (Exception ignore) {
            return new HashMap<>();
        }
    }
}
