package com.eddyizm.tempus.util

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

/** Turns what a user types in the server editor ("nas:4533", "music.example.com") into a full URL. */
object ServerAddress {
    private val HAS_SCHEME = Regex("^[A-Za-z][A-Za-z0-9+.-]*://")
    private val IPV4 = Regex("^\\d{1,3}(\\.\\d{1,3}){3}$")

    /**
     * Adds the missing scheme: http for hosts that cannot hold a public certificate (IP address, a
     * name without a dot, .local/.lan/.home.arpa), https for everything else. A scheme the user typed is kept.
     */
    @JvmStatic
    fun normalize(input: String): String {
        val text = input.trim()
        if (text.isEmpty() || HAS_SCHEME.containsMatchIn(text)) return text
        val host = text.substringBefore('/').substringBefore(':').lowercase()
        val local = IPV4.matches(host) || !host.contains('.') ||
            host.endsWith(".local") || host.endsWith(".lan") || host.endsWith(".home.arpa")
        return (if (local) "http://" else "https://") + text
    }

    /** The host of [url], to name a server the user left unnamed. */
    @JvmStatic
    fun displayName(url: String): String? = url.toHttpUrlOrNull()?.host
}
