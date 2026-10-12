package com.ven.app.ui.screens.profile

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.ven.app.ui.components.feature.MediaGridItem

/**
 * Tab pilihan pada halaman Profil.
 */
enum class ProfileTab {
    Posts,
    Exhibits,
}

/**
 * Data keahlian / skill pengguna.
 */
@Immutable
data class Skill(
    val label: String,
)

/**
 * Data profil pengguna.
 */
@Immutable
data class Profile(
    val username: String,
    val fullName: String,
    val postCount: Int,
    val exhibitCount: Int,
    val skills: List<Skill>,
    val bio: String,
    val email: String,
    val phone: String,
    @param:DrawableRes val avatarRes: Int? = null,
)

/**
 * UI State untuk [ProfileScreen].
 * Mengikuti arsitektur MVI/MVVM dan Unidirectional Data Flow.
 */
@Immutable
data class ProfileUiState(
    val isLoading: Boolean = false,
    val isOwnProfile: Boolean = true,
    val profile: Profile? = null,
    val selectedTab: ProfileTab = ProfileTab.Posts,
    val posts: List<MediaGridItem> = emptyList(),
    val exhibits: List<MediaGridItem> = emptyList(),
    val isSkillSheetOpen: Boolean = false,
    val errorMessage: String? = null,
) {
    val visibleSkills: List<Skill>
        get() = profile?.skills?.take(MAX_VISIBLE_SKILLS).orEmpty()

    val extraSkillCount: Int
        get() = ((profile?.skills?.size ?: 0) - MAX_VISIBLE_SKILLS).coerceAtLeast(0)

    val allSkills: List<Skill>
        get() = profile?.skills.orEmpty()

    companion object {
        const val MAX_VISIBLE_SKILLS = 4
    }
}
