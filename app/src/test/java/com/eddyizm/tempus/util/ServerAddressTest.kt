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
