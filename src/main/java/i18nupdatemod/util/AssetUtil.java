package i18nupdatemod.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class AssetUtil {
    private static final String GITHUB_ASSET_ROOT = "https://github.com/flier268/Minecraft-Mod-Traditional-Chinese-Language-Package/releases/latest/download/";

    public static void download(String url, Path localFile) throws IOException, URISyntaxException {
        Log.info("Downloading: %s -> %s", url, localFile);
        FileUtils.copyURLToFile(new URI(url).toURL(), localFile.toFile(),
                10000, 60000);
        Log.debug("Downloaded: %s -> %s", url, localFile);
    }

    public static String getString(String url) throws IOException, URISyntaxException {
        return IOUtils.toString(new URI(url).toURL(), StandardCharsets.UTF_8);
    }

    public static String getFastestUrl() {
        Log.info("Using GitHub asset root: %s", GITHUB_ASSET_ROOT);
        return GITHUB_ASSET_ROOT;
    }
}
