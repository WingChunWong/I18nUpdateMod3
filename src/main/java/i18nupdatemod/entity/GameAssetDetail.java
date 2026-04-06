package i18nupdatemod.entity;

import java.util.List;

public class GameAssetDetail {
    public List<AssetDownloadDetail> downloads;
    public Integer covertPackFormat;
    public Integer minFormat;  // pack.mcmeta min_format，用于 pack_format >= 69
    public Integer maxFormat;  // pack.mcmeta max_format，用于 pack_format >= 69
    public String covertFileName;

    public static class AssetDownloadDetail {
        public String fileName;
        public String fileUrl;
        public String md5Url;
        public String targetVersion;
    }
}
