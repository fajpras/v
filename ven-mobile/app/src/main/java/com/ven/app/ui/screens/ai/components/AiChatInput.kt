package com.ven.app.ui.screens.ai.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ven.app.R
import com.ven.app.ui.icons.VexIcon
import com.ven.app.ui.icons.VexIcons
import com.ven.app.ui.theme.ElmsSans
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexRadius
import com.ven.app.ui.theme.VexSize
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Kolom input berbentuk Pill untuk halaman AI Assistant (DESIGN.md §8.12.4).
 * - Bentuk: Pill sempurna (VexRadius.full / 100dp).
 * - Tinggi: 58dp (ergonomis & lega untuk input percakapan).
 * - Warna Latar: surfaceInput (#363636 / putih 10% di dark mode).
 * - Placeholder: "Type Something" (textMuted).
 * - Tombol Kirim: Ikon panah muncul dinamis saat ada teks.
 */
@Composable
fun AiChatInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.ai_chat_placeholder),
    enabled: Boolean = true,
) {
    val colors = VexTheme.colors
    val shape = RoundedCornerShape(VexRadius.full)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(shape)
            .background(colors.surfaceInput)
            .padding(start = VexSpace.s6, end = VexSpace.s2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Area Ketikan Teks
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            enabled = enabled,
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = ElmsSans,
                fontSize = 17.sp,
                color = colors.text,
            ),
            cursorBrush = SolidColor(colors.primaryText),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    if (value.isNotBlank() && enabled) {
                        onSend()
                    }
                },
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(
                                fontFamily = ElmsSans,
                                fontSize = 17.sp,
                                color = colors.textMuted,
                            ),
                        )
                    }
                    innerTextField()
                }
            },
        )

        // Tombol Kirim Dinamis
        AnimatedVisibility(
            visible = value.isNotBlank(),
            enter = fadeIn() + scaleIn(initialScale = 0.8f),
            exit = fadeOut() + scaleOut(targetScale = 0.8f),
        ) {
            IconButton(
                onClick = onSend,
                enabled = enabled && value.isNotBlank(),
                modifier = Modifier.size(VexSize.minTouch),
            ) {
                VexIcon(
                    id = VexIcons.Send,
                    contentDescription = stringResource(R.string.ai_chat_send_desc),
                    tint = colors.text,
                    size = 20.dp,
                )
            }
        }
    }
}

@Preview(name = "AiChatInput - Empty - Dark")
@Composable
private fun AiChatInputEmptyDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        AiChatInput(
            value = "",
            onValueChange = {},
            onSend = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "AiChatInput - Filled - Dark")
@Composable
private fun AiChatInputFilledDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        AiChatInput(
            value = "Rekomendasikan karya...",
            onValueChange = {},
            onSend = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
