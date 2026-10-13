package com.ven.app.ui.components.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Label seksi/kategori grup menu Settings.
 * Sesuai DESIGN.md §6.1:
 * - Teks [bodyMedium] dengan warna [VexTheme.colors.textMuted].
 */
@Composable
fun VexSectionLabel(
    title: String,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = VexSpace.s6,
                vertical = VexSpace.s2,
            )
            .padding(top = VexSpace.s3),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
            ),
            color = colors.textMuted,
        )
    }
}

@Preview(name = "SectionLabel - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun VexSectionLabelDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        VexSectionLabel(title = "Account")
    }
}

@Preview(name = "SectionLabel - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun VexSectionLabelLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        VexSectionLabel(title = "Account")
    }
}
