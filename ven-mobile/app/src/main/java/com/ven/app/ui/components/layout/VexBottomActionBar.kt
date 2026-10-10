package com.ven.app.ui.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.ui.components.ui.VexButton
import com.ven.app.ui.components.ui.VexButtonVariant
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Bar aksi bawah tetap (Sticky Bottom Action Bar) untuk layar SubPage / CreateFlow tanpa BottomNav.
 *
 * Sesuai spesifikasi DESIGN.md §5.2, §6.2, dan §8.6.1:
 * - Ditempatkan pada slot bottomBar Scaffold: `Scaffold(bottomBar = { VexBottomActionBar(...) })`.
 * - Fixed di bawah layar (tidak ikut ter-scroll di dalam konten).
 * - Background menggunakan `colors.navBar` (#252525 di Dark, #FFFFFF di Light).
 * - Garis pembatas atas 1dp menggunakan `colors.border`.
 * - Margin horizontal 20dp, padding atas 12dp, padding bawah 24dp + insets navigasi & keyboard (`imePadding`).
 */
@Composable
fun VexBottomActionBar(
    modifier: Modifier = Modifier,
    showTopDivider: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colors = VexTheme.colors

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.navBar)
            .navigationBarsPadding()
            .imePadding(),
    ) {
        if (showTopDivider) {
            HorizontalDivider(
                thickness = 1.dp,
                color = colors.border,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 0.dp,
                )
                .padding(
                    top = VexSpace.s3,
                    bottom = VexSpace.s6,
                ),
        ) {
            content()
        }
    }
}

// ---------------------------------------------------------
// Previews untuk 2 Mode
// ---------------------------------------------------------

@Preview(name = "BottomActionBar - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun VexBottomActionBarDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        VexBottomActionBar {
            VexButton(
                text = "Send Request",
                onClick = {},
                variant = VexButtonVariant.Primary,
            )
        }
    }
}

@Preview(name = "BottomActionBar - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun VexBottomActionBarLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        VexBottomActionBar {
            VexButton(
                text = "Send Request",
                onClick = {},
                variant = VexButtonVariant.Primary,
            )
        }
    }
}
