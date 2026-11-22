package com.eddyizm.tempus.util

import org.junit.Assert.assertEquals
import org.junit.Test

class ArtistLineTest {
    @Test fun musicShowsArtistAndAlbum() =
        assertEquals("Queen - A Night at the Opera", ArtistLine.compose(Constants.MEDIA_TYPE_MUSIC, "Queen", "A Night at the Opera"))

    @Test fun radioKeepsTheStreamMetadata() =
        assertEquals("Radio Uno", ArtistLine.compose(Constants.MEDIA_TYPE_RADIO, "Radio Uno", "Some album"))

    @Test fun missingAlbumKeepsTheArtist() {
        assertEquals("Queen", ArtistLine.compose(Constants.MEDIA_TYPE_MUSIC, "Queen", null))
        assertEquals("Queen", ArtistLine.compose(Constants.MEDIA_TYPE_MUSIC, "Queen", "  "))
    }

    @Test fun missingArtistStaysEmpty() =
        assertEquals(null, ArtistLine.compose(Constants.MEDIA_TYPE_MUSIC, null, "A Night at the Opera"))

    @Test fun albumAlreadyInTheArtistIsNotRepeated() =
        assertEquals("Queen - A Night at the Opera", ArtistLine.compose(Constants.MEDIA_TYPE_MUSIC, "Queen - A Night at the Opera", "A Night at the Opera"))
}
