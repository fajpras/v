package com.ven.app.ui.screens.ai.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ven.app.ui.theme.ElmsSans
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSize
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Baris item riwayat percakapan pada Drawer AI Assistant (DESIGN.md §8.12.5).
 * Sesuai wireframe `Wireframe - AI Chat-2.png`:
 * - Teks 1 baris dengan pemotongan otomatis ellipsis (TextOverflow.Ellipsis).
 * - Tinggi sentuh minimal 48dp (VexSize.minTouch).
 * - Warna teks putih solid (#FFFFFF / colors.text), atau warna aksen jika terpilih.
 */
@Composable
fun ChatHistoryItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    val colors = VexTheme.colors

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = VexSize.minTouch)
            .clickable(onClick = onClick)
            .padding(horizontal = VexSpace.s6, vertical = VexSpace.s2),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = ElmsSans,
                fontSize = 16.sp,
                color = if (isSelected) colors.primaryText else colors.text,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(name = "ChatHistoryItem - Normal")
@Composable
private fun ChatHistoryItemNormalPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        ChatHistoryItem(
            title = "Rekomendasi karya terkeren",
            onClick = {},
            isSelected = false,
        )
    }
}

@Preview(name = "ChatHistoryItem - Selected")
@Composable
private fun ChatHistoryItemSelectedPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        ChatHistoryItem(
            title = "Pameran yang sesuai dengan kategori desain grafis",
            onClick = {},
            isSelected = true,
        )
    }
}
