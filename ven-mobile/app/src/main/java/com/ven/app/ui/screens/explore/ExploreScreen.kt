package com.ven.app.ui.screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.R
import com.ven.app.ui.components.feature.MediaGridItem
import com.ven.app.ui.components.feature.MediaGridTile
import com.ven.app.ui.components.layout.AppScaffold
import com.ven.app.ui.components.ui.VexBottomSheet
import com.ven.app.ui.components.ui.VexSelectableChip
import com.ven.app.ui.icons.VexIcon
import com.ven.app.ui.icons.VexIcons
import com.ven.app.ui.navigation.BottomNavTab
import com.ven.app.ui.screens.onboarding.DefaultInterestCategories
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSize
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Halaman Explore (Figma node 4183-125).
 *
 * Struktur sesuai screenshot:
 * 1. Pill pencarian read-only (diklik -> halaman Recents/pencarian) + tombol filter sliders.
 * 2. Grid poster 3 kolom edge-to-edge, celah 2dp, radius 0dp.
 *    Setiap tile dapat diklik -> halaman detail (Expostvex).
 * 3. Tombol filter membuka sheet Category (multi-pilih, tetap terbuka saat memilih).
 *    Kategori terpilih memfilter grid + ditandai badge pada ikon filter.
 * 4. Bottom Navigation Bar dengan tab Explore aktif.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onMediaClick: (ExplorePost) -> Unit = {},
) {
    var selectedCategories by rememberSaveable { mutableStateOf(emptyList<String>()) }
    var isCategoryOpen by rememberSaveable { mutableStateOf(false) }

    val visiblePosts = remember(selectedCategories) {
        if (selectedCategories.isEmpty()) {
            dummyExplorePosts
        } else {
            dummyExplorePosts.filter { post ->
                post.categories.any { it in selectedCategories }
            }
        }
    }

    ExploreContent(
        posts = visiblePosts,
        hasActiveFilter = selectedCategories.isNotEmpty(),
        onSearchClick = onSearchClick,
        onFilterClick = { isCategoryOpen = true },
        onMediaClick = onMediaClick,
        onTabSelected = onTabSelected,
        modifier = modifier,
    )

    if (isCategoryOpen) {
        VexBottomSheet(
            onDismissRequest = { isCategoryOpen = false },
        ) {
            ExploreCategorySheet(
                selected = selectedCategories,
                onToggle = { category ->
                    selectedCategories = if (category in selectedCategories) {
                        selectedCategories - category
                    } else {
                        selectedCategories + category
                    }
                },
                onReset = { selectedCategories = emptyList() },
            )
        }
    }
}

@Composable
private fun ExploreContent(
    posts: List<ExplorePost>,
    hasActiveFilter: Boolean,
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit,
    onMediaClick: (ExplorePost) -> Unit,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    AppScaffold(
        currentTab = BottomNavTab.Explore,
        onTabSelected = onTabSelected,
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // -------------------------------------------------
            // Bar pencarian + filter (tetap di atas)
            // -------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.bg)
                    .padding(horizontal = VexSpace.s4, vertical = VexSpace.s3),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(VexSpace.s3),
            ) {
                ExploreSearchButton(
                    onClick = onSearchClick,
                    modifier = Modifier.weight(1f),
                )

                Box(
                    modifier = Modifier
                        .size(VexSize.minTouch)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(color = colors.text.copy(alpha = 0.15f)),
                            role = Role.Button,
                            onClickLabel = stringResource(R.string.explore_filter_cd),
                            onClick = onFilterClick,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    VexIcon(
                        id = VexIcons.Filter,
                        contentDescription = stringResource(R.string.explore_filter_cd),
                        size = VexSize.iconLg,
                        tint = colors.text,
                    )
                    if (hasActiveFilter) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 11.dp, end = 11.dp)
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(colors.primary),
                        )
                    }
                }
            }

            // -------------------------------------------------
            // Grid poster 3 kolom
            // -------------------------------------------------
            if (posts.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f, fill = true),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    contentPadding = PaddingValues(bottom = VexSpace.s6),
                ) {
                    items(
                        items = posts,
                        key = { it.id },
                    ) { post ->
                        MediaGridTile(
                            item = MediaGridItem(
                                id = post.id,
                                title = post.title,
                                imageRes = post.imageRes,
                            ),
                            onClick = { onMediaClick(post) },
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.explore_empty),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textMuted,
                    )
                }
            }
        }
    }
}

/**
 * Sheet Category (Figma node 4207-602):
 * judul "Category" + teks "Reset" + daftar chip 14 kategori.
 * Sheet TIDAK tertutup saat chip dipilih agar pengguna sempat memilih.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ExploreCategorySheet(
    selected: List<String>,
    onToggle: (String) -> Unit,
    onReset: () -> Unit,
) {
    val colors = VexTheme.colors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = VexSpace.s6)
            .padding(bottom = VexSpace.s8)
            .navigationBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = VexSpace.s3),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.explore_category_title),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = colors.text,
            )
            Text(
                text = stringResource(R.string.explore_reset),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                ),
                color = colors.primaryText,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(color = colors.primary.copy(alpha = 0.2f)),
                    role = Role.Button,
                    onClick = onReset,
                ),
            )
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(VexSpace.s2),
            verticalArrangement = Arrangement.spacedBy(VexSpace.s2),
        ) {
            DefaultInterestCategories.forEach { category ->
                VexSelectableChip(
                    text = category,
                    selected = category in selected,
                    onSelectedChange = { onToggle(category) },
                )
            }
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "Explore - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ExploreLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        ExploreScreen(onTabSelected = {})
    }
}

@Preview(name = "Explore - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun ExploreDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        ExploreScreen(onTabSelected = {})
    }
}
