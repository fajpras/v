package com.ven.app.ui.screens.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.R
import com.ven.app.ui.components.feature.MediaGridItem
import com.ven.app.ui.components.feature.MediaGridTile
import com.ven.app.ui.components.ui.Avatar
import com.ven.app.ui.components.ui.AvatarSize
import com.ven.app.ui.icons.VexIcon
import com.ven.app.ui.icons.VexIcons
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSize
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Tab hasil pencarian (Figma Explorevex 4183-494, Accountsvex, Postvex, Exhibitsvex).
 * Keempat tab adalah satu halaman; keyword dipertahankan saat berpindah tab.
 */
enum class SearchTab {
    ForYou,
    Accounts,
    Posts,
    Exhibits,
}

/**
 * Halaman pencarian dari Explore.
 *
 * - Query kosong -> Recents (Figma 4183-426): histori + hapus per item + pilih untuk mencari lagi.
 * - Query terisi -> hasil Explorevex dengan 4 tab (mengetik langsung memfilter lokal).
 * - Submit keyboard (IME Search) menyimpan query ke histori; mengetik tidak menghapus histori.
 */
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    initialQuery: String = "",
    onPostClick: (postId: String) -> Unit = {},
) {
    val colors = VexTheme.colors
    var query by rememberSaveable { mutableStateOf(initialQuery) }
    var tab by rememberSaveable { mutableStateOf(SearchTab.ForYou) }
    val recents = remember {
        mutableStateListOf<SearchRecent>().apply { addAll(dummySearchRecents) }
    }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    fun submitSearch() {
        val keyword = query.trim()
        if (keyword.isEmpty()) return
        recents.removeAll { it.title.equals(keyword, ignoreCase = true) }
        recents.add(
            0,
            SearchRecent(
                id = "r-${System.currentTimeMillis()}",
                title = keyword,
                isKeyword = true,
            ),
        )
        if (recents.size > 10) {
            recents.removeRange(10, recents.size)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bg,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            // -------------------------------------------------
            // Bar atas: kembali + field pencarian
            // -------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = VexSpace.s4, vertical = VexSpace.s3),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(VexSpace.s2),
            ) {
                IconButton(onClick = onBackClick) {
                    VexIcon(
                        id = VexIcons.Back,
                        contentDescription = stringResource(R.string.explore_back_cd),
                        size = VexSize.icon,
                        tint = colors.text,
                    )
                }
                ExploreSearchInput(
                    value = query,
                    onValueChange = { query = it },
                    onSearchSubmit = ::submitSearch,
                    focusRequester = focusRequester,
                    modifier = Modifier.weight(1f),
                )
            }

            if (query.isBlank()) {
                RecentsBlock(
                    recents = recents,
                    onSelect = { query = it.title },
                    onRemove = { recents.remove(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true),
                )
            } else {
                SearchTabsRow(
                    selected = tab,
                    onTabSelected = { tab = it },
                )
                SearchResultContent(
                    query = query,
                    tab = tab,
                    onPostClick = onPostClick,
                    onAccountClick = { query = it.username },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true),
                )
            }
        }
    }
}

@Composable
private fun RecentsBlock(
    recents: List<SearchRecent>,
    onSelect: (SearchRecent) -> Unit,
    onRemove: (SearchRecent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.search_recents_title),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            color = colors.text,
            modifier = Modifier.padding(
                horizontal = VexSpace.s6,
                vertical = VexSpace.s2,
            ),
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = VexSpace.s6),
        ) {
            items(
                items = recents,
                key = { it.id },
            ) { item ->
                RecentRow(
                    item = item,
                    onSelect = { onSelect(item) },
                    onRemove = { onRemove(item) },
                )
            }
        }
    }
}

@Composable
private fun RecentRow(
    item: SearchRecent,
    onSelect: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = colors.text.copy(alpha = 0.1f)),
                role = Role.Button,
                onClick = onSelect,
            )
            .padding(horizontal = VexSpace.s6, vertical = VexSpace.s2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(VexSpace.s3),
    ) {
        if (item.isKeyword) {
            Box(
                modifier = Modifier
                    .size(AvatarSize.Md.dp)
                    .clip(CircleShape)
                    .background(colors.surfaceInput),
                contentAlignment = Alignment.Center,
            ) {
                VexIcon(
                    id = VexIcons.History,
                    contentDescription = null,
                    size = VexSize.icon,
                    tint = colors.textSecondary,
                )
            }
        } else {
            Avatar(
                initials = item.title,
                size = AvatarSize.Md,
                contentDescription = item.title,
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = colors.text,
            )
            if (!item.subtitle.isNullOrBlank()) {
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textMuted,
                )
            }
        }

        IconButton(onClick = onRemove) {
            VexIcon(
                id = VexIcons.Close,
                contentDescription = stringResource(R.string.search_remove_cd),
                size = VexSize.iconSm,
                tint = colors.textMuted,
            )
        }
    }
}

@Composable
private fun SearchTabsRow(
    selected: SearchTab,
    onTabSelected: (SearchTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors
    val tabs = listOf(
        SearchTab.ForYou to stringResource(R.string.search_tab_for_you),
        SearchTab.Accounts to stringResource(R.string.search_tab_accounts),
        SearchTab.Posts to stringResource(R.string.search_tab_posts),
        SearchTab.Exhibits to stringResource(R.string.search_tab_exhibits),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = VexSpace.s4),
    ) {
        tabs.forEach { (tab, label) ->
            val isSelected = tab == selected
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = colors.text.copy(alpha = 0.1f)),
                        role = Role.Tab,
                        onClick = { onTabSelected(tab) },
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    ),
                    color = if (isSelected) colors.text else colors.textMuted,
                    maxLines = 1,
                    modifier = Modifier.padding(vertical = VexSpace.s3),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(if (isSelected) colors.text else Color.Transparent),
                )
            }
        }
    }
}

