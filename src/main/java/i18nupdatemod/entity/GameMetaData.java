package i18nupdatemod.entity;

import java.util.List;

public class GameMetaData {
    public String gameVersions;
    public int packFormat;
    public Integer minFormat;  // 可选，用于 pack_format >= 69 的版本范围
    public Integer maxFormat;  // 可选，用于 pack_format >= 69 的版本范围
    public List<String> convertFrom;
}
