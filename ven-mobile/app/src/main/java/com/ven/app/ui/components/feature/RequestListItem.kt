package com.ven.app.ui.components.feature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.R
import com.ven.app.ui.components.ui.Avatar
import com.ven.app.ui.components.ui.AvatarSize
import com.ven.app.ui.screens.schedule.ScheduleRequestItem
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Komponen baris daftar permohonan pertemuan masuk untuk tab Request.
 * Sesuai DESIGN.md §6.3 & §8.4.4:
 * - Kiri: Avatar lingkaran 40–48dp ([AvatarSize.Md]).
 * - Kanan atas: Username pemohon (bold) + tanggal & jam (kanan atas, teks redup).
 * - Kanan bawah: Cuplikan pesan catatan ([TextOverflow.Ellipsis], maxLines 2).
 */
@Composable
fun RequestListItem(
    entry: ScheduleRequestItem,
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
            .padding(horizontal = VexSpace.s5, vertical = VexSpace.s3),
        verticalAlignment = Alignment.Top,
    ) {
        // Avatar Pemohon
        Avatar(
            initials = entry.requesterName.take(2).uppercase(),
            imageRes = entry.requesterAvatarRes,
            size = AvatarSize.Md,
            contentDescription = entry.requesterName,
        )

        Spacer(modifier = Modifier.width(VexSpace.s4))

        // Informasi Pesan & Waktu
        Column(
            modifier = Modifier.weight(1f),
        ) {
            // Baris Atas: Username & Tanggal Jam
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = entry.requesterName,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = colors.text,
                    modifier = Modifier.weight(1f, fill = false),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.width(VexSpace.s2))

                Text(
                    text = "${entry.dateText}  ${entry.timeText}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textMuted,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Cuplikan Pesan Catatan
            Text(
                text = entry.note,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textMuted,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(name = "RequestListItem - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun RequestListItemDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        RequestListItem(
            entry = ScheduleRequestItem(
                id = "req_1",
                requesterName = "Gra._.aph",
                dateText = "27/9/26",
                timeText = "02.00 PM",
                fullDateFormatted = "Thursday, 24th September 2026 at 02.00 PM",
                note = "Saya dari perusahaan citra sinar bahagia ingin berdiskusi dengan anda...",
                bannerRes = R.drawable.poster_3,
            ),
            onClick = {},
        )
    }
}
