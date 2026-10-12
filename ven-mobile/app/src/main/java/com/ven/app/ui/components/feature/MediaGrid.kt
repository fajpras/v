package com.ven.app.ui.components.feature

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.R
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexAspect
import com.ven.app.ui.theme.VexTheme

/**
 * Model data representasi satu kartu media dalam [MediaGrid].
 */
@Immutable
data class MediaGridItem(
    val id: String,
    val title: String,
    @param:DrawableRes val imageRes: Int? = null,
    val imageUrl: String? = null,
    val span: Int = 1,
)

/**
 * Tile individual karya seni/poster untuk grid media.
 */
@Composable
fun MediaGridTile(
    item: MediaGridItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    aspectRatio: Float = VexAspect.poster,
) {
    val colors = VexTheme.colors

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .background(colors.surfaceInput)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = colors.text.copy(alpha = 0.2f)),
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (item.imageRes != null) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        } else {
            Text(
                text = item.title.ifBlank { "Media" },
                style = MaterialTheme.typography.bodySmall,
                color = colors.textMuted,
            )
        }
    }
}

/**
 * Grid Media untuk Explore dan Profile.
 * Sesuai spesifikasi DESIGN.md §5.3 & §6.3:
 * - Standar 3 kolom, celah antar item 2dp.
 * - Sudut tajam tanpa radius (0dp).
 */
@Composable
fun MediaGrid(
    items: List<MediaGridItem>,
    modifier: Modifier = Modifier,
    columns: Int = 3,
    onItemClick: (MediaGridItem) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp),
    header: (@Composable () -> Unit)? = null,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = contentPadding,
    ) {
        if (header != null) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                header()
            }
        }

        items(
            items = items,
            key = { it.id },
            span = { item -> GridItemSpan(item.span.coerceAtMost(maxLineSpan)) },
        ) { item ->
            MediaGridTile(
                item = item,
                onClick = { onItemClick(item) },
            )
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "MediaGrid - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun MediaGridDarkPreview() {
    val sampleItems = listOf(
        MediaGridItem("1", "Poster 1", R.drawable.poster_1),
        MediaGridItem("2", "Poster 2", R.drawable.poster_2),
        MediaGridItem("3", "Poster 3", R.drawable.poster_3),
    )
    VexTheme(mode = ThemeMode.Dark) {
        MediaGrid(items = sampleItems)
    }
}

@Preview(name = "MediaGrid - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun MediaGridLightPreview() {
    val sampleItems = listOf(
        MediaGridItem("1", "Poster 1", R.drawable.poster_1),
        MediaGridItem("2", "Poster 2", R.drawable.poster_2),
    )
    VexTheme(mode = ThemeMode.Light) {
        MediaGrid(items = sampleItems)
    }
}
