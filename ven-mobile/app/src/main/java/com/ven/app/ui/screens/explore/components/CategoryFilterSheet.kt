package com.ven.app.ui.screens.explore.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ven.app.ui.components.ui.VexBottomSheet
import com.ven.app.ui.components.ui.VexSelectableChip
import com.ven.app.ui.screens.explore.exploreCategories
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexRadius
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Bottom sheet filter kategori untuk halaman Explore.
 * Sesuai DESIGN.md §8.5.4 & wireframe `Wireframe - Explore filter.png`:
 * - Header: judul "Category" (kiri, SemiBold) + aksi teks "Reset" (kanan, warna primary).
 * - Body: [FlowRow] berisi chip pill kategori yang dapat dipilih (multi-select).
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterSheet(
    categories: List<String>,
    selectedCategories: Set<String>,
    onCategoryToggle: (String) -> Unit,
    onResetClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    resetLabel: String = "Reset",
    titleLabel: String = "Category",
) {
    val colors = VexTheme.colors

    VexBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = VexSpace.s6),
        ) {
            // ---------------------------------------------------------
            // Header: Judul "Category" + aksi teks "Reset"
            // ---------------------------------------------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = VexSpace.s6, vertical = VexSpace.s2),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = titleLabel,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = colors.text,
                )

                Text(
                    text = resetLabel,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = colors.primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(VexRadius.full))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(bounded = false),
                            role = Role.Button,
                            onClick = onResetClick,
                        )
                        .padding(horizontal = VexSpace.s2, vertical = VexSpace.s1),
                )
            }

            // ---------------------------------------------------------
            // Body: FlowRow chip kategori
            // ---------------------------------------------------------
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = VexSpace.s6, vertical = VexSpace.s3),
                horizontalArrangement = Arrangement.spacedBy(VexSpace.s2),
                verticalArrangement = Arrangement.spacedBy(VexSpace.s2),
            ) {
                categories.forEach { category ->
                    VexSelectableChip(
                        text = category,
                        selected = category in selectedCategories,
                        onSelectedChange = { onCategoryToggle(category) },
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "CategoryFilterSheet - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun CategoryFilterSheetDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        CategoryFilterSheet(
            categories = exploreCategories,
            selectedCategories = setOf("UI/UX", "3D"),
            onCategoryToggle = {},
            onResetClick = {},
            onDismissRequest = {},
        )
    }
}
