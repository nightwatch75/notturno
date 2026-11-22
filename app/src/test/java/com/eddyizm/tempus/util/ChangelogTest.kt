package com.eddyizm.tempus.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ChangelogTest {
    private val text = """
        # Changelog

        ## 4.32.1.3
        - Go to artist button
        - Server editor with port

        ## 4.32.1.2
        - No mixes in Android Auto Home

        ## 4.32.1.1
        - First signed release
    """.trimIndent()

    @Test fun parsesVersionsNewestFirstWithTheirItems() {
        val entries = Changelog.parse(text)
        assertEquals(listOf("4.32.1.3", "4.32.1.2", "4.32.1.1"), entries.map { it.version })
        assertEquals(listOf("Go to artist button", "Server editor with port"), entries[0].items)
    }

    @Test fun sinceKeepsOnlyVersionsAfterTheLastSeenOne() {
        val shown = Changelog.since(Changelog.parse(text), lastSeen = "4.32.1.1", current = "4.32.1.3")
        assertEquals(listOf("4.32.1.3", "4.32.1.2"), shown.map { it.version })
    }

    @Test fun sinceIgnoresVersionsNewerThanTheRunningApp() {
        val shown = Changelog.since(Changelog.parse(text), lastSeen = "4.32.1.1", current = "4.32.1.2")
        assertEquals(listOf("4.32.1.2"), shown.map { it.version })
    }

    @Test fun nothingNewWhenLastSeenIsCurrent() =
        assertTrue(Changelog.since(Changelog.parse(text), lastSeen = "4.32.1.3", current = "4.32.1.3").isEmpty())

    @Test fun devSuffixOnTheCurrentVersionIsIgnored() =
        assertTrue(Changelog.since(Changelog.parse(text), lastSeen = "4.32.1.1", current = "4.32.1-dev").isEmpty())

    @Test fun formatListsVersionsAndBullets() =
        assertEquals("4.32.1.2\n• No mixes in Android Auto Home", Changelog.format(Changelog.since(Changelog.parse(text), "4.32.1.1", "4.32.1.2")))

    @Test fun emptyOrBrokenTextGivesNoEntries() {
        assertTrue(Changelog.parse("").isEmpty())
        assertTrue(Changelog.parse("just words\n- orphan bullet").isEmpty())
    }
}
