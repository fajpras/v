package com.ven.app.ui.screens.explore

import androidx.compose.runtime.Immutable
import com.ven.app.R

/**
 * Data dummy lokal untuk alur Explore.
 *
 * Seluruh pencarian (keyword, kategori, tab) dilakukan lokal di memori.
 * TIDAK ada integrasi backend pencarian. Ganti sumber data ini dengan
 * repository/API saat backend pencarian tersedia.
 */

/** Satu karya pada grid Explore beserta kategorinya untuk filter. */
@Immutable
data class ExplorePost(
    val id: String,
    val title: String,
    val imageRes: Int,
    val categories: Set<String>,
    val username: String,
    val description: String,
    val timeAgo: String,
)

/** Satu akun pada hasil pencarian tab Accounts. */
@Immutable
data class ExploreAccount(
    val username: String,
    val displayName: String,
)

/** Satu banner pameran pada hasil pencarian tab Exhibits. */
@Immutable
data class ExploreExhibit(
    val id: String,
    val title: String,
    val imageRes: Int,
    val owner: String,
    val description: String,
    val timeAgo: String,
)

/** Satu baris histori pada halaman Recents. */
@Immutable
data class SearchRecent(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val isKeyword: Boolean = false,
)

val dummyExploreAccounts = listOf(
    ExploreAccount(
        username = "vex.pbl204",
        displayName = "Virtual Exhibition (VEX)",
    ),
    ExploreAccount(
        username = "valuexnet.310",
        displayName = "Value Exchange Network (VEN)",
    ),
    ExploreAccount(
        username = "gra._.aph",
        displayName = "Terra Faqih Satria Madjid",
    ),
    ExploreAccount(
        username = "shaf.one",
        displayName = "Shafwan Fuad",
    ),
)

val dummyExplorePosts = listOf(
    ExplorePost(
        id = "ex1",
        title = "SENIDZ",
        imageRes = R.drawable.poster_1,
        categories = setOf("Software", "UI/UX"),
        username = "vex.pbl204",
        description = "Peletakan kalimat ini akan diisi dengan deskripsi yang akan menjelaskan dengan lebih rinci lagi dari apa yang ingin disampaikan oleh kreator mengenai karyanya.",
        timeAgo = "17 hours ago",
    ),
    ExplorePost(
        id = "ex2",
        title = "PBL EXPO",
        imageRes = R.drawable.poster_2,
        categories = setOf("Software", "Game Dev"),
        username = "valuexnet.310",
        description = "Pameran karya PBL EXPO 2026. Kunjungi showroom untuk melihat detail karya dan tinggalkan feedback.",
        timeAgo = "1 day ago",
    ),
    ExplorePost(
        id = "ex3",
        title = "Virtual Exhibition",
        imageRes = R.drawable.poster_3,
        categories = setOf("3D", "Animation"),
        username = "gra._.aph",
        description = "Virtual exhibition experience yang dibangun dengan fotografi dan 3D environment interaktif.",
        timeAgo = "2 days ago",
    ),
    ExplorePost(
        id = "ex4",
        title = "EcoTrack",
        imageRes = R.drawable.poster_2,
        categories = setOf("Software", "UI/UX"),
        username = "shaf.one",
        description = "EcoTrack membantu melacak jejak karbon harian dengan tampilan dashboard yang simpel dan informatif.",
        timeAgo = "3 days ago",
    ),
    ExplorePost(
        id = "ex5",
        title = "Runflow",
        imageRes = R.drawable.poster_3,
        categories = setOf("UI/UX", "Software"),
        username = "vex.pbl204",
        description = "Runflow adalah konsep aplikasi lari dengan panduan visual dan statistik latihan yang mudah dibaca.",
        timeAgo = "4 days ago",
    ),
    ExplorePost(
        id = "ex6",
        title = "Problem and Solution",
        imageRes = R.drawable.poster_1,
        categories = setOf("UI/UX", "Software"),
        username = "valuexnet.310",
        description = "Studi kasus problem dan solution untuk aplikasi mobile dengan pendekatan design thinking.",
        timeAgo = "5 days ago",
    ),
    ExplorePost(
        id = "ex7",
        title = "Lilies",
        imageRes = R.drawable.poster_3,
        categories = setOf("2D", "Photography"),
        username = "gra._.aph",
        description = "Seri poster bunga lily dengan eksplorasi tipografi dan komposisi warna pastel.",
        timeAgo = "6 days ago",
    ),
    ExplorePost(
        id = "ex8",
        title = "Open Source Competition",
        imageRes = R.drawable.poster_1,
        categories = setOf("Software", "Cybersecurity"),
        username = "vex.pbl204",
        description = "Poster kompetisi open source clash of code yang diselenggarakan komunitas developer kampus.",
        timeAgo = "1 week ago",
    ),
    ExplorePost(
        id = "ex9",
        title = "NSC Showcase",
        imageRes = R.drawable.poster_2,
        categories = setOf("Software", "Animation"),
        username = "valuexnet.310",
        description = "Showcase karya finalis NSC dengan galeri interaktif dan voting pengunjung.",
        timeAgo = "1 week ago",
    ),
    ExplorePost(
        id = "ex10",
        title = "Everything Delivered",
        imageRes = R.drawable.poster_1,
        categories = setOf("UI/UX", "Software"),
        username = "shaf.one",
        description = "Konsep aplikasi delivery dengan pelacakan paket real-time dan estimasi tiba yang akurat.",
        timeAgo = "2 weeks ago",
    ),
    ExplorePost(
        id = "ex11",
        title = "PosPe New UI",
        imageRes = R.drawable.poster_2,
        categories = setOf("UI/UX", "Software"),
        username = "vex.pbl204",
        description = "New UI, smarter flow, more services. Redesign aplikasi layanan dengan navigasi yang lebih simpel.",
        timeAgo = "2 weeks ago",
    ),
    ExplorePost(
        id = "ex12",
        title = "Social Media App",
        imageRes = R.drawable.poster_3,
        categories = setOf("UI/UX", "Software"),
        username = "gra._.aph",
        description = "Desain aplikasi social media dengan fokus pada koneksi komunitas dan berbagi momen.",
        timeAgo = "3 weeks ago",
    ),
    ExplorePost(
        id = "ex13",
        title = "Runflow Blue",
        imageRes = R.drawable.poster_2,
        categories = setOf("UI/UX", "Videography"),
        username = "shaf.one",
        description = "Varian biru dari seri Runflow dengan video promosi dan motion graphic.",
        timeAgo = "3 weeks ago",
    ),
    ExplorePost(
        id = "ex14",
        title = "EcoTrack Night",
        imageRes = R.drawable.poster_1,
        categories = setOf("UI/UX", "Software"),
        username = "valuexnet.310",
        description = "Tampilan mode malam EcoTrack dengan kontras yang nyaman untuk pemantauan progres harian.",
        timeAgo = "1 month ago",
    ),
    ExplorePost(
        id = "ex15",
        title = "Lilies Pink",
        imageRes = R.drawable.poster_3,
        categories = setOf("2D", "Photography"),
        username = "gra._.aph",
        description = "Varian kedua seri Lilies dengan dominasi warna pink dan tekstur grain.",
        timeAgo = "1 month ago",
    ),
)

