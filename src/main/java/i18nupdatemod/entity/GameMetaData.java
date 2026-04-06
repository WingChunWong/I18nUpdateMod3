package i18nupdatemod.entity;

import java.util.List;

public class GameMetaData {
    public String gameVersions;
    public Integer packFormat;     // 旧格式，用于 1.21.8 及之前
    public Integer minFormat;      // 新格式，用于 1.21.9 及之后
    public Integer maxFormat;      // 新格式，用于 1.21.9 及之后
    public List<String> convertFrom;
}
