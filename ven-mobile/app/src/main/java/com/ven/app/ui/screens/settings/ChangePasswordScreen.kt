package com.ven.app.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.ven.app.ui.components.layout.VexBottomActionBar
import com.ven.app.ui.components.layout.VexTopBar
import com.ven.app.ui.components.ui.VexButton
import com.ven.app.ui.components.ui.VexButtonVariant
import com.ven.app.ui.components.ui.VexPasswordField
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Halaman Change Password.
 * Sesuai wireframe `Wireframe - Setting Password.png` dan DESIGN.md §8.10:
 * - Field: Current password, New password, Confirm password.
 * - Sticky bottom CTA: [VexBottomActionBar] dengan tombol "Send OTP".
 */
@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit,
    onSendOtpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = VexTheme.colors

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isFormValid = currentPassword.isNotBlank() &&
        newPassword.isNotBlank() &&
        (newPassword == confirmPassword)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bg,
        topBar = {
            VexTopBar.Back(
                title = stringResource(R.string.settings_item_change_password),
                onBackClick = onBackClick,
            )
        },
        bottomBar = {
            VexBottomActionBar {
                VexButton(
                    text = stringResource(R.string.settings_send_otp),
                    onClick = onSendOtpClick,
                    variant = VexButtonVariant.Primary,
                    enabled = isFormValid,
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colors.bg)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = VexSpace.s6, vertical = VexSpace.s5)
                .padding(bottom = VexSpace.s8),
        ) {
            VexPasswordField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                label = stringResource(R.string.settings_current_password_label),
            )

            Spacer(modifier = Modifier.height(VexSpace.s4))

            VexPasswordField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = stringResource(R.string.settings_new_password_label),
            )

            Spacer(modifier = Modifier.height(VexSpace.s4))

            VexPasswordField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = stringResource(R.string.settings_confirm_password_label),
            )
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "ChangePassword - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun ChangePasswordDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        ChangePasswordScreen(
            onBackClick = {},
            onSendOtpClick = {},
        )
    }
}

@Preview(name = "ChangePassword - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ChangePasswordLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        ChangePasswordScreen(
            onBackClick = {},
            onSendOtpClick = {},
        )
    }
}
