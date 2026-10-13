package com.ven.app.ui.screens.settings

import androidx.compose.runtime.Immutable
import com.ven.app.ui.components.feature.HistoryEntry
import com.ven.app.ui.theme.ThemeMode

/**
 * Model data profil untuk layar pengaturan profil pengguna.
 */
@Immutable
data class ProfileSettingsData(
    val username: String = "gra._.aph",
    val name: String = "Terra Faqih Satria Madjid",
    val phone: String = "082387593452",
    val description: String = "I’m a fullstack game developer",
    val interests: Set<String> = setOf("UI/UX", "3D", "Animation", "Game Dev"),
)

/**
 * UI State untuk seluruh modul Settings.
 */
@Immutable
data class SettingsUiState(
    val profileData: ProfileSettingsData = ProfileSettingsData(),
    val themeMode: ThemeMode = ThemeMode.Dark,
    val selectedLanguage: String = "English",
    val isAccountDisabled: Boolean = false,
    val historyEntries: List<HistoryEntry> = emptyList(),
    val selectedHistoryEntry: HistoryEntry? = null,
    val isLogoutDialogOpen: Boolean = false,
)
