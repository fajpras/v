package com.ven.app.ui.screens.schedule.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ven.app.R
import com.ven.app.ui.components.ui.VexBottomSheet
import com.ven.app.ui.components.ui.VexButton
import com.ven.app.ui.components.ui.VexButtonVariant
import com.ven.app.ui.screens.schedule.ScheduleEventItem
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexRadius
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Bottom Sheet modal untuk detail jadwal pertemuan pameran (Tab Approved & Waiting).
 * Sesuai DESIGN.md §8.4.5 & Wireframe schedule-1 & schedule-3:
 * - Banner pameran besar rasio 16:9 di bagian atas.
 * - Teks penjelasan dengan tanggal & jam dicetak tebal via [AnnotatedString].
 * - Tombol "Join Room":
 *   - Aktif (Primary ungu) jika [ScheduleEventItem.isApproved] = true.
 *   - Nonaktif (Disabled) jika [ScheduleEventItem.isApproved] = false.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailSheet(
    event: ScheduleEventItem,
    onDismissRequest: () -> Unit,
    onJoinRoom: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    VexBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = VexSpace.s6)
                .padding(bottom = VexSpace.s6)
                .navigationBarsPadding(),
        ) {
            // Banner Pameran 16:9
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(VexRadius.md)),
            ) {
                Image(
                    painter = painterResource(id = event.bannerRes),
                    contentDescription = event.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(VexSpace.s5))

            // Teks Pesan Status (AnnotatedString dengan tanggal/jam bold)
            val prefix = if (event.isApproved) {
                stringResource(R.string.schedule_approved_detail_prefix)
            } else {
                stringResource(R.string.schedule_waiting_detail_prefix)
            }

            val suffix = if (event.isApproved) {
                stringResource(R.string.schedule_approved_detail_suffix)
            } else {
                stringResource(R.string.schedule_waiting_detail_suffix)
            }

            val annotatedMessage = buildAnnotatedString {
                append(prefix)
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colors.text)) {
                    append(event.fullDateFormatted)
                }
                append(suffix)
            }

            Text(
                text = annotatedMessage,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                color = colors.text,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol Utama "Join Room"
            VexButton(
                text = stringResource(R.string.schedule_join_room),
                onClick = onJoinRoom,
                enabled = event.isApproved,
                variant = VexButtonVariant.Primary,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "EventDetailSheet Approved - Dark Mode")
@Composable
private fun EventDetailSheetApprovedPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        EventDetailSheet(
            event = ScheduleEventItem(
                id = "1",
                title = "Open Source Competition",
                dateText = "24th Sep 2026",
                timeText = "02.00 PM",
                fullDateFormatted = "Thursday, 24th September 2026 at 02.00 PM",
                bannerRes = R.drawable.poster_3,
                isApproved = true,
            ),
            onDismissRequest = {},
            onJoinRoom = {},
        )
    }
}

@Preview(name = "EventDetailSheet Waiting - Dark Mode")
@Composable
private fun EventDetailSheetWaitingPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        EventDetailSheet(
            event = ScheduleEventItem(
                id = "2",
                title = "Virtual Exhibition",
                dateText = "24th Sep 2026",
                timeText = "02.00 PM",
                fullDateFormatted = "Thursday, 24th September 2026 at 02.00 PM",
                bannerRes = R.drawable.poster_3,
                isApproved = false,
            ),
            onDismissRequest = {},
            onJoinRoom = {},
        )
    }
}
