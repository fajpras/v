package com.ven.app.ui.screens.schedule.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.ven.app.ui.components.ui.Avatar
import com.ven.app.ui.components.ui.AvatarSize
import com.ven.app.ui.components.ui.VexBottomSheet
import com.ven.app.ui.components.ui.VexButton
import com.ven.app.ui.components.ui.VexButtonVariant
import com.ven.app.ui.screens.schedule.ScheduleRequestItem
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexRadius
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Bottom Sheet modal untuk peninjauan permohonan masuk pada tab Request.
 * Sesuai DESIGN.md §8.4.6 & Wireframe schedule-5:
 * - Banner pameran 16:9 di bagian atas.
 * - Label "Requested for {tanggal jam}" dengan teks tebal.
 * - Baris pemohon: Avatar kecil + Username.
 * - Isi pesan lengkap catatan permohonan diskusi/kolaborasi.
 * - Dua tombol aksi seimbang (50:50):
 *   - "Approve" (Primary ungu neon)
 *   - "Reject" (Danger merah)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailSheet(
    request: ScheduleRequestItem,
    onDismissRequest: () -> Unit,
    onApprove: () -> Unit,
    onReject: () -> Unit,
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
                    painter = painterResource(id = request.bannerRes),
                    contentDescription = request.requesterName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(VexSpace.s5))

            // Label Jadwal Target (Requested for ...)
            val annotatedSchedule = buildAnnotatedString {
                append(stringResource(R.string.schedule_requested_for_prefix))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = colors.text)) {
                    append(request.fullDateFormatted)
                }
                append(".")
            }

            Text(
                text = annotatedSchedule,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                color = colors.text,
            )

            Spacer(modifier = Modifier.height(VexSpace.s4))

            // Baris Profil Pemohon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Avatar(
                    initials = request.requesterName.take(2).uppercase(),
                    imageRes = request.requesterAvatarRes,
                    size = AvatarSize.Sm,
                    contentDescription = request.requesterName,
                )

                Spacer(modifier = Modifier.width(VexSpace.s3))

                Text(
                    text = request.requesterName,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = colors.text,
                )
            }

            Spacer(modifier = Modifier.height(VexSpace.s3))

            // Isi Pesan Lengkap
            Text(
                text = request.note,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                color = colors.text,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Dua Tombol Aksi Sejajar: Approve & Reject
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                VexButton(
                    text = stringResource(R.string.schedule_approve),
                    onClick = onApprove,
                    variant = VexButtonVariant.Primary,
                    modifier = Modifier.weight(1f),
                )

                VexButton(
                    text = stringResource(R.string.schedule_reject),
                    onClick = onReject,
                    variant = VexButtonVariant.Danger,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview(name = "RequestDetailSheet - Dark Mode")
@Composable
private fun RequestDetailSheetPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        RequestDetailSheet(
            request = ScheduleRequestItem(
                id = "req_1",
                requesterName = "Gra._.aph",
                dateText = "27/9/26",
                timeText = "02.00 PM",
                fullDateFormatted = "Thursday, 24th September 2026 at 02.00 PM",
                note = "Saya dari perusahaan citra sinar bahagia ingin berdiskusi dengan anda mengenai salah satu proyek yang sedang anda kembangkan, jika anda bersedia saya siap menawarkan sebuah kerjasama berupa kontrak.",
                bannerRes = R.drawable.poster_3,
            ),
            onDismissRequest = {},
            onApprove = {},
            onReject = {},
        )
    }
}
