package com.ven.app.ui.screens.requestmeeting

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BuildMonthGridTest {

    @Test
    fun buildMonthGrid_alwaysReturnsExact42Cells() {
        val testMonths = listOf(
            YearMonth.of(2026, 9),
            YearMonth.of(2026, 10),
            YearMonth.of(2026, 12),
            YearMonth.of(2027, 1),
            YearMonth.of(2027, 2),
            YearMonth.of(2028, 2),
        )

        testMonths.forEach { month ->
            val grid = buildMonthGrid(month, selectedDate = null)
            assertEquals("Grid size for $month must be 42", 42, grid.size)
        }
    }

    @Test
    fun buildMonthGrid_firstDayOfGridIsAlwaysMonday() {
        val testMonths = listOf(
            YearMonth.of(2026, 9),
            YearMonth.of(2026, 10),
            YearMonth.of(2026, 12),
            YearMonth.of(2027, 1),
            YearMonth.of(2027, 2),
            YearMonth.of(2028, 2),
        )

        testMonths.forEach { month ->
            val grid = buildMonthGrid(month, selectedDate = null)
            assertEquals(
                "First cell of grid for $month must be Monday",
                DayOfWeek.MONDAY,
                grid.first().date.dayOfWeek,
            )
        }
    }

    @Test
    fun buildMonthGrid_september2026_paddingAndIndicesCorrect() {
        val visibleMonth = YearMonth.of(2026, 9)
        val selectedDate = LocalDate.of(2026, 9, 30)
        val grid = buildMonthGrid(visibleMonth, selectedDate)

        // 2026-09-01 is Tuesday, so grid starts on Monday 2026-08-31
        assertEquals(LocalDate.of(2026, 8, 31), grid[0].date)
        assertFalse("August 31 is not September", grid[0].isCurrentMonth)
        assertFalse(grid[0].isSelected)

        // Index 1 is 2026-09-01
        assertEquals(LocalDate.of(2026, 9, 1), grid[1].date)
        assertTrue(grid[1].isCurrentMonth)

        // Index 30 is 2026-09-30 (Selected)
        assertEquals(LocalDate.of(2026, 9, 30), grid[30].date)
        assertTrue(grid[30].isCurrentMonth)
        assertTrue(grid[30].isSelected)

        // Index 31 is 2026-10-01
        assertEquals(LocalDate.of(2026, 10, 1), grid[31].date)
        assertFalse("October 1 is outside September", grid[31].isCurrentMonth)

        // Index 41 is 2026-10-11
        assertEquals(LocalDate.of(2026, 10, 11), grid[41].date)
        assertFalse(grid[41].isCurrentMonth)
    }

    @Test
    fun buildMonthGrid_activeDaysCountMatchesMonthLength() {
        val testMonths = listOf(
            YearMonth.of(2026, 9) to 30,
            YearMonth.of(2026, 10) to 31,
            YearMonth.of(2027, 2) to 28, // non-leap
            YearMonth.of(2028, 2) to 29, // leap
        )

        testMonths.forEach { (month, expectedDays) ->
            val grid = buildMonthGrid(month, selectedDate = null)
            val currentMonthDays = grid.count { it.isCurrentMonth }
            assertEquals("Month $month should have $expectedDays days marked isCurrentMonth", expectedDays, currentMonthDays)
        }
    }

    @Test
    fun buildMonthGrid_datesAreContinuousWithoutGaps() {
        val grid = buildMonthGrid(YearMonth.of(2026, 9), selectedDate = null)

        for (i in 0 until grid.size - 1) {
            val current = grid[i].date
            val next = grid[i + 1].date
            assertEquals(
                "Cell ${i + 1} must be exactly the day after cell $i",
                current.plusDays(1),
                next,
            )
        }
    }

    @Test
    fun buildMonthGrid_singleSelectionOnlyForMatchingDate() {
        val visibleMonth = YearMonth.of(2026, 9)
        val selectedDate = LocalDate.of(2026, 9, 15)
        val grid = buildMonthGrid(visibleMonth, selectedDate)

        val selectedCount = grid.count { it.isSelected }
        assertEquals(1, selectedCount)

        val selectedCell = grid.first { it.isSelected }
        assertEquals(selectedDate, selectedCell.date)
        assertTrue(selectedCell.isCurrentMonth)
    }

    @Test
    fun buildMonthGrid_selectionOutsideVisibleMonth_noCellSelected() {
        val visibleMonth = YearMonth.of(2026, 9)
        // Date outside visible range entirely or in another month
        val selectedDate = LocalDate.of(2026, 8, 15)
        val grid = buildMonthGrid(visibleMonth, selectedDate)

        val selectedCount = grid.count { it.isSelected }
        assertEquals("Date from previous non-visible range should not be selected", 0, selectedCount)
    }

    @Test
    fun buildMonthGrid_rolloverDecemberToJanuary() {
        val visibleMonth = YearMonth.of(2026, 12)
        val grid = buildMonthGrid(visibleMonth, selectedDate = null)

        // 2026-12-01 is Tuesday -> start on Monday 2026-11-30
        assertEquals(LocalDate.of(2026, 11, 30), grid[0].date)
        assertFalse(grid[0].isCurrentMonth)

        assertEquals(LocalDate.of(2026, 12, 1), grid[1].date)
        assertTrue(grid[1].isCurrentMonth)

        // 31 days in Dec -> index 31 is Dec 31
        assertEquals(LocalDate.of(2026, 12, 31), grid[31].date)
        assertTrue(grid[31].isCurrentMonth)

        // Next month rollover to 2027
        assertEquals(LocalDate.of(2027, 1, 1), grid[32].date)
        assertFalse(grid[32].isCurrentMonth)
    }

    @Test
    fun buildMonthGrid_rolloverJanuaryStartsFromPreviousDecember() {
        val visibleMonth = YearMonth.of(2027, 1)
        val grid = buildMonthGrid(visibleMonth, selectedDate = null)

        // 2027-01-01 is Friday (dow=5) -> offset 4 -> start on Monday 2026-12-28
        assertEquals(LocalDate.of(2026, 12, 28), grid[0].date)
        assertFalse(grid[0].isCurrentMonth)

        assertEquals(LocalDate.of(2027, 1, 1), grid[4].date)
        assertTrue(grid[4].isCurrentMonth)
    }

    @Test
    fun buildMonthGrid_leapYearFebruary2028_has29ActiveDays() {
        val visibleMonth = YearMonth.of(2028, 2)
        val grid = buildMonthGrid(visibleMonth, selectedDate = null)

        val activeCount = grid.count { it.isCurrentMonth }
        assertEquals(29, activeCount)

        val feb29 = grid.firstOrNull { it.date == LocalDate.of(2028, 2, 29) }
        assertTrue(feb29 != null && feb29.isCurrentMonth)
    }

    @Test
    fun buildMonthGrid_nonLeapYearFebruary2027_has28ActiveDays() {
        val visibleMonth = YearMonth.of(2027, 2)
        val grid = buildMonthGrid(visibleMonth, selectedDate = null)

        val activeCount = grid.count { it.isCurrentMonth }
        assertEquals(28, activeCount)

        val feb28 = grid.firstOrNull { it.date == LocalDate.of(2027, 2, 28) }
        assertTrue("February 28th exists and is in current month", feb28 != null && feb28.isCurrentMonth)

        val hasDay29OrMore = grid.any { it.isCurrentMonth && it.date.dayOfMonth >= 29 }
        assertFalse("February 2027 must not have any day 29 or higher in current month", hasDay29OrMore)
    }

    @Test
    fun buildMonthGrid_monthStartingOnMonday_hasZeroOffset() {
        // 2025-12-01 is Monday
        val visibleMonth = YearMonth.of(2025, 12)
        val grid = buildMonthGrid(visibleMonth, selectedDate = null)

        assertEquals(LocalDate.of(2025, 12, 1), grid[0].date)
        assertTrue("First cell is December 1st when month starts on Monday", grid[0].isCurrentMonth)
    }

    @Test
    fun buildMonthGrid_monthStartingOnSunday_hasOffsetOfSix() {
        // 2026-02-01 is Sunday (DayOfWeek.SUNDAY.value = 7) -> offset = 7 - 1 = 6
        // Start date is Monday 2026-01-26
        val visibleMonth = YearMonth.of(2026, 2)
        val grid = buildMonthGrid(visibleMonth, selectedDate = null)

        assertEquals(LocalDate.of(2026, 1, 26), grid[0].date)
        assertFalse(grid[0].isCurrentMonth)

        assertEquals(LocalDate.of(2026, 2, 1), grid[6].date)
        assertTrue(grid[6].isCurrentMonth)
    }

    @Test
    fun buildMonthGrid_dayNumberProperty_matchesDayOfMonth() {
        val grid = buildMonthGrid(YearMonth.of(2026, 9), selectedDate = null)

        grid.forEach { cell ->
            assertEquals(cell.date.dayOfMonth, cell.dayNumber)
        }
    }
}
