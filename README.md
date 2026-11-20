<p align="center">
  <img alt="Notturno" title="Notturno" src="fastlane/metadata/android/en-US/images/icon.png" width="160">
</p>

<h1 align="center">Notturno</h1>

<p align="center">
  <b>An Android music player for Subsonic-compatible servers</b>
</p>

<div align="center">
<a href="https://www.gnu.org/licenses/gpl-3.0">
    <img src="https://img.shields.io/badge/license-GPL%20v3-2B6DBE.svg?style=flat">
</a>
</div>

---

Notturno streams and downloads music from your own server: Navidrome, Gonic, LMS, Nextcloud Music, Ampache, Airsonic Advanced and others that speak the Subsonic API.
It is a personal fork, built on [Tempus](https://github.com/eddyizm/tempus), and installs next to it without sharing data.

## Features

- Browse by artist, album, genre, decade and playlist; search; instant mix; podcasts; internet radio.
- Streaming, downloads and offline playback, transcoding, gapless playback, ReplayGain, equalizer, home-screen widget.
- Scrobbling to Last.fm and ListenBrainz through your server.
- Multiple servers and libraries, with custom HTTP headers and client certificates (mTLS).
- Android Auto and Chromecast (`tempus` flavor), or a build without Google services (`degoogled` flavor).

Added in Notturno:

- **Radio song covers**: shows the cover of the song on air. It reads the stream title and looks the cover up on MusicBrainz and Cover Art Archive, then Deezer, then iTunes. On by default, switch off in Settings.
- **Shuffle all**: endless random play of the whole library, from a Home button and from the Android Auto tree.
- **Go to album**: a player button (notification, Android Auto) that turns the queue into the album of the song in play. Android Auto shows Like first and Go to album second; change them in Settings, custom buttons.
- **Server editor**: type a bare host or IP (`music.example.com`, `192.168.1.10:4533`) and the scheme is added; the name defaults to the host; Test works before saving.
- **Playback reports**: sends play state to servers that support the OpenSubsonic `playbackReport` extension.
- **Certificate chooser fix** for servers that need a client certificate.

The usage guide is in [USAGE.md](USAGE.md). Notes on how the fork follows upstream, and its fragile spots, are in [FORK.md](FORK.md).

## Build

Notturno is built from source.

```bash
./gradlew assembleTempusDebug       # Android Auto and Chromecast
./gradlew assembleDegoogledDebug    # no Google services
```

The APK is in `app/build/outputs/apk/<flavor>/debug/`. It is signed with the local debug key, so an update installs only over an APK signed with the same key.
Gradle downloads Java 21. Unit tests: `./gradlew testTempusDebugUnitTest`.

## Privacy

The app talks to your server only. The radio cover lookup (on by default, switch it off in Settings) sends the artist and title of the song on air to MusicBrainz, Deezer and iTunes. See [PRIVACY_POLICY.md](PRIVACY_POLICY.md).

## License

GNU General Public License v3.0, see [LICENSE](LICENSE).

## Credits

Notturno is a derivative work. The app exists thanks to [Tempus](https://github.com/eddyizm/tempus) and its contributors, and to [Tempo](https://github.com/CappielloAntonio/tempo), from which Tempus was forked.
Notturno uses its own name and icon.
