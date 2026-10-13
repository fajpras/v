package com.ven.app.ui.components.feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
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
import com.ven.app.ui.screens.schedule.ScheduleEventItem
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexRadius
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Komponen kartu item kegiatan pameran untuk tab Approved dan Waiting.
 * Sesuai DESIGN.md §6.3 & §8.4.2:
 * - Sisi kiri: Thumbnail banner 16:9 (lebar ~140dp, radius 8–12dp [VexRadius.md]).
 * - Sisi kanan: Tanggal (bold), Jam, dan teks aksi "See details" di kanan-bawah.
 */
@Composable
fun EventListItem(
    entry: ScheduleEventItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = colors.text.copy(alpha = 0.12f)),
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = VexSpace.s5, vertical = VexSpace.s3)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Thumbnail Banner 16:9
        Box(
            modifier = Modifier
                .width(140.dp)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(VexRadius.md)),
        ) {
            Image(
                painter = painterResource(id = entry.bannerRes),
                contentDescription = entry.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.width(VexSpace.s4))

        // Info Kolom Kanan
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = entry.dateText,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = colors.text,
                )
                Text(
                    text = entry.timeText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.text,
                )
            }

            Text(
                text = stringResource(R.string.schedule_see_details),
                style = MaterialTheme.typography.bodySmall,
                color = colors.textMuted,
                modifier = Modifier.align(Alignment.End),
            )
        }
    }
}

@Preview(name = "EventListItem - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun EventListItemDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        EventListItem(
            entry = ScheduleEventItem(
                id = "1",
                title = "Open Source Competition",
                dateText = "24th Sep 2026",
                timeText = "02.00 PM",
                fullDateFormatted = "Thursday, 24th September 2026 at 02.00 PM",
                bannerRes = R.drawable.poster_3,
                isApproved = true,
            ),
            onClick = {},
        )
    }
}