@Composable
private fun SearchResultContent(
    query: String,
    tab: SearchTab,
    onPostClick: (postId: String) -> Unit,
    onAccountClick: (ExploreAccount) -> Unit,
    modifier: Modifier = Modifier,
) {
    val accounts = remember(query) { searchExploreAccounts(query) }
    val posts = remember(query) { searchExplorePosts(query) }
    val exhibits = remember(query) { searchExploreExhibits(query) }

    when (tab) {
        SearchTab.ForYou -> {
            ForYouResults(
                accounts = accounts,
                posts = posts,
                onAccountClick = onAccountClick,
                onPostClick = onPostClick,
                modifier = modifier,
            )
        }
        SearchTab.Accounts -> {
            AccountsResults(
                accounts = accounts,
                onAccountClick = onAccountClick,
                modifier = modifier,
            )
        }
        SearchTab.Posts -> {
            PostsResults(
                posts = posts,
                onPostClick = onPostClick,
                modifier = modifier,
            )
        }
        SearchTab.Exhibits -> {
            ExhibitsResults(
                exhibits = exhibits,
                onExhibitClick = onPostClick,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.SemiBold,
        ),
        color = VexTheme.colors.text,
        modifier = modifier.padding(
            horizontal = VexSpace.s4,
            vertical = VexSpace.s3,
        ),
    )
}

@Composable
private fun AccountRow(
    account: ExploreAccount,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = colors.text.copy(alpha = 0.1f)),
                role = Role.Button,
                onClick = onClick,
            )
            .padding(horizontal = VexSpace.s6, vertical = VexSpace.s2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(VexSpace.s3),
    ) {
        Avatar(
            initials = account.username,
            size = AvatarSize.Md,
            contentDescription = account.username,
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = account.username,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = colors.text,
            )
            Text(
                text = account.displayName,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textMuted,
            )
        }
    }
}

@Composable
private fun EmptyResults(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.explore_empty),
            style = MaterialTheme.typography.bodyMedium,
            color = VexTheme.colors.textMuted,
        )
    }
}

/** Tab For you: seksi Accounts ringkas + seksi Posts grid 2 kolom. */
@Composable
private fun ForYouResults(
    accounts: List<ExploreAccount>,
    posts: List<ExplorePost>,
    onAccountClick: (ExploreAccount) -> Unit,
    onPostClick: (postId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (accounts.isEmpty() && posts.isEmpty()) {
        EmptyResults(modifier = modifier)
        return
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = PaddingValues(bottom = VexSpace.s6),
    ) {
        if (accounts.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                SectionHeader(text = stringResource(R.string.search_tab_accounts))
            }
            items(
                items = accounts,
                key = { it.username },
                span = { GridItemSpan(maxLineSpan) },
            ) { account ->
                AccountRow(
                    account = account,
                    onClick = { onAccountClick(account) },
                )
            }
        }
        if (posts.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                SectionHeader(text = stringResource(R.string.search_tab_posts))
            }
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
                    onClick = { onPostClick(post.id) },
                )
            }
        }
    }
}

/** Tab Accounts: daftar akun penuh. */
@Composable
private fun AccountsResults(
    accounts: List<ExploreAccount>,
    onAccountClick: (ExploreAccount) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (accounts.isEmpty()) {
        EmptyResults(modifier = modifier)
        return
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = VexSpace.s6),
    ) {
        items(
            items = accounts,
            key = { it.username },
        ) { account ->
            AccountRow(
                account = account,
                onClick = { onAccountClick(account) },
            )
        }
    }
}

/** Tab Posts: grid 2 kolom edge-to-edge. */
@Composable
private fun PostsResults(
    posts: List<ExplorePost>,
    onPostClick: (postId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (posts.isEmpty()) {
        EmptyResults(modifier = modifier)
        return
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
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
                onClick = { onPostClick(post.id) },
            )
        }
    }
}

/** Tab Exhibits: banner selebar layar bertumpuk. */
@Composable
private fun ExhibitsResults(
    exhibits: List<ExploreExhibit>,
    onExhibitClick: (exhibitId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    if (exhibits.isEmpty()) {
        EmptyResults(modifier = modifier)
        return
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = PaddingValues(bottom = VexSpace.s6),
    ) {
        items(
            items = exhibits,
            key = { it.id },
        ) { exhibit ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(colors.surfaceInput)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = colors.text.copy(alpha = 0.2f)),
                        role = Role.Button,
                        onClick = { onExhibitClick(exhibit.id) },
                    ),
            ) {
                Image(
                    painter = painterResource(id = exhibit.imageRes),
                    contentDescription = exhibit.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "Search Recents - Light", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SearchRecentsLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        SearchScreen(onBackClick = {})
    }
}

@Preview(name = "Search Recents - Dark", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun SearchRecentsDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        SearchScreen(onBackClick = {})
    }
}

@Preview(name = "Search Results - Light", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SearchResultsLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        SearchScreen(onBackClick = {}, initialQuery = "vex")
    }
}

@Preview(name = "Search Results - Dark", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun SearchResultsDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        SearchScreen(onBackClick = {}, initialQuery = "vex")
    }
}
