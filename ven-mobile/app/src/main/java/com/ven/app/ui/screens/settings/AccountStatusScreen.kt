package com.ven.app.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ven.app.R
import com.ven.app.ui.components.layout.VexTopBar
import com.ven.app.ui.components.ui.VexRadioRow
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexTheme

/**
 * Halaman Account Status.
 * Sesuai wireframe `Wireframe - Setting Account Status.png`:
 * - Pilihan "Disable Account" dengan teks keterangan.
 */
@Composable
fun AccountStatusScreen(
    isDisabledInitial: Boolean,
    onStatusChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors
    var isDisabled by remember { mutableStateOf(isDisabledInitial) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bg,
        topBar = {
            VexTopBar.Back(
                title = stringResource(R.string.settings_item_account_status),
                onBackClick = onBackClick,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colors.bg),
        ) {
            VexRadioRow(
                label = stringResource(R.string.settings_disable_account_title),
                description = stringResource(R.string.settings_disable_account_desc),
                selected = isDisabled,
                onClick = {
                    val newStatus = !isDisabled
                    isDisabled = newStatus
                    onStatusChange(newStatus)
                },
            )
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "AccountStatus - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun AccountStatusDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        AccountStatusScreen(
            isDisabledInitial = false,
            onStatusChange = {},
            onBackClick = {},
        )
    }
}

@Preview(name = "AccountStatus - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AccountStatusLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        AccountStatusScreen(
            isDisabledInitial = true,
            onStatusChange = {},
            onBackClick = {},
        )
    }
}
