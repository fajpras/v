package com.ven.app.ui.screens.requestmeeting

import java.time.LocalDate
import java.time.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RequestMeetingUiStateTest {

    @Test
    fun defaultState_hasExpectedDefaultValues() {
        val state = RequestMeetingUiState()

        assertEquals("1", state.pameranId)
        assertEquals("6th International Expo", state.pameranTitle)
        assertNull(state.bannerUrl)
        assertEquals(YearMonth.of(2026, 9), state.visibleMonth)
        assertEquals(LocalDate.of(2026, 9, 30), state.selectedDate)
        assertEquals("Wed, 30 September 2026", state.selectedDateText)
        assertEquals("September 2026", state.monthTitleText)
        assertTrue(state.calendarDays.isEmpty())

        assertEquals("02", state.hourText)
        assertEquals("30", state.minuteText)
        assertFalse("Default should be PM (isAm = false)", state.isAm)
        assertEquals("", state.message)

        assertFalse(state.isLoading)
        assertFalse(state.isSending)
        assertNull(state.errorMessage)
        assertFalse(state.isSuccess)
    }

    @Test
    fun calendarDay_dayNumberComputedFromDate() {
        val day = CalendarDay(
            date = LocalDate.of(2026, 9, 15),
            isCurrentMonth = true,
        )

        assertEquals(15, day.dayNumber)
        assertFalse(day.isSelected)
        assertTrue(day.isCurrentMonth)
    }
}
