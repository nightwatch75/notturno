package com.eddyizm.tempus.github;

import com.eddyizm.tempus.github.api.release.ReleaseClient;

public class Github {
    private static final String OWNER = "nightwatch75";
    private static final String REPO = "notturno";
    private ReleaseClient releaseClient;

    public ReleaseClient getReleaseClient() {
        if (releaseClient == null) {
            releaseClient = new ReleaseClient(this);
        }

        return releaseClient;
    }

    public String getUrl() {
        return "https://api.github.com/";
    }

    public static String getOwner() {
        return OWNER;
    }

    public static String getRepo() {
        return REPO;
    }
}
