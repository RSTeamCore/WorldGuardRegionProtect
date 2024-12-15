package net.ritasister.wgrp.util.utility;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateDownloaderGitHub {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;
    private static final String LATEST_RELEASE_URL = "https://api.github.com/repos/{owner}/{repo}/releases/latest";
    private static final String JAR_DOWNLOAD_URL_PATTERN = "https://github.com/{owner}/{repo}/releases/download/{tag}/{fileName}";

    public UpdateDownloaderGitHub(WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    public void downloadLatestJar(String owner, String repo, String fileName, String savePath) {
        try {
            final String latestReleaseTag = getLatestReleaseTag(owner, repo);
            if (latestReleaseTag != null) {
                final String jarDownloadUrl = JAR_DOWNLOAD_URL_PATTERN.replace("{owner}", owner).replace("{repo}", repo).replace("{tag}", latestReleaseTag).replace("{fileName}", fileName);
                wgrpPlugin.getLogger().info("Starting download from URL: " + jarDownloadUrl);
                downloadFile(jarDownloadUrl, savePath);
                wgrpPlugin.getLogger().info("Download process completed successfully.");
            } else {
                wgrpPlugin.getLogger().warn("Failed to get the latest release tag. Aborting download.");
            }
        } catch (Exception e) {
            wgrpPlugin.getLogger().severe("Error while downloading the latest jar: " + e.getMessage());
        }
    }

    private String getLatestReleaseTag(String owner, String repo) throws IOException {
        final String url = LATEST_RELEASE_URL.replace("{owner}", owner).replace("{repo}", repo);
        final StringBuilder response = new StringBuilder();
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
            connection.setRequestMethod("GET");

            try (BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream())) {
                final byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    response.append(new String(buffer, 0, bytesRead));
                }
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return parseJsonForTag(response.toString());
    }

    private @Nullable String parseJsonForTag(@NotNull String json) {
        final int tagNameIndex = json.indexOf("\"tag_name\"");
        if (tagNameIndex != -1) {
            final int start = json.indexOf("\"", tagNameIndex + 11) + 1;
            final int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }
        return null;
    }

    private void downloadFile(String fileUrl, String savePath) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream()); FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {

            final byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, bytesRead, bytesRead);
            }
            wgrpPlugin.getLogger().info("Saving file to path: " + savePath);
            wgrpPlugin.getLogger().info("File downloaded successfully: " + savePath);
        }
    }
}
