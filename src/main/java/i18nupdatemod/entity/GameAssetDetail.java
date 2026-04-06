package i18nupdatemod.entity;

import java.util.List;

public class GameAssetDetail {
    public List<AssetDownloadDetail> downloads;
    public Integer covertPackFormat;   // 旧格式 pack_format
    public Integer minFormat;          // 新格式 min_format
    public Integer maxFormat;          // 新格式 max_format
    public String covertFileName;

    public static class AssetDownloadDetail {
        public String fileName;
        public String fileUrl;
        public String md5Url;
        public String targetVersion;
    }
}
