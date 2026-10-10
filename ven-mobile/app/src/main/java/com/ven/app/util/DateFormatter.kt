package com.ven.app.util

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Utilitas pemformat tanggal untuk aplikasi VEN.
 * Sesuai spesifikasi arsitektur DESIGN.md §3.2 & §8.6.1.
 */
object DateFormatter {

    private val requestMeetingPattern: DateTimeFormatter =
        DateTimeFormatter.ofPattern("EEE, d MMMM yyyy", Locale.ENGLISH)

    private val monthYearPattern: DateTimeFormatter =
        DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)

    /**
     * Memformat tanggal menjadi format "Sat, 26 September 2026".
     */
    fun formatRequestMeetingDate(date: LocalDate): String {
        return date.format(requestMeetingPattern)
    }

    /**
     * Memformat YearMonth menjadi format "September 2026".
     */
    fun formatMonthYear(yearMonth: YearMonth): String {
        return yearMonth.format(monthYearPattern)
    }
}
