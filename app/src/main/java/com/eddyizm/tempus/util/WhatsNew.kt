package com.eddyizm.tempus.util

import android.content.Context
import android.util.Log
import com.eddyizm.tempus.BuildConfig
import com.eddyizm.tempus.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/** Shows the changes made since the version the user saw last. A new install only records the version. */
object WhatsNew {
    private const val TAG = "WhatsNew"

    private fun isUpdate(context: Context): Boolean = try {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        info.lastUpdateTime > info.firstInstallTime
    } catch (e: Exception) {
        false
    }

    @JvmStatic
    fun showIfNeeded(context: Context) {
        val saved = Preferences.getLastSeenVersion()
        val current = BuildConfig.VERSION_NAME
        if (saved == current) return
        Preferences.setLastSeenVersion(current)
        // No saved version: a new install has nothing to show, an update from before this window existed shows everything.
        val lastSeen = saved ?: if (isUpdate(context)) "0" else return

        val entries = try {
            val text = context.assets.open("CHANGELOG.md").bufferedReader().use { it.readText() }
            Changelog.since(Changelog.parse(text), lastSeen, current)
        } catch (e: Exception) {
            Log.w(TAG, "No changelog", e)
            return
        }
        if (entries.isEmpty()) return

        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.whats_new_title))
            .setMessage(Changelog.format(entries))
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}
