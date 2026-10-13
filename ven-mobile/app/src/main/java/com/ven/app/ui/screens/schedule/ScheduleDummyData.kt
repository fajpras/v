package com.ven.app.ui.screens.schedule

import com.ven.app.R

val dummyApprovedSchedules = listOf(
    ScheduleEventItem(
        id = "app_1",
        title = "Open Source Competition",
        dateText = "24th Sep 2026",
        timeText = "02.00 PM",
        fullDateFormatted = "Thursday, 24th September 2026 at 02.00 PM",
        bannerRes = R.drawable.poster_3,
        isApproved = true,
    ),
    ScheduleEventItem(
        id = "app_2",
        title = "PBL EXPO 2026",
        dateText = "16th Oct 2026",
        timeText = "04.00 PM",
        fullDateFormatted = "Friday, 16th October 2026 at 04.00 PM",
        bannerRes = R.drawable.poster_1,
        isApproved = true,
    ),
)

val dummyWaitingSchedules = listOf(
    ScheduleEventItem(
        id = "wait_1",
        title = "Open Source Competition",
        dateText = "24th Sep 2026",
        timeText = "02.00 PM",
        fullDateFormatted = "Thursday, 24th September 2026 at 02.00 PM",
        bannerRes = R.drawable.poster_3,
        isApproved = false,
    ),
    ScheduleEventItem(
        id = "wait_2",
        title = "Virtual Exhibition VEX",
        dateText = "16th Oct 2026",
        timeText = "04.00 PM",
        fullDateFormatted = "Friday, 16th October 2026 at 04.00 PM",
        bannerRes = R.drawable.poster_2,
        isApproved = false,
    ),
)

val dummyRequestSchedules = listOf(
    ScheduleRequestItem(
        id = "req_1",
        requesterName = "Gra._.aph",
        dateText = "27/9/26",
        timeText = "02.00 PM",
        fullDateFormatted = "Thursday, 24th September 2026 at 02.00 PM",
        note = "Saya dari perusahaan citra sinar bahagia ingin berdiskusi dengan anda mengenai salah satu proyek yang sedang anda kembangkan, jika anda bersedia saya siap menawarkan sebuah kerjasama berupa kontrak.",
        bannerRes = R.drawable.poster_3,
        requesterAvatarRes = null,
    ),
    ScheduleRequestItem(
        id = "req_2",
        requesterName = "Shaf.One",
        dateText = "30/9/26",
        timeText = "08.00 PM",
        fullDateFormatted = "Wednesday, 30th September 2026 at 08.00 PM",
        note = "Saya dari perusahaan citra sinar redup ingin berdiskusi dengan anda mengenai potensi kolaborasi teknologi 3D.",
        bannerRes = R.drawable.poster_1,
        requesterAvatarRes = null,
    ),
)

val dummyScheduleUiState = ScheduleUiState(
    selectedTab = ScheduleTab.Approved,
    searchQuery = "",
    approvedItems = dummyApprovedSchedules,
    waitingItems = dummyWaitingSchedules,
    requestItems = dummyRequestSchedules,
    activeEventDetail = null,
    activeRequestDetail = null,
    userFeedbackMessage = null,
)
