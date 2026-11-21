package com.eddyizm.tempus.github.utils;

import com.eddyizm.tempus.BuildConfig;
import com.eddyizm.tempus.github.models.LatestRelease;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateUtil {
    private static final Pattern VERSION = Pattern.compile("(\\d+(?:\\.\\d+)*)");

    public static boolean showUpdateDialog(LatestRelease release) {
        if (release.getTagName() == null) return false;
        return isNewer(BuildConfig.VERSION_NAME, release.getTagName());
    }

    /** True when [remoteTag] ("v4.32.2", "4.32.1.1") is a higher version than [localVersion] ("4.32.1-dev"). A missing part counts as 0. */
    public static boolean isNewer(String localVersion, String remoteTag) {
        int[] local = parts(localVersion);
        int[] remote = parts(remoteTag);
        if (local == null || remote == null) return false;

        for (int i = 0; i < Math.max(local.length, remote.length); i++) {
            int l = i < local.length ? local[i] : 0;
            int r = i < remote.length ? remote[i] : 0;
            if (l != r) return r > l;
        }
        return false;
    }

    private static int[] parts(String version) {
        Matcher m = VERSION.matcher(version);
        if (!m.find()) return null;
        try {
            String[] split = m.group(1).split("\\.");
            int[] out = new int[split.length];
            for (int i = 0; i < split.length; i++) out[i] = Integer.parseInt(split[i]);
            return out;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
