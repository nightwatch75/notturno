package com.eddyizm.tempus.util

import com.eddyizm.tempus.github.utils.UpdateUtil

/** Reads assets/CHANGELOG.md: a "## version" heading, then "- item" lines. The same file feeds the GitHub release notes. */
object Changelog {
    data class Entry(val version: String, val items: List<String>)

    @JvmStatic
    fun parse(text: String): List<Entry> {
        val entries = mutableListOf<Entry>()
        var version: String? = null
        var items = mutableListOf<String>()
        fun close() { version?.let { entries.add(Entry(it, items)) } }
        for (raw in text.lines()) {
            val line = raw.trim()
            when {
                line.startsWith("## ") -> { close(); version = line.removePrefix("## ").trim(); items = mutableListOf() }
                line.startsWith("- ") && version != null -> items.add(line.removePrefix("- ").trim())
            }
        }
        close()
        return entries
    }

    /** The entries after [lastSeen] and up to [current], in file order (newest first). */
    @JvmStatic
    fun since(entries: List<Entry>, lastSeen: String, current: String): List<Entry> =
        entries.filter { UpdateUtil.isNewer(lastSeen, it.version) && !UpdateUtil.isNewer(current, it.version) }

    @JvmStatic
    fun format(entries: List<Entry>): String =
        entries.joinToString("\n\n") { e -> (listOf(e.version) + e.items.map { "• $it" }).joinToString("\n") }
}
