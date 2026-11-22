package com.eddyizm.tempus.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QueueKindTest {
    @Test fun queueOfOneAlbumIsAnAlbumQueue() = assertTrue(QueueKind.isAlbumQueue(listOf("a1", "a1", "a1")))
    @Test fun mixedAlbumsAreNotAnAlbumQueue() = assertFalse(QueueKind.isAlbumQueue(listOf("a1", "a2", "a1")))
    @Test fun singleSongIsNotAnAlbumQueue() = assertFalse(QueueKind.isAlbumQueue(listOf("a1")))
    @Test fun songsWithoutAlbumAreNotAnAlbumQueue() = assertFalse(QueueKind.isAlbumQueue(listOf(null, null)))
    @Test fun emptyQueueIsNotAnAlbumQueue() = assertFalse(QueueKind.isAlbumQueue(emptyList()))
}
