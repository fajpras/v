package com.ven.app.ui.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Baris item radio untuk Theme dan Account Status.
 * - Sisi kiri: Label teks (+ deskripsi opsional).
 * - Sisi kanan: Indikator radio lingkaran (kosong jika tidak terpilih, ungu [colors.primary] jika terpilih).
 */
@Composable
fun VexRadioRow(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
) {
    val colors = VexTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = colors.text.copy(alpha = 0.15f)),
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(horizontal = VexSpace.s6, vertical = VexSpace.s4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                ),
                color = colors.text,
            )

            if (!description.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(VexSpace.s1))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textMuted,
                )
            }
        }

        // Radio indicator
        Box(
            modifier = Modifier
                .padding(start = VexSpace.s4)
                .size(22.dp)
                .clip(CircleShape)
                .border(
                    width = if (selected) 2.dp else 1.5.dp,
                    color = if (selected) colors.primary else colors.textMuted,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(colors.primary),
                )
            }
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "VexRadioRow - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun VexRadioRowDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        Column {
            VexRadioRow(label = "Light", selected = false, onClick = {})
            VexRadioRow(label = "Dark", selected = true, onClick = {})
            VexRadioRow(
                label = "Disable Account",
                selected = false,
                onClick = {},
                description = "This action will deactivate your account until you log back in and reactivate it.",
            )
        }
    }
}

@Preview(name = "VexRadioRow - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun VexRadioRowLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        Column {
            VexRadioRow(label = "Light", selected = true, onClick = {})
            VexRadioRow(label = "Dark", selected = false, onClick = {})
        }
    }
}
