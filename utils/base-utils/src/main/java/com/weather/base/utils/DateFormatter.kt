package com.weather.base.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

object DateFormatter {

    /** Воскренье, 15 Января */
    private const val TODAY_PATTERN = "EEEE, d MMM"
    private const val DEFAULT_PATTERN = "dd.MM.yyyy"
    private const val TIME_PATTERN = "HH:mm"
    private const val DAY_MONTH_PATTERN = "d MMMM"

    private val dayMonthDateTimeFormatter =
        DateTimeFormatter.ofPattern(DAY_MONTH_PATTERN)

    private val defaultDateFormatter =
        DateTimeFormatter.ofPattern(DEFAULT_PATTERN)

    private val todayDateTimeFormatter =
        DateTimeFormatter.ofPattern(TODAY_PATTERN)

    /**
     * @return Сегодняшняя дата в формате: Воскренье, 15 Января
     */
    fun getTodayDate(): String {
        return LocalDateTime.now().format(todayDateTimeFormatter).replaceFirstChar(Char::titlecase)
    }

    /**
     * @param date - дата, которую надо отформатировать
     * @return Получить дату в формате: dd.MM.yyyy
     */
    fun getDefaultFormatDate(date: Long): String {
        return toLocalDateTime(date).format(defaultDateFormatter)
    }

    /**
     * @return Возвращает следующие сутки в миллисекундах
     */
    fun getNextDateTime(): Long {
        return LocalDateTime.now().plusDays(1).toEpochSecond()
    }

    /**
     * @param date - дата, которую надо отформатировать
     * @return Получить время в формате HH:mm
     */
    fun getTime(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ofPattern(TIME_PATTERN))
    }

    /**
     * @param date - дата, которую надо отформатировать
     * @return Получить время в формате d MMMM
     */
    fun getDayMonth(date: LocalDate): String {
        return date.format(dayMonthDateTimeFormatter)
    }

    fun timeIsMatch(date: LocalDateTime, hour: Int): Boolean {
        return date.toLocalTime().hour == hour
    }

    fun LocalDateTime.toEpochSecond(): Long {
        return toEpochSecond(ZoneOffset.UTC)
    }

    fun toLocalDateTime(dateTime: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(dateTime, 0, ZoneOffset.UTC)
    }

}