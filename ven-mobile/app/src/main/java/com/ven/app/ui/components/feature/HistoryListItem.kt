package com.ven.app.ui.components.feature

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.R
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexRadius
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Model item entri riwayat pameran / pertemuan.
 */
@Immutable
data class HistoryEntry(
    val id: String,
    val title: String,
    val dateText: String,
    val timeText: String,
    val fullDateFormatted: String,
    @param:DrawableRes val bannerRes: Int,
)

/**
 * Komponen kartu item riwayat pameran pada halaman History.
 * Sesuai wireframe History:
 * - Thumbnail banner 16:9 di sisi kiri (radius 4dp).
 * - Kanan: Tanggal (bold) + Jam + Teks "See details" (kanan-bawah).
 */
@Composable
fun HistoryListItem(
    entry: HistoryEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = colors.text.copy(alpha = 0.15f)),
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = VexSpace.s6, vertical = VexSpace.s3),
        verticalAlignment = Alignment.Top,
    ) {
        // Thumbnail Banner 16:9
        Box(
            modifier = Modifier
                .width(150.dp)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(VexRadius.xs))
                .background(colors.surfaceInput),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = entry.bannerRes),
                contentDescription = entry.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.width(VexSpace.s4))

        // Info Tanggal, Waktu & Aksi
        Column(
            modifier = Modifier
                .weight(1f)
                .height(84.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = entry.dateText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = colors.text,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = entry.timeText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.text,
                )
            }

            Text(
                text = stringResource(R.string.settings_history_see_details),
                style = MaterialTheme.typography.bodySmall,
                color = colors.textMuted,
                modifier = Modifier.align(Alignment.End),
            )
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "HistoryListItem - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun HistoryListItemDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        HistoryListItem(
            entry = HistoryEntry(
                id = "1",
                title = "Open Source Competition",
                dateText = "24th Sep 2026",
                timeText = "02.00 PM",
                fullDateFormatted = "Thursday, 24th September 2026",
                bannerRes = R.drawable.poster_1,
            ),
            onClick = {},
        )
    }
}

@Preview(name = "HistoryListItem - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun HistoryListItemLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        HistoryListItem(
            entry = HistoryEntry(
                id = "1",
                title = "Open Source Competition",
                dateText = "24th Sep 2026",
                timeText = "02.00 PM",
                fullDateFormatted = "Thursday, 24th September 2026",
                bannerRes = R.drawable.poster_1,
            ),
            onClick = {},
        )
    }
}
