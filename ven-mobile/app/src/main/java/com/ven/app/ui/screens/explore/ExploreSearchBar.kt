package com.ven.app.ui.screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.ven.app.R
import com.ven.app.ui.icons.VexIcon
import com.ven.app.ui.icons.VexIcons
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSize
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Kerangka pill pencarian Explore.
 * Latar [fieldPill] terang di kedua mode, ikon kaca pembesar selalu gelap.
 */
@Composable
fun SearchPillContainer(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = VexTheme.colors

    Row(
        modifier = modifier
            .height(VexSize.search)
            .clip(CircleShape)
            .background(colors.fieldPill)
            .padding(horizontal = VexSpace.s4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        VexIcon(
            id = VexIcons.Search,
            contentDescription = null,
            size = VexSize.iconSm,
            tint = Color.Black,
        )
        Spacer(modifier = Modifier.width(VexSpace.s3))
        content()
    }
}

/**
 * Pill pencarian read-only untuk halaman Explore.
 * Diklik untuk berpindah ke halaman pencarian (Recents).
 */
@Composable
fun ExploreSearchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String = stringResource(R.string.explore_search_hint),
) {
    val colors = VexTheme.colors

    SearchPillContainer(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(color = Color.Black.copy(alpha = 0.15f)),
            role = Role.Button,
            onClickLabel = hint,
            onClick = onClick,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.fieldPillPlaceholder,
            )
        }
    }
}

/**
 * Pill pencarian yang dapat diketik untuk halaman pencarian (Recents / hasil).
 */
@Composable
fun ExploreSearchInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = stringResource(R.string.explore_search_hint),
    focusRequester: FocusRequester? = null,
    onSearchSubmit: () -> Unit = {},
) {
    val colors = VexTheme.colors

    SearchPillContainer(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchSubmit() }),
            cursorBrush = SolidColor(Color.Black),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = MaterialTheme.typography.bodyLarge,
                                color = colors.fieldPillPlaceholder,
                            )
                        }
                        innerTextField()
                    }
                }
            },
        )
    }
}

@Preview(name = "ExploreSearchBar - Light", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ExploreSearchBarLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        ExploreSearchButton(onClick = {})
    }
}

@Preview(name = "ExploreSearchBar - Dark", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun ExploreSearchBarDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        ExploreSearchInput(value = "vex", onValueChange = {})
    }
}
