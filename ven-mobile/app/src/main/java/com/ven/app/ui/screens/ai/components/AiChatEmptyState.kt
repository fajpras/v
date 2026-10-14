package com.ven.app.ui.screens.ai.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ven.app.R
import com.ven.app.ui.theme.ElmsSans
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Tampilan kondisi kosong (Empty State) untuk halaman AI Assistant (DESIGN.md §8.12.2).
 * Sesuai wireframe `Wireframe - AI Chat.png`:
 * - Watermark sparkle besar bintang 4 sudut di tengah-atas (opasitas ±8%).
 * - Kolom input pill di tengah vertikal kanvas.
 * - Teks label "AI assistant" di bawah kolom input.
 */
@Composable
fun AiChatEmptyState(
    inputText: String,
    onInputChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        // 1. Watermark Sparkle di Tengah-Atas (sepertiga atas layar)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_nav_ai_filled),
                contentDescription = stringResource(R.string.ai_chat_sparkle_desc),
                modifier = Modifier.size(150.dp),
                colorFilter = ColorFilter.tint(colors.text.copy(alpha = 0.08f)),
            )
        }

        // 2. Kolom Input Pill & Label "AI assistant" di Tengah Layar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AiChatInput(
                value = inputText,
                onValueChange = onInputChange,
                onSend = onSend,
            )

            Spacer(modifier = Modifier.height(VexSpace.s4))

            Text(
                text = stringResource(R.string.ai_chat_title),
                style = TextStyle(
                    fontFamily = ElmsSans,
                    fontSize = 18.sp,
                    color = colors.text,
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

@Preview(name = "AiChatEmptyState - Dark")
@Composable
private fun AiChatEmptyStateDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        AiChatEmptyState(
            inputText = "",
            onInputChange = {},
            onSend = {},
        )
    }
}

@Preview(name = "AiChatEmptyState - Light")
@Composable
private fun AiChatEmptyStateLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        AiChatEmptyState(
            inputText = "",
            onInputChange = {},
            onSend = {},
        )
    }
}
