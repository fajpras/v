package com.ven.app.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ven.app.R
import com.ven.app.ui.components.layout.VexTopBar
import com.ven.app.ui.components.ui.VexRadioRow
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexTheme

/**
 * Halaman Pengaturan Tema (Light / Dark).
 * Sesuai wireframe `Wireframe - Setting Password-1.png` dan DESIGN.md §4.7.
 */
@Composable
fun ThemeScreen(
    currentTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bg,
        topBar = {
            VexTopBar.Back(
                title = stringResource(R.string.settings_item_theme),
                onBackClick = onBackClick,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colors.bg),
        ) {
            VexRadioRow(
                label = stringResource(R.string.settings_theme_light),
                selected = currentTheme == ThemeMode.Light,
                onClick = { onThemeSelected(ThemeMode.Light) },
            )

            VexRadioRow(
                label = stringResource(R.string.settings_theme_dark),
                selected = currentTheme == ThemeMode.Dark,
                onClick = { onThemeSelected(ThemeMode.Dark) },
            )
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "ThemeScreen - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun ThemeScreenDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        ThemeScreen(
            currentTheme = ThemeMode.Dark,
            onThemeSelected = {},
            onBackClick = {},
        )
    }
}

@Preview(name = "ThemeScreen - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ThemeScreenLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        ThemeScreen(
            currentTheme = ThemeMode.Light,
            onThemeSelected = {},
            onBackClick = {},
        )
    }
}
