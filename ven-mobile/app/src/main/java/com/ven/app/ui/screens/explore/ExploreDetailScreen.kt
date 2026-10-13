package com.ven.app.ui.screens.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ven.app.R
import com.ven.app.ui.components.feature.PostCaption
import com.ven.app.ui.components.layout.VexTopBar
import com.ven.app.ui.icons.VexIcon
import com.ven.app.ui.icons.VexIcons
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSize
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Halaman detail karya setelah gambar Explore diklik (Figma Expostvex 4205-823).
 *
 * - TopBar kembali + judul "Posts"/"Exhibits".
 * - Header @username + ikon kalender (Request Meeting) & play (Virtual 3D).
 * - Media selebar layar + caption (dukung see more/less + link) + waktu posting.
 */
@Composable
fun ExploreDetailScreen(
    postId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onRequestMeetingClick: (postId: String) -> Unit = {},
    onView3dClick: () -> Unit = {},
) {
    val colors = VexTheme.colors
    val detail = remember(postId) { findExploreDetail(postId) }
    val title = if (detail?.isExhibit == true) {
        stringResource(R.string.explore_detail_exhibits)
    } else {
        stringResource(R.string.explore_detail_posts)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bg,
        topBar = {
            VexTopBar.Back(
                title = title,
                onBackClick = onBackClick,
            )
        },
    ) { innerPadding ->
        if (detail == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.explore_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textMuted,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = VexSpace.s6),
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = VexSpace.s6,
                                end = VexSpace.s6,
                                top = VexSpace.s4,
                                bottom = VexSpace.s3,
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = if (detail.username.startsWith("@")) {
                                detail.username
                            } else {
                                "@${detail.username}"
                            },
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                            ),
                            color = colors.text,
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(VexSpace.s4),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(
                                onClick = { onRequestMeetingClick(detail.id) },
                            ) {
                                VexIcon(
                                    id = VexIcons.ScheduleAction,
                                    contentDescription = stringResource(R.string.home_request_meeting),
                                    size = VexSize.icon,
                                    tint = colors.text,
                                )
                            }
                            IconButton(onClick = onView3dClick) {
                                VexIcon(
                                    id = VexIcons.Play,
                                    contentDescription = stringResource(R.string.home_view_3d),
                                    size = VexSize.icon,
                                    tint = colors.text,
                                )
                            }
                        }
                    }
                }

                item {
                    Image(
                        painter = painterResource(id = detail.imageRes),
                        contentDescription = detail.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(detail.aspectRatio)
                            .background(colors.surfaceInput),
                        contentScale = ContentScale.Crop,
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = VexSpace.s6,
                                end = VexSpace.s6,
                                top = VexSpace.s3,
                            ),
                    ) {
                        PostCaption(
                            username = detail.username,
                            description = detail.description,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Text(
                            text = detail.timeAgo,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textMuted,
                            modifier = Modifier.padding(top = VexSpace.s1),
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "Explore Detail - Light", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ExploreDetailLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        ExploreDetailScreen(postId = "ex1", onBackClick = {})
    }
}

@Preview(name = "Explore Detail - Dark", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun ExploreDetailDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        ExploreDetailScreen(postId = "ex1", onBackClick = {})
    }
}
