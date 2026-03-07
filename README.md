# 自動漢化更新模組Ⅲ

[![Version](https://img.shields.io/github/v/release/WingChunWong/I18nUpdateMod3?label=&logo=V&labelColor=E1F5FE&color=5D87BF&style=for-the-badge)](https://github.com/WingChunWong/I18nUpdateMod3/tags)
[![License](https://img.shields.io/github/license/WingChunWong/I18nUpdateMod3?label=&logo=c&style=for-the-badge&color=A8B9CC&labelColor=455A64)](https://github.com/WingChunWong/I18nUpdateMod3/blob/main/LICENSE)
[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/WingChunWong/I18nUpdateMod3/beta.yml?style=for-the-badge&label=&logo=Gradle&labelColor=388E3C)](https://github.com/WingChunWong/I18nUpdateMod3/actions)
[![Star](https://img.shields.io/github/stars/WingChunWong/I18nUpdateMod3?label=&logo=GitHub&labelColor=black&color=FAFAFA&style=for-the-badge)](https://github.com/WingChunWong/I18nUpdateMod3/stargazers)
[![Minecraft](https://cf.way2muchnoise.eu/versions/Minecraft_I18nUpdateMod_all.svg?badge_style=for_the_badge)](https://github.com/WingChunWong/I18nUpdateMod3)

更現代化的自動漢化更新模組。

「[Minecraft 模組繁體中文語言包（Minecraft Mod Traditional Chinese Language Package）](https://github.com/flier268/Minecraft-Mod-Traditional-Chinese-Language-Package)」是由「[CFPAOrg](http://cfpa.team/)」團隊維護，並由「[flier268](https://github.com/flier268)」轉換為繁體中文的「自動漢化資源包」，可將部分 Mod 內文翻譯為繁體中文。  
本模組用於自動下載、更新與套用「Minecraft 模組繁體中文語言包」。

## 支援的版本

- Minecraft：1.6.1~1.21.11 皆支援
- 模組載入器：MinecraftForge、NeoForge、Fabric、Quilt 皆支援
- Java：8~21 皆支援

只要將本模組的 jar 檔放入 mods 資料夾即可。模組本體與主流 Minecraft 版本、模組載入器及 Java 版本皆相容，不需要額外做版本區隔。

## 支援的「簡體中文資源包」

為了盡可能提升實用性，目前本模組會依遊戲版本自動下載、合併與轉換「簡體中文資源包」。

- 官方資源：1.10.2、1.12.2、1.16、1.18、1.19、1.20、1.21
- 合併轉換：會合併並轉換較新版本的部分資源包，盡可能擴大支援範圍
- 特別說明：1.13 起語言檔改為 JSON 格式，因此無法將 1.12.2 的資源包用於 1.13 以上版本，反之亦然

## 開發環境

請使用 Java 8 以上版本的 JDK 進行建置。
```shell
gradle clean shadowJar
```