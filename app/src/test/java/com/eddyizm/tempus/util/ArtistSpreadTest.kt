package com.eddyizm.tempus.util

import org.junit.Assert.assertEquals
import org.junit.Test

class ArtistSpreadTest {
    private fun spread(artists: List<String?>, previous: String? = null) =
        ArtistSpread.apply(artists, previous) { it }

    @Test
    fun noTwoNeighboursShareAnArtistWhenPossible() {
        val out = spread(listOf("Queen", "Queen", "Genesis", "Queen", "Yes", "Genesis"))
        assertEquals(6, out.size)
        out.zipWithNext().forEach { (a, b) -> assert(a != b) { "$out" } }
    }

    @Test
    fun firstSongDiffersFromThePreviousArtist() {
        val out = spread(listOf("Queen", "Queen", "Genesis"), previous = "Queen")
        assertEquals("Genesis", out.first())
    }

    @Test
    fun keepsEverySongAndFallsBackWhenOnlyOneArtistIsLeft() {
        val out = spread(listOf("Queen", "Queen", "Queen"), previous = "Queen")
        assertEquals(listOf("Queen", "Queen", "Queen"), out)
    }

    @Test
    fun comparesArtistsIgnoringCase() {
        val out = spread(listOf("queen", "QUEEN", "Genesis"), previous = null)
        assertEquals("Genesis", out[1])
    }
}
