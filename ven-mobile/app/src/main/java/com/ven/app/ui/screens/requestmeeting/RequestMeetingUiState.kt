package com.ven.app.ui.screens.requestmeeting

import androidx.compose.runtime.Immutable
import com.ven.app.util.DateFormatter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

/**
 * Representasi satu hari dalam kalender grid 7 kolom.
 */
@Immutable
data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isSelected: Boolean = false,
) {
    val dayNumber: Int get() = date.dayOfMonth
}

/**
 * UI State untuk halaman Request Meeting.
 * Sesuai spesifikasi DESIGN.md §8.6.1 dengan kalender dinamis (java.time).
 */
@Immutable
data class RequestMeetingUiState(
    val pameranId: String = "1",
    val pameranTitle: String = "6th International Expo",
    val bannerUrl: String? = null,
    val visibleMonth: YearMonth = YearMonth.of(2026, 9),
    val selectedDate: LocalDate = LocalDate.of(2026, 9, 30),
    val selectedDateText: String = DateFormatter.formatRequestMeetingDate(LocalDate.of(2026, 9, 30)),
    val monthTitleText: String = DateFormatter.formatMonthYear(YearMonth.of(2026, 9)),
    val calendarDays: List<CalendarDay> = emptyList(),
    val hourText: String = "02",
    val minuteText: String = "30",
    val isAm: Boolean = false, // false = PM (sesuai wireframe aktif di PM)
    val message: String = "",
    val isLoading: Boolean = false,
    val isSending: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)

/**
 * Membangun 42 sel (6 minggu x 7 hari) untuk kalender dinamis.
 * Dimulai dari hari Senin (Monday-first) sesuai wireframe.
 */
fun buildMonthGrid(
    visibleMonth: YearMonth,
    selectedDate: LocalDate?,
): List<CalendarDay> {
    val firstDayOfMonth = visibleMonth.atDay(1)
    // DayOfWeek: MONDAY = 1 ... SUNDAY = 7
    val daysBefore = firstDayOfMonth.dayOfWeek.value - DayOfWeek.MONDAY.value
    val startDate = firstDayOfMonth.minusDays(daysBefore.toLong())

    val totalDays = 42 // 6 baris x 7 kolom agar tinggi grid selalu konsisten
    val days = ArrayList<CalendarDay>(totalDays)

    for (i in 0 until totalDays) {
        val currentDate = startDate.plusDays(i.toLong())
        val isCurrentMonth = (currentDate.month == visibleMonth.month && currentDate.year == visibleMonth.year)
        val isSelected = (selectedDate != null && currentDate == selectedDate)

        days.add(
            CalendarDay(
                date = currentDate,
                isCurrentMonth = isCurrentMonth,
                isSelected = isSelected,
            )
        )
    }

    return days
}