val dummyExploreExhibits = listOf(
    ExploreExhibit(
        id = "exh1",
        title = "Open Source Competition",
        imageRes = R.drawable.poster_1,
        owner = "vex.pbl204",
        description = "Pameran virtual kompetisi open source dengan puluhan karya peserta dari berbagai komunitas.",
        timeAgo = "1 week ago",
    ),
    ExploreExhibit(
        id = "exh2",
        title = "PBL EXPO 2026",
        imageRes = R.drawable.poster_2,
        owner = "valuexnet.310",
        description = "PBL EXPO 2026: innovate, collaborate, elevate. Jelajahi stand pameran dalam ruang 3D.",
        timeAgo = "2 weeks ago",
    ),
    ExploreExhibit(
        id = "exh3",
        title = "Virtual Exhibition",
        imageRes = R.drawable.poster_3,
        owner = "vex.pbl204",
        description = "Ruang pameran virtual dengan tur interaktif dan kurasi karya pilihan.",
        timeAgo = "3 weeks ago",
    ),
)

val dummySearchRecents = listOf(
    SearchRecent(id = "r1", title = "vex", isKeyword = true),
    SearchRecent(
        id = "r2",
        title = "gra._.aph",
        subtitle = "Terra Faqih Satria Madjid",
    ),
    SearchRecent(
        id = "r3",
        title = "shaf.one",
        subtitle = "Shafwan Fuad",
    ),
)

/** Pencarian akun lokal berdasarkan username atau nama tampilan. */
fun searchExploreAccounts(query: String): List<ExploreAccount> {
    val keyword = query.trim()
    if (keyword.isEmpty()) return emptyList()
    return dummyExploreAccounts.filter {
        it.username.contains(keyword, ignoreCase = true) ||
            it.displayName.contains(keyword, ignoreCase = true)
    }
}

/** Pencarian karya lokal berdasarkan judul atau username pemilik. */
fun searchExplorePosts(query: String): List<ExplorePost> {
    val keyword = query.trim()
    if (keyword.isEmpty()) return emptyList()
    return dummyExplorePosts.filter {
        it.title.contains(keyword, ignoreCase = true) ||
            it.username.contains(keyword, ignoreCase = true)
    }
}

/** Pencarian pameran lokal berdasarkan judul atau pemilik. */
fun searchExploreExhibits(query: String): List<ExploreExhibit> {
    val keyword = query.trim()
    if (keyword.isEmpty()) return emptyList()
    return dummyExploreExhibits.filter {
        it.title.contains(keyword, ignoreCase = true) ||
            it.owner.contains(keyword, ignoreCase = true)
    }
}

/** Model tampilan halaman detail (Expostvex) untuk post maupun exhibit. */
@Immutable
data class ExploreDetail(
    val id: String,
    val isExhibit: Boolean,
    val username: String,
    val title: String,
    val imageRes: Int,
    val aspectRatio: Float,
    val description: String,
    val timeAgo: String,
)

/** Mencari detail berdasarkan id karya atau id pameran. Null bila tidak dikenal. */
fun findExploreDetail(id: String): ExploreDetail? {
    dummyExplorePosts.firstOrNull { it.id == id }?.let { post ->
        return ExploreDetail(
            id = post.id,
            isExhibit = false,
            username = post.username,
            title = post.title,
            imageRes = post.imageRes,
            aspectRatio = 4f / 5f,
            description = post.description,
            timeAgo = post.timeAgo,
        )
    }
    dummyExploreExhibits.firstOrNull { it.id == id }?.let { exhibit ->
        return ExploreDetail(
            id = exhibit.id,
            isExhibit = true,
            username = exhibit.owner,
            title = exhibit.title,
            imageRes = exhibit.imageRes,
            aspectRatio = 16f / 9f,
            description = exhibit.description,
            timeAgo = exhibit.timeAgo,
        )
    }
    return null
}
