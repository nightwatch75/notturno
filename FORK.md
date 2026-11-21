# Notturno (personal fork of Tempus)

Fork of [eddyizm/tempus](https://github.com/eddyizm/tempus), kept as a patch set on top of upstream `development`.
The upstream PR for the radio covers (#1281) was not accepted, so the changes live here.

Default branch: `main`.

## What the mod adds

- **Radio song covers**: for internet radio streams, the cover of the song in play is looked up from the ICY title
  (MusicBrainz + Cover Art Archive, then Deezer, then iTunes). Parser: `RadioStreamTitle.kt`, resolver: `RadioTrackCoverResolver.kt`.
- **Shuffle all**: endless random play of the whole library (Home button, and an item in the Android Auto tree on the `tempus` flavor).
  `MediaManager.startGlobalShuffle` / `continueGlobalShuffle`.
- **Go to album**: a media-session button (notification, Android Auto) that rebuilds the queue as the album of the playing song.
  Choose where it sits in Settings, first/second custom button: Android Auto shows only 4 custom buttons.
- **Server editor** (`LoginServerFragment.kt`, `util/ServerAddress.kt`): address first, scheme added when missing, name defaults to the host, "Advanced" section, Test works for a new server.
- **Upstream PRs applied before release**: #1338 (mTLS certificate chooser) and #1285 (OpenSubsonic `playbackReport`).

## Following upstream

```bash
git fetch origin
git rebase origin/development      # conflicts are usually in BaseSessionCallback, MediaManager, BaseMediaService
./gradlew testTempusDebugUnitTest testDegoogledDebugUnitTest
./gradlew assembleTempusDebug
git push --force-with-lease fork main
```

`origin` is upstream, `fork` is this repository. Sync from time to time, or when a release of interest appears.
Before a rebase, keep a safety branch: `git branch backup/pre-<upstream version> main`.

## Notes

- App name "Notturno", application id `com.nightwatch75.notturno` (`.degoogled.notturno` and `.play.notturno` for the other flavors, `.debug` suffix on debug builds). It installs next to the official Tempus. The code keeps the `com.eddyizm.tempus` package so rebases stay clean.
- Icon: moon and eye with a music note, purple. Vector files `ic_launcher_*` in `app/src/main/res/drawable`.
- The APK is a debug build, signed with the local debug key: updates install only over APKs signed with the same key.
- The radio cover lookup sends artist and title to the three services above (see `PRIVACY_POLICY.md`).

## Fragile spots

Check these first after a rebase or when something breaks.

- **`LoginServerFragment.kt` and `fragment_login_server.xml`**: upstream (tvillega) is still reworking the login editor. Expect conflicts; keep our changes small.
- **#1285 `playbackReport`**: merged by hand into `BaseMediaService` (`setPlayer` is wrapped in a try/finally, `onPlaybackStateChanged` and `onPositionDiscontinuity` carry extra calls). Never tested against a real server that supports the extension.
- **#1338 and #1285 are cherry-picked**: when upstream merges them, the rebase will conflict on the duplicate commits. Drop ours with `git rebase --skip` and keep the upstream version.
- **`BaseSessionCallback`**: Go to album works only while `customCommandGotoAlbum` is in `customLayoutCommandButtons`, which feeds `mediaNotificationSessionCommands`. A button missing there is silently hidden.
- **Shuffle all** recognizes its songs by id only (`globalShuffleIds`): another queue that ends with one of those songs also continues at random.
- **Radio covers** depend on three external services. MusicBrainz allows 1 request per second and wants a descriptive User-Agent (`Notturno/<version> (repo URL)`).
- **Application id vs namespace**: the namespace stays `com.eddyizm.tempus`, only `applicationId` changed. Code must use `BuildConfig.APPLICATION_ID`, never a literal. The instrumented test `CastArtworkConverterTest` hard-codes the debug authority.
- **Icons**: the `degoogled` flavor no longer has its own logo copies. If upstream adds drawables there again, the red Tempus logo comes back.
- **Debug signing**: the APK is signed with the local debug key. Another machine has another key, so it cannot update the installed app.
- **Local scan test**: `RadioCoverScan.kt` is excluded from the build; `cover-check/scan/stations.json` is renamed to `stations.off` around Gradle runs.

## Releases and updates

- **Signing**: `app/build.gradle` reads `NOTTURNO_STORE_FILE`, `NOTTURNO_STORE_PASSWORD`, `NOTTURNO_KEY_ALIAS`, `NOTTURNO_KEY_PASSWORD` from `~/.gradle/gradle.properties` or the environment. Without them the release APK is unsigned. Keep the keystore and its password backed up: a lost key means no more updates over installed APKs.
- **Local release**: `./gradlew assembleTempusRelease -PnotturnoVersion=4.32.1.1 -PnotturnoVersionCode=1001`.
- **Public release**: push a tag `vX.Y.Z[.N]`. `.github/workflows/notturno_release.yml` builds and signs `tempus` and `degoogled`, and publishes them (not a draft). It needs the repo secrets `NOTTURNO_KEYSTORE_BASE64`, `NOTTURNO_STORE_PASSWORD`, `NOTTURNO_KEY_ALIAS`, `NOTTURNO_KEY_PASSWORD`. The upstream release and prerelease workflows are disabled on the fork with `gh workflow disable`.
- **Version rules**: `versionCode` must grow with every release (CI uses 1000 + run number). A fork-only release adds a fourth part to the upstream version (`v4.32.1.1`).
- **Update check** (`tempus` flavor only): `UpdateUtil.isNewer` compares the app version with the tag of the latest published release of `nightwatch75/notturno`. Draft releases are invisible to it.
- **Fragile**: the `versionCode` and `versionName` lines in `app/build.gradle` change on every upstream version bump, so a rebase conflicts there. Keep our `findProperty` form.
- **Android Auto tree** (`MediaBrowserTree.kt`, `tempus` flavor): Quick mix, My mix, Discovery mix and Random are not added to Home, to the "For you" bundle or to the Tracks bundle. The nodes and the `allFunctions` list stay as upstream: the tab choices are saved as indexes, so removing an entry would shift them. If upstream edits these lines, keep the removed `addChild` calls out.

