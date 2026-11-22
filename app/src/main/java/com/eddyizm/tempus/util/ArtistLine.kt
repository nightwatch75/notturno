package com.eddyizm.tempus.util

import androidx.media3.common.MediaMetadata

/**
 * The artist line of a song shows the album too ("Queen - A Night at the Opera"), so Android Auto displays it.
 * The plain artist stays in the extras, where history, scrobbling and the favorites read it.
 */
object ArtistLine {
    @JvmStatic
    fun compose(type: String?, artist: String?, album: String?): String? {
        if (type != Constants.MEDIA_TYPE_MUSIC || artist.isNullOrBlank() || album.isNullOrBlank()) return artist
        if (artist.endsWith(album)) return artist
        return "$artist - $album"
    }

    /** The artist without the album, for the app's own screens and for comparisons. Radio keeps its stream metadata. */
    @JvmStatic
    fun plainArtist(metadata: MediaMetadata): String? {
        val extras = metadata.extras
        if (extras?.getString("type") == Constants.MEDIA_TYPE_MUSIC) {
            extras.getString("artist")?.let { return it }
        }
        return metadata.artist?.toString()
    }
}
