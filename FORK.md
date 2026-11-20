# Tempus mod (personal fork)

Fork of [eddyizm/tempus](https://github.com/eddyizm/tempus), kept as a patch set on top of upstream `development`.
The upstream PR for the radio covers (#1281) was not accepted, so the changes live here.

Default branch: `tempus-mod`.

## What the mod adds

- **Radio song covers**: for internet radio streams, the cover of the song in play is looked up from the ICY title
  (MusicBrainz + Cover Art Archive, then Deezer, then iTunes). Parser: `RadioStreamTitle.kt`, resolver: `RadioTrackCoverResolver.kt`.
- **Shuffle all**: endless random play of the whole library (Home button, and an item in the Android Auto tree on the `tempus` flavor).
  `MediaManager.startGlobalShuffle` / `continueGlobalShuffle`.
- **Go to album**: a media-session button (notification, Android Auto) that rebuilds the queue as the album of the playing song.
  Choose where it sits in Settings, first/second custom button: Android Auto shows only 4 custom buttons.

## Following upstream

```bash
git fetch origin
git rebase origin/development      # conflicts are usually in BaseSessionCallback, MediaManager, BaseMediaService
./gradlew testTempusDebugUnitTest testDegoogledDebugUnitTest
./gradlew assembleTempusDebug
git push --force-with-lease fork tempus-mod
```

`origin` is upstream, `fork` is this repository. Sync from time to time, or when a release of interest appears.
Before a rebase, keep a safety branch: `git branch backup/pre-<upstream version> tempus-mod`.

## Notes

- The APK is a debug build, signed with the local debug key: updates install only over APKs signed with the same key.
- The radio cover lookup sends artist and title to the three services above (see `PRIVACY_POLICY.md`).
