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

    data class Parts(val scheme: String, val host: String, val port: String)

    private val URL_PARTS = Regex("^(?:([A-Za-z][A-Za-z0-9+.-]*)://)?([^/:]*)(?::(\\d+))?(.*)$")

    /**
     * Joins the three fields of the editor. A scheme or a port typed inside [host] (a pasted URL) wins over the
     * dropdown and the port field. A path stays after the port. Blank host gives "".
     */
    @JvmStatic
    fun build(scheme: String, host: String, port: String): String {
        val m = URL_PARTS.matchEntire(host.trim()) ?: return ""
        val name = m.groupValues[2]
        if (name.isEmpty()) return ""
        val usedScheme = m.groupValues[1].ifEmpty { scheme }
        val usedPort = m.groupValues[3].ifEmpty { port.trim() }
        val path = m.groupValues[4].trimEnd('/')
        return usedScheme + "://" + name + (if (usedPort.isNotEmpty()) ":$usedPort" else "") + path
    }

    /** Splits a saved address for the editor: the host keeps its path, a missing scheme counts as https. */
    @JvmStatic
    fun split(address: String): Parts {
        val m = URL_PARTS.matchEntire(address.trim()) ?: return Parts("https", address.trim(), "")
        return Parts(m.groupValues[1].ifEmpty { "https" }, m.groupValues[2] + m.groupValues[4].trimEnd('/'), m.groupValues[3])
    }

    /** The host of [url], to name a server the user left unnamed. */
    @JvmStatic
    fun displayName(url: String): String? = url.toHttpUrlOrNull()?.host
}
