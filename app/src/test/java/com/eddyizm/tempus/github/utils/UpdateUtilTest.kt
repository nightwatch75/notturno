package com.eddyizm.tempus.github.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class UpdateUtilTest {
    @Test fun newerPatchIsAnUpdate() = assertTrue(UpdateUtil.isNewer("4.32.1", "v4.32.2"))
    @Test fun sameVersionIsNotAnUpdate() = assertFalse(UpdateUtil.isNewer("4.32.1", "v4.32.1"))
    @Test fun olderTagIsNotAnUpdate() = assertFalse(UpdateUtil.isNewer("4.33.0", "v4.32.9"))
    @Test fun devSuffixOnTheLocalVersionIsIgnored() = assertTrue(UpdateUtil.isNewer("4.32.1-dev", "v4.32.2"))
    @Test fun forkRevisionAsFourthPartIsNewer() = assertTrue(UpdateUtil.isNewer("4.32.1", "v4.32.1.1"))
    @Test fun missingPartCountsAsZero() = assertFalse(UpdateUtil.isNewer("4.32.1.0", "v4.32.1"))
    @Test fun garbageTagIsNotAnUpdate() = assertFalse(UpdateUtil.isNewer("4.32.1", "nightly"))
}
