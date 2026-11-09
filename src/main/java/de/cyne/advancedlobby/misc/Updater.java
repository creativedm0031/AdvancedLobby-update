package de.cyne.advancedlobby.misc;

import de.cyne.advancedlobby.AdvancedLobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater {

    private long resourceId;
    private String latestVersion;
    private String currentVersion;
    private UpdateResult updateResult;

    public Updater(long resourceId) {
        this.resourceId = resourceId;
        this.currentVersion = AdvancedLobby.getInstance().getDescription().getVersion();
    }

    public enum UpdateResult {
        UPDATE_AVAILABLE, NO_UPDATE, CONNECTION_ERROR
    }

    public void checkLatestVersion() {
        this.updateResult = null;
        this.latestVersion = null;
        try {
            HttpURLConnection httpConnection = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId)
                    .openConnection();
            this.latestVersion = new BufferedReader(new InputStreamReader(httpConnection.getInputStream())).readLine();
        } catch (IOException e) {
            this.setUpdateResult(UpdateResult.CONNECTION_ERROR);
        }
    }

    public void compareVersions() {
        if (this.updateResult == UpdateResult.CONNECTION_ERROR || latestVersion == null) {
            this.setUpdateResult(UpdateResult.CONNECTION_ERROR);
            return;
        }
        if (compare(latestVersion.trim(), currentVersion) > 0) {
            this.setUpdateResult(UpdateResult.UPDATE_AVAILABLE);
            return;
        }
        this.setUpdateResult(UpdateResult.NO_UPDATE);
    }

    private static int compare(String a, String b) {
        String[] pa = a.split("[^0-9]+");
        String[] pb = b.split("[^0-9]+");
        for (int i = 0; i < Math.max(pa.length, pb.length); i++) {
            long x = i < pa.length && !pa[i].isEmpty() ? Long.parseLong(pa[i]) : 0;
            long y = i < pb.length && !pb[i].isEmpty() ? Long.parseLong(pb[i]) : 0;
            if (x != y) return Long.compare(x, y);
        }
        return 0;
    }

    public void run() {
        AdvancedLobby.getInstance().getLogger().info("Searching for an update on 'spigotmc.org'..");

        this.checkLatestVersion();
        this.compareVersions();

        switch (this.updateResult) {
            case UPDATE_AVAILABLE:
                AdvancedLobby.getInstance().getLogger().info("There was a new version found. It is recommended to update. (Visit spigotmc.org)");
                AdvancedLobby.updateAvailable = true;
                break;

            case NO_UPDATE:
                AdvancedLobby.getInstance().getLogger().info("The plugin is up to date.");
                AdvancedLobby.updateAvailable = false;
                break;

            case CONNECTION_ERROR:
                AdvancedLobby.getInstance().getLogger().warning("Could not connect to spigotmc.org. Retrying soon.");
                AdvancedLobby.updateAvailable = false;
                break;

            default:
                AdvancedLobby.getInstance().getLogger().warning("Could not connect to spigotmc.org. Retrying soon.");
                AdvancedLobby.updateAvailable = false;
                break;
        }
    }

    private void setUpdateResult(UpdateResult updateResult) {
        this.updateResult = updateResult;
    }

}