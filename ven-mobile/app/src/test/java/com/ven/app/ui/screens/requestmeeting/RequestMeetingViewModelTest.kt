package com.ven.app.ui.screens.requestmeeting

import java.time.LocalDate
import java.time.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RequestMeetingViewModelTest {

    @Test
    fun initialState_hasDefaultDynamicCalendarValues() {
        val vm = RequestMeetingViewModel()
        val state = vm.uiState.value

        assertEquals(YearMonth.of(2026, 9), state.visibleMonth)
        assertEquals(LocalDate.of(2026, 9, 30), state.selectedDate)
        assertEquals("September 2026", state.monthTitleText)
        assertEquals("Wed, 30 September 2026", state.selectedDateText)
        assertEquals(42, state.calendarDays.size)

        // 2026-09-30 should be the selected cell
        val selectedCell = state.calendarDays.firstOrNull { it.isSelected }
        assertNotNull(selectedCell)
        assertEquals(LocalDate.of(2026, 9, 30), selectedCell?.date)
    }

    @Test
    fun onDaySelected_currentMonth_updatesSelectedDateAndText() {
        val vm = RequestMeetingViewModel()
        val targetDay = CalendarDay(
            date = LocalDate.of(2026, 9, 15),
            isCurrentMonth = true,
        )

        vm.onDaySelected(targetDay)

        val state = vm.uiState.value
        assertEquals(LocalDate.of(2026, 9, 15), state.selectedDate)
        assertEquals("Tue, 15 September 2026", state.selectedDateText)

        val selectedCell = state.calendarDays.firstOrNull { it.isSelected }
        assertEquals(LocalDate.of(2026, 9, 15), selectedCell?.date)
    }

    @Test
    fun onDaySelected_outsideCurrentMonth_stateRemainsUnchanged() {
        val vm = RequestMeetingViewModel()
        val outsideDay = CalendarDay(
            date = LocalDate.of(2026, 8, 31),
            isCurrentMonth = false,
        )

        vm.onDaySelected(outsideDay)

        val state = vm.uiState.value
        // Must still be 2026-09-30
        assertEquals(LocalDate.of(2026, 9, 30), state.selectedDate)
        assertEquals("Wed, 30 September 2026", state.selectedDateText)
    }

    @Test
    fun onNextMonth_incrementsVisibleMonthAndRecomputesGrid() {
        val vm = RequestMeetingViewModel()

        vm.onNextMonth()

        val state = vm.uiState.value
        assertEquals(YearMonth.of(2026, 10), state.visibleMonth)
        assertEquals("October 2026", state.monthTitleText)
        assertEquals(42, state.calendarDays.size)
        assertEquals(31, state.calendarDays.count { it.isCurrentMonth })
    }

    @Test
    fun onPrevMonth_decrementsVisibleMonthAndRecomputesGrid() {
        val vm = RequestMeetingViewModel()

        vm.onPrevMonth()

        val state = vm.uiState.value
        assertEquals(YearMonth.of(2026, 8), state.visibleMonth)
        assertEquals("August 2026", state.monthTitleText)
        assertEquals(42, state.calendarDays.size)
        assertEquals(31, state.calendarDays.count { it.isCurrentMonth })
    }

    @Test
    fun onNextMonth_multipleTimes_correctlyRollsOverYear() {
        val vm = RequestMeetingViewModel()

        // 2026-09 -> 2026-10 -> 2026-11 -> 2026-12 -> 2027-01
        repeat(4) {
            vm.onNextMonth()
        }

        val state = vm.uiState.value
        assertEquals(YearMonth.of(2027, 1), state.visibleMonth)
        assertEquals("January 2027", state.monthTitleText)
    }

    @Test
    fun onHourChange_sanitizesToMaxTwoDigits() {
        val vm = RequestMeetingViewModel()

        vm.onHourChange("123")
        assertEquals("12", vm.uiState.value.hourText)

        vm.onHourChange("ab")
        assertEquals("", vm.uiState.value.hourText)

        vm.onHourChange("09")
        assertEquals("09", vm.uiState.value.hourText)
    }

    @Test
    fun onMinuteChange_sanitizesToMaxTwoDigits() {
        val vm = RequestMeetingViewModel()

        vm.onMinuteChange("450")
        assertEquals("45", vm.uiState.value.minuteText)

        vm.onMinuteChange("xyz")
        assertEquals("", vm.uiState.value.minuteText)

        vm.onMinuteChange("00")
        assertEquals("00", vm.uiState.value.minuteText)
    }

    @Test
    fun onPeriodChange_togglesBetweenAmAndPm() {
        val vm = RequestMeetingViewModel()

        vm.onPeriodChange(isAm = true)
        assertTrue(vm.uiState.value.isAm)

        vm.onPeriodChange(isAm = false)
        assertFalse(vm.uiState.value.isAm)
    }

    @Test
    fun onMessageChange_updatesMessageInState() {
        val vm = RequestMeetingViewModel()

        vm.onMessageChange("Halo, ingin diskusi kolaborasi booth.")
        assertEquals("Halo, ingin diskusi kolaborasi booth.", vm.uiState.value.message)
    }

    @Test
    fun sendRequest_withBlankHour_synchronouslySetsErrorMessageWithoutSending() {
        val vm = RequestMeetingViewModel()
        vm.onHourChange("")

        vm.sendRequest()

        val state = vm.uiState.value
        assertEquals("Waktu pertemuan harus diisi", state.errorMessage)
        assertFalse(state.isSending)
        assertFalse(state.isSuccess)
    }

    @Test
    fun sendRequest_withBlankMinute_synchronouslySetsErrorMessageWithoutSending() {
        val vm = RequestMeetingViewModel()
        vm.onMinuteChange("")

        vm.sendRequest()

        val state = vm.uiState.value
        assertEquals("Waktu pertemuan harus diisi", state.errorMessage)
        assertFalse(state.isSending)
        assertFalse(state.isSuccess)
    }
}
