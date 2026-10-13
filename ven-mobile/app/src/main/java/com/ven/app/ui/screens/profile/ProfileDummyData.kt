package com.ven.app.ui.screens.profile

import com.ven.app.R
import com.ven.app.ui.components.feature.MediaGridItem

val dummyProfile = Profile(
    username = "gra._.aph",
    fullName = "Terra Faqih Satria Madjid",
    postCount = 2,
    exhibitCount = 1,
    skills = listOf(
        Skill("3D"),
        Skill("2D"),
        Skill("Animation"),
        Skill("Game Dev"),
        Skill("UI/UX"),
        Skill("Software"),
    ),
    bio = "Seorang yang memiliki ketertarikan pada dunia 3D dan gameDev yang sedang membuka pameran.",
    email = "truebadluck146@gmail.com",
    phone = "082387593452",
    avatarRes = null,
)

val dummyProfilePosts = listOf(
    MediaGridItem(
        id = "p1",
        title = "PBL EXPO 2026",
        imageRes = R.drawable.poster_1,
    ),
    MediaGridItem(
        id = "p2",
        title = "Virtual Exhibition VEX",
        imageRes = R.drawable.poster_2,
    ),
)

val dummyProfileExhibits = listOf(
    MediaGridItem(
        id = "e1",
        title = "Open Source Competition",
        imageRes = R.drawable.poster_3,
    ),
)

val dummyProfileUiState = ProfileUiState(
    isLoading = false,
    isOwnProfile = true,
    profile = dummyProfile,
    selectedTab = ProfileTab.Posts,
    posts = dummyProfilePosts,
    exhibits = dummyProfileExhibits,
    isSkillSheetOpen = false,
    errorMessage = null,
)

val dummyProfileLoadingState = ProfileUiState(
    isLoading = true,
)

val dummyProfileEmptyState = dummyProfileUiState.copy(
    posts = emptyList(),
    exhibits = emptyList(),
)

val dummyProfileErrorState = ProfileUiState(
    errorMessage = "Failed to load profile",
)
