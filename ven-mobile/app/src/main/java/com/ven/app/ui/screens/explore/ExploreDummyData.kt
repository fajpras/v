package com.ven.app.ui.screens.explore

import com.ven.app.R
import com.ven.app.ui.components.feature.MediaGridItem

/**
 * Daftar 14 kategori minat VEN sesuai DESIGN.md §8.5.4 (Category Filter Sheet).
 * Sumber: Interest onboarding & wireframe `Wireframe - Explore filter.png`.
 */
val exploreCategories: List<String> = listOf(
    "Cybersecurity",
    "Software",
    "UI/UX",
    "Videography",
    "Photography",
    "3D",
    "Animation",
    "Internet Of Things",
    "Automation System",
    "Game Dev",
    "Fabrication",
    "Manufacturing",
    "2D",
    "Others",
)

/**
 * Riwayat pencarian dummy sesuai wireframe `Wireframe - Explore history.png`.
 */
val dummyRecentSearches: List<RecentSearchItem> = listOf(
    RecentSearchItem.Query(id = "recent_q1", query = "vex"),
    RecentSearchItem.Account(
        id = "recent_a1",
        username = "gra._.aph",
        fullName = "Terra Faqih Satria Madjid",
        avatarRes = R.drawable.poster_1,
    ),
    RecentSearchItem.Account(
        id = "recent_a2",
        username = "shaf.one",
        fullName = "Shafwan Fuad",
        avatarRes = R.drawable.poster_2,
    ),
)

/**
 * Grid media Discovery dummy (Explore default) sesuai wireframe `Wireframe - Explore.png`.
 * Memakai aset lokal poster_1/2/3 secara berulang, termasuk tile span 2 kolom
 * untuk poster horizontal "Open Source Competition".
 */
val dummyDiscoveryMedia: List<MediaGridItem> = listOf(
    MediaGridItem("d1", "Semidz", R.drawable.poster_1),
    MediaGridItem("d2", "PBL Expo", R.drawable.poster_2),
    MediaGridItem("d3", "Virtual Exhibition VEX", R.drawable.poster_3),
    MediaGridItem("d4", "EcoTrack", R.drawable.poster_1),
    MediaGridItem("d5", "Sunflow", R.drawable.poster_2),
    MediaGridItem("d6", "Problem & Solution", R.drawable.poster_3),
    MediaGridItem("d7", "Lilies", R.drawable.poster_1),
    MediaGridItem("d8", "Open Source Competition", R.drawable.poster_2, span = 2),
    MediaGridItem("d9", "Click", R.drawable.poster_3),
    MediaGridItem("d10", "PooPe", R.drawable.poster_1),
    MediaGridItem("d11", "Social Media App", R.drawable.poster_2),
    MediaGridItem("d12", "Sunflow Mobile", R.drawable.poster_3),
)

/**
 * Hasil pencarian dummy untuk tab Accounts sesuai wireframe `Wireframe - Explore account.png`.
 */
val dummyResultAccounts: List<ExploreAccountItem> = listOf(
    ExploreAccountItem(
        id = "acc_1",
        username = "vex.pbl204",
        fullName = "Virtual Exhibition (VEX)",
        avatarRes = R.drawable.poster_1,
    ),
    ExploreAccountItem(
        id = "acc_2",
        username = "valuexnet.310",
        fullName = "Value Exchange Network (VEN)",
        avatarRes = R.drawable.poster_2,
    ),
)

/**
 * Hasil pencarian dummy untuk tab Posts (grid 2 kolom).
 */
val dummyResultPosts: List<MediaGridItem> = listOf(
    MediaGridItem("rp1", "Semidz", R.drawable.poster_1),
    MediaGridItem("rp2", "PBL Expo", R.drawable.poster_2),
    MediaGridItem("rp3", "Virtual Exhibition VEX", R.drawable.poster_3),
    MediaGridItem("rp4", "EcoTrack", R.drawable.poster_1),
    MediaGridItem("rp5", "Sunflow", R.drawable.poster_2),
    MediaGridItem("rp6", "Problem & Solution", R.drawable.poster_3),
)

/**
 * Hasil pencarian dummy untuk tab Exhibits (banner 16:9).
 */
val dummyResultExhibits: List<ExhibitItem> = listOf(
    ExhibitItem("ex_1", "Open Source Competition", R.drawable.poster_2),
    ExhibitItem("ex_2", "PBL EXPO 2026", R.drawable.poster_1),
)

/**
 * State awal ExploreScreen dengan seluruh dummy data terisi.
 */
val dummyExploreUiState = ExploreUiState(
    isSearchActive = false,
    searchQuery = "",
    selectedTab = ExploreSearchTab.ForYou,
    isFilterSheetOpen = false,
    selectedCategories = emptySet(),
    availableCategories = exploreCategories,
    recentSearches = dummyRecentSearches,
    discoveryMedia = dummyDiscoveryMedia,
    resultAccounts = dummyResultAccounts,
    resultPosts = dummyResultPosts,
    resultExhibits = dummyResultExhibits,
)
