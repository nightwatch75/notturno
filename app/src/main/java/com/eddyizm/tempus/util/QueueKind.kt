package com.eddyizm.tempus.util

object QueueKind {
    /** True when every song of a queue of two or more belongs to the same album. */
    @JvmStatic
    fun isAlbumQueue(albumIds: List<String?>): Boolean =
        albumIds.size >= 2 && albumIds.first() != null && albumIds.all { it == albumIds.first() }
}
