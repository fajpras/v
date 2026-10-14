package com.ven.app.ui.screens.ai.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.ui.theme.ChatBodyStyle
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexRadius
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Balon pesan dari pengguna (User Message Bubble) pada halaman AI Assistant (DESIGN.md §8.12.3).
 * Sesuai wireframe `Wireframe - AI Chat-1.png`:
 * - Perataan: Rata kanan (Alignment.End).
 * - Bentuk: Speech tail bubble, radius 16dp kecuali sudut kanan-atas ~4dp.
 * - Latar Belakang: surfaceInput (#363636 / putih 10% di dark mode).
 * - Lebar Maksimal: ~87% dari lebar layar.
 * - Tipografi: ChatBodyStyle (18sp, Regular 400, line-height 1.4).
 */
@Composable
fun ChatBubbleUser(
    text: String,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors
    // Sudut kanan-atas 4dp untuk memberikan efek ekor balon bicara arah keluar
    val bubbleShape = RoundedCornerShape(
        topStart = VexRadius.lg,
        bottomStart = VexRadius.lg,
        bottomEnd = VexRadius.lg,
        topEnd = VexRadius.xs,
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 48.dp, end = VexSpace.s5),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 340.dp)
                .clip(bubbleShape)
                .background(colors.surfaceInput)
                .padding(horizontal = 20.dp, vertical = 14.dp),
        ) {
            Text(
                text = text,
                style = ChatBodyStyle,
                color = colors.text,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Preview(name = "ChatBubbleUser - Dark")
@Composable
private fun ChatBubbleUserDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        ChatBubbleUser(
            text = "carikan saya 10 karya terkeren yang sesuai dengan kategori yang saya pilih",
        )
    }
}

@Preview(name = "ChatBubbleUser - Light")
@Composable
private fun ChatBubbleUserLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        ChatBubbleUser(
            text = "carikan saya 10 karya terkeren yang sesuai dengan kategori yang saya pilih",
        )
    }
}
