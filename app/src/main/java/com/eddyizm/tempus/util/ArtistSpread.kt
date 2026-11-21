package com.eddyizm.tempus.util

object ArtistSpread {
    /**
     * Reorders [items] so that neighbours do not share an artist, the first one included against [previousArtist].
     * Greedy: takes the first item whose artist differs, or the first left when none does.
     */
    @JvmStatic
    fun <T> apply(items: List<T>, previousArtist: String?, artistOf: (T) -> String?): List<T> {
        val left = items.toMutableList()
        val out = ArrayList<T>(items.size)
        var last = previousArtist?.lowercase()
        while (left.isNotEmpty()) {
            val next = left.firstOrNull { artistOf(it)?.lowercase() != last } ?: left.first()
            left.remove(next)
            out.add(next)
            last = artistOf(next)?.lowercase()
        }
        return out
    }
}
