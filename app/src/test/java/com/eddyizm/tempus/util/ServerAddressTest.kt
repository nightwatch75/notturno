package com.eddyizm.tempus.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ServerAddressTest {
    @Test fun keepsAFullUrl() =
        assertEquals("https://music.example.com/navidrome", ServerAddress.normalize("https://music.example.com/navidrome"))

    @Test fun trimsSpaces() =
        assertEquals("https://music.example.com", ServerAddress.normalize("  music.example.com \n"))

    @Test fun publicHostnameGetsHttps() =
        assertEquals("https://music.example.com", ServerAddress.normalize("music.example.com"))

    @Test fun ipAddressWithPortGetsHttp() =
        assertEquals("http://192.168.1.10:4533", ServerAddress.normalize("192.168.1.10:4533"))

    @Test fun bareHostnameAndLocalDomainsGetHttp() {
        assertEquals("http://nas", ServerAddress.normalize("nas"))
        assertEquals("http://nas.local:4533", ServerAddress.normalize("nas.local:4533"))
    }

    @Test fun schemeIsCaseInsensitiveAndKept() =
        assertEquals("HTTP://nas", ServerAddress.normalize("HTTP://nas"))

    @Test fun blankStaysBlank() = assertEquals("", ServerAddress.normalize("   "))

    @Test fun displayNameIsTheHost() {
        assertEquals("music.example.com", ServerAddress.displayName("https://music.example.com:8443/navidrome"))
        assertEquals("192.168.1.10", ServerAddress.displayName("http://192.168.1.10:4533"))
    }

    @Test fun displayNameOfGarbageIsNull() = assertNull(ServerAddress.displayName("not a url"))
}

class ServerAddressPartsTest {
    @org.junit.Test fun buildsFromSchemeHostAndPort() =
        org.junit.Assert.assertEquals("http://minchius.ddns.net:4533", ServerAddress.build("http", "minchius.ddns.net", "4533"))

    @org.junit.Test fun emptyPortLeavesItOut() =
        org.junit.Assert.assertEquals("https://music.example.com", ServerAddress.build("https", "music.example.com", ""))

    @org.junit.Test fun portGoesBeforeAPath() =
        org.junit.Assert.assertEquals("https://nas.example.com:4533/navidrome", ServerAddress.build("https", "nas.example.com/navidrome", "4533"))

    @org.junit.Test fun pastedUrlOverridesSchemeAndPort() =
        org.junit.Assert.assertEquals("https://music.example.com:8443", ServerAddress.build("http", "https://music.example.com:8443/", "4533"))

    @org.junit.Test fun portTypedInTheHostWinsOverThePortField() =
        org.junit.Assert.assertEquals("http://192.168.1.10:4600", ServerAddress.build("http", "192.168.1.10:4600", "4533"))

    @org.junit.Test fun blankHostGivesEmpty() =
        org.junit.Assert.assertEquals("", ServerAddress.build("http", "  ", "4533"))

    @org.junit.Test fun splitsASavedAddress() {
        val parts = ServerAddress.split("http://minchius.ddns.net:4533")
        org.junit.Assert.assertEquals(ServerAddress.Parts("http", "minchius.ddns.net", "4533"), parts)
    }

    @org.junit.Test fun splitKeepsThePathInTheHost() =
        org.junit.Assert.assertEquals(ServerAddress.Parts("https", "nas.example.com/navidrome", ""), ServerAddress.split("https://nas.example.com/navidrome"))

    @org.junit.Test fun splitWithoutSchemeDefaultsToHttps() =
        org.junit.Assert.assertEquals(ServerAddress.Parts("https", "music.example.com", ""), ServerAddress.split("music.example.com"))
}
