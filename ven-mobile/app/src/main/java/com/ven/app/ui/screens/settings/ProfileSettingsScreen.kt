package com.ven.app.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ven.app.R
import com.ven.app.ui.components.layout.VexTopBar
import com.ven.app.ui.components.ui.Avatar
import com.ven.app.ui.components.ui.AvatarSize
import com.ven.app.ui.components.ui.VexSelectableChip
import com.ven.app.ui.components.ui.VexTextField
import com.ven.app.ui.components.ui.VexTextFieldVariant
import com.ven.app.ui.icons.VexIcon
import com.ven.app.ui.icons.VexIcons
import com.ven.app.ui.screens.onboarding.DefaultInterestCategories
import com.ven.app.ui.theme.ThemeMode
import com.ven.app.ui.theme.VexSpace
import com.ven.app.ui.theme.VexTheme

/**
 * Halaman Pengaturan & Edit Profil pengguna.
 * Sesuai wireframe `Wireframe - Setting Profile.png` dan `Profile-1.png`:
 * - Mode View: Top bar "Profile" + ikon pensil di kanan atas.
 * - Mode Edit: Top bar "Edit profile" + tombol teks "Save changes" di kanan atas.
 * - Avatar 96dp tengah + teks "New Photo".
 * - Input Boxed: Username, Name, Phone, Description.
 * - Pilihan kategori Interest dengan FlowRow [VexSelectableChip].
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileSettingsScreen(
    initialData: ProfileSettingsData,
    onBackClick: () -> Unit,
    onSaveProfile: (ProfileSettingsData) -> Unit,
    modifier: Modifier = Modifier,
    initialIsEditing: Boolean = false,
) {
    val colors = VexTheme.colors
    var isEditing by remember { mutableStateOf(initialIsEditing) }

    var username by remember { mutableStateOf(initialData.username) }
    var name by remember { mutableStateOf(initialData.name) }
    var phone by remember { mutableStateOf(initialData.phone) }
    var description by remember { mutableStateOf(initialData.description) }
    var selectedInterests by remember { mutableStateOf(initialData.interests) }

    val topBarTitle = if (isEditing) {
        stringResource(R.string.settings_edit_profile_title)
    } else {
        stringResource(R.string.settings_item_profile)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.bg,
        topBar = {
            VexTopBar.Back(
                title = topBarTitle,
                onBackClick = {
                    if (isEditing && !initialIsEditing) {
                        isEditing = false
                    } else {
                        onBackClick()
                    }
                },
                trailingContent = {
                    if (isEditing) {
                        TextButton(
                            onClick = {
                                isEditing = false
                                onSaveProfile(
                                    ProfileSettingsData(
                                        username = username,
                                        name = name,
                                        phone = phone,
                                        description = description,
                                        interests = selectedInterests,
                                    )
                                )
                            },
                        ) {
                            Text(
                                text = stringResource(R.string.settings_save_changes),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                ),
                                color = colors.primaryText,
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { isEditing = true },
                        ) {
                            VexIcon(
                                id = VexIcons.Edit,
                                contentDescription = "Edit Profile",
                                size = 22.dp,
                                tint = colors.text,
                            )
                        }
                    }
                },
            )
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Avatar 96dp + Teks "New Photo"
            Avatar(
                initials = username.ifBlank { "G" },
                size = AvatarSize.Lg,
            )

            Spacer(modifier = Modifier.height(VexSpace.s2))

            Text(
                text = stringResource(R.string.settings_new_photo),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                ),
                color = colors.primaryText,
                modifier = Modifier
                    .clickable { /* TODO: upload/change photo */ }
                    .padding(VexSpace.s1),
            )

            Spacer(modifier = Modifier.height(VexSpace.s6))

            // Form Fields
            VexTextField(
                value = username,
                onValueChange = { if (isEditing) username = it },
                variant = VexTextFieldVariant.Boxed,
                label = stringResource(R.string.settings_username_label),
                enabled = isEditing,
            )

            Spacer(modifier = Modifier.height(VexSpace.s4))

            VexTextField(
                value = name,
                onValueChange = { if (isEditing) name = it },
                variant = VexTextFieldVariant.Boxed,
                label = stringResource(R.string.settings_name_label),
                enabled = isEditing,
            )

            Spacer(modifier = Modifier.height(VexSpace.s4))

            VexTextField(
                value = phone,
                onValueChange = { if (isEditing) phone = it },
                variant = VexTextFieldVariant.Boxed,
                label = stringResource(R.string.settings_phone_label),
                enabled = isEditing,
            )

            Spacer(modifier = Modifier.height(VexSpace.s4))

            VexTextField(
                value = description,
                onValueChange = { if (isEditing) description = it },
                variant = VexTextFieldVariant.Boxed,
                label = stringResource(R.string.settings_description_label),
                enabled = isEditing,
                singleLine = false,
            )

            Spacer(modifier = Modifier.height(VexSpace.s6))

            // Seksi Interest
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.settings_interest_label),
                    style = MaterialTheme.typography.titleSmall,
                    color = colors.text,
                    modifier = Modifier.padding(bottom = VexSpace.s3),
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(VexSpace.s2),
                    verticalArrangement = Arrangement.spacedBy(VexSpace.s2),
                ) {
                    DefaultInterestCategories.forEach { category ->
                        val isSelected = category in selectedInterests
                        VexSelectableChip(
                            text = category,
                            selected = isSelected,
                            onSelectedChange = {
                                if (isEditing) {
                                    selectedInterests = if (isSelected) {
                                        selectedInterests - category
                                    } else {
                                        selectedInterests + category
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------
// Previews
// ---------------------------------------------------------

@Preview(name = "ProfileSettings View - Dark Mode", showBackground = true, backgroundColor = 0xFF1F1F1F)
@Composable
private fun ProfileSettingsViewDarkPreview() {
    VexTheme(mode = ThemeMode.Dark) {
        ProfileSettingsScreen(
            initialData = dummySettingsProfile,
            onBackClick = {},
            onSaveProfile = {},
            initialIsEditing = false,
        )
    }
}

@Preview(name = "ProfileSettings Edit - Light Mode", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProfileSettingsEditLightPreview() {
    VexTheme(mode = ThemeMode.Light) {
        ProfileSettingsScreen(
            initialData = dummySettingsProfile,
            onBackClick = {},
            onSaveProfile = {},
            initialIsEditing = true,
        )
    }
}
