<p align="center">
  <img alt="Notturno" title="Notturno" src="fastlane/metadata/android/en-US/images/icon.png" width="160">
</p>

<h1 align="center">Notturno</h1>

<p align="center">
  <b>A personal Android client for Subsonic servers, forked from <a href="https://github.com/eddyizm/tempus">Tempus</a></b>
</p>

<div align="center">
<a href="https://www.gnu.org/licenses/gpl-3.0">
    <img src="https://img.shields.io/badge/license-GPL%20v3-2B6DBE.svg?style=flat">
</a>
</div>

---

**Notturno** plays your music from a Subsonic-compatible server (Navidrome, Gonic, LMS, Nextcloud Music, Ampache, Airsonic Advanced and more).
It is a personal fork of [**Tempus**](https://github.com/eddyizm/tempus) by eddyizm, which is itself a fork of [Tempo](https://github.com/CappielloAntonio/tempo).
Almost everything in the app is their work: this repository only adds a small set of changes on top of upstream `development`.
If you want the original app, with its releases, F-Droid and IzzyOnDroid packages, use [Tempus](https://github.com/eddyizm/tempus).

Notturno installs next to Tempus (different application id) and does not share its data.

## What Notturno adds

- **Radio song covers**: for internet radio, shows the cover of the song on air. It reads the ICY stream title and looks the cover up on MusicBrainz and Cover Art Archive, then Deezer, then iTunes. Off by default, in Settings.
- **Shuffle all**: endless random play of the whole library, from a Home button and from an item in the Android Auto tree.
- **Go to album**: a player button (notification, Android Auto) that turns the queue into the album of the song in play. Choose its place in Settings, custom buttons.
- **Server editor**: type a bare host or IP (`music.example.com`, `192.168.1.10:4533`) and the scheme is added; the name defaults to the host; the Test button works before saving.
- **From upstream pull requests not yet released**: mTLS certificate chooser fix ([#1338](https://github.com/eddyizm/tempus/pull/1338)) and OpenSubsonic `playbackReport` ([#1285](https://github.com/eddyizm/tempus/pull/1285)).
- **Name and icon**: new name, application id and launcher icon.

Details, upstream sync procedure and known fragile spots are in [FORK.md](FORK.md).

## What comes from Tempus

Subsonic integration, browsing and search, streaming and downloads, playlists, gapless playback, ReplayGain, scrobbling to Last.fm and ListenBrainz,
podcasts, radio, instant mix, transcoding, multiple libraries, equalizer, widget, Android Auto and Chromecast (`tempus` flavor),
and the translations. See the upstream [README](https://github.com/eddyizm/tempus#readme) and the [usage guide](USAGE.md).

## Build

Notturno is built from source. There are no store releases.

```bash
./gradlew assembleTempusDebug       # Android Auto and Chromecast
./gradlew assembleDegoogledDebug    # no Google services
```

The APK is in `app/build/outputs/apk/<flavor>/debug/`. It is signed with the local debug key, so an update installs only over an APK signed with the same key.
Java 21 is downloaded by Gradle. Unit tests: `./gradlew testTempusDebugUnitTest`.

## Privacy

The app talks to your server only. The optional radio cover lookup sends the artist and title of the song on air to MusicBrainz, Deezer and iTunes. See [PRIVACY_POLICY.md](PRIVACY_POLICY.md).

## License

Notturno is released under the [GNU General Public License v3.0](LICENSE), like Tempus.

## Credits

- [eddyizm](https://github.com/eddyizm) and the [Tempus contributors](https://github.com/eddyizm/tempus/graphs/contributors), for the app this is built on, including [tvillega](https://github.com/tvillega), [herrerad85](https://github.com/herrerad85) and [MaFo-28](https://github.com/MaFo-28).
- [CappielloAntonio](https://github.com/CappielloAntonio), creator of [Tempo](https://github.com/CappielloAntonio/tempo), from which Tempus was forked (v3.9.0).
- [SeattleGuy](https://github.com/SeattleGuy), for the Tempus logo. Notturno uses its own icon.
