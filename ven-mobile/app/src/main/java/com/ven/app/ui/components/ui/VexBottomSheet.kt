package com.ven.app.ui.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ven.app.ui.theme.VexRadius
import com.ven.app.ui.theme.VexTheme

/**
 * Bottom Sheet modal untuk VEN (Virtual Exhibition).
 * Sesuai DESIGN.md §6.1:
 * - Radius atas 28dp ([VexRadius.xl]).
 * - Handle bar abu-abu 40×4dp bentuk pill.
 * - Warna kontainer [VexTheme.colors.surface].
 * - Warna scrim [VexTheme.colors.scrim].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VexBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = VexTheme.colors

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = RoundedCornerShape(
            topStart = VexRadius.xl,
            topEnd = VexRadius.xl,
            bottomStart = 0.dp,
            bottomEnd = 0.dp,
        ),
        containerColor = colors.surface,
        scrimColor = colors.scrim,
        dragHandle = {
            Box(
                modifier = Modifier
                    .height(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 4.dp)
                        .clip(RoundedCornerShape(VexRadius.full))
                        .background(colors.border),
                )
            }
        },
        content = content,
    )
}
