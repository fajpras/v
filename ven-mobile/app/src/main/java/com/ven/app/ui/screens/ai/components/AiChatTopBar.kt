package com.ven.app.ui.screens.ai.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.R
import com.ven.app.ui.icons.VexIcon
import com.ven.app.ui.icons.VexIcons
import com.ven.app.ui.icons.VexLogo
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSize
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Header TopBar khusus untuk halaman AI Assistant (DESIGN.md §8.12.1).
 * - Sisi Kiri: Tombol pemicu Drawer Riwayat (ic_action_menu / VexIcons.Menu).
 * - Sisi Kanan: Logo resmi VEX (VexIcons.LogoVex).
 * - Latar belakang menyatu dengan kanvas (#1F1F1F) tanpa divider bawah [OBSERVED].
 */
@Composable
fun AiChatTopBar(
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.bg)
            .statusBarsPadding()
            .height(VexSize.topBar),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = VexSpace.s6),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Sisi Kiri: Ikon Menu Drawer (dua garis tak sama panjang)
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.size(VexSize.minTouch),
            ) {
                VexIcon(
                    id = VexIcons.Menu,
                    contentDescription = stringResource(R.string.ai_chat_menu_desc),
                    tint = colors.text,
                    size = VexSize.icon,
                )
            }

            // Sisi Kanan: Logo resmi VEX
            VexLogo(
                modifier = Modifier.height(24.dp),
            )
        }
    }
}

@Preview(name = "AiChatTopBar - Dark Mode")
@Composable
private fun AiChatTopBarDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        AiChatTopBar(onMenuClick = {})
    }
}

@Preview(name = "AiChatTopBar - Light Mode")
@Composable
private fun AiChatTopBarLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        AiChatTopBar(onMenuClick = {})
    }
}
