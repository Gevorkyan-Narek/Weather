package com.weather.base.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    /** Воскренье, 15 Января */
    private const val TODAY_PATTERN = "EEEE, d MMM"
    private const val DEFAULT_PATTERN = "dd.MM.yyyy"
    private const val TIME_PATTERN = "HH:mm"
    private const val DAY_MONTH_PATTERN = "d MMMM"

    private val ruLocale = Locale.forLanguageTag("RU")

    private val todayDateFormatter = SimpleDateFormat(TODAY_PATTERN, ruLocale)
    private val defaultDateFormatter = SimpleDateFormat(DEFAULT_PATTERN, ruLocale)

    private val dayMonthDateTimeFormatter =
        DateTimeFormatter.ofPattern(DAY_MONTH_PATTERN)

    /**
     * @return Сегодняшняя дата в формате: Воскренье, 15 Января
     */
    fun getTodayDate(): String {
        return todayDateFormatter.format(Date()).replaceFirstChar(Char::titlecase)
    }

    /**
     * @param date - дата, которую надо отформатировать
     * @return Получить дату в формате: dd.MM.yyyy
     */
    fun getDefaultFormatDate(date: Long): String {
        return (defaultDateFormatter.format(Date(toMs(date))))
    }

    /**
     * @return Возвращает следующие сутки в миллисекундах
     */
    fun getNextDateTime(): Long {
        return toMs(LocalDateTime.now().plusDays(1).toEpochSecond(ZoneOffset.UTC))
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

    fun toMs(date: Long): Long {
        return date * 1000
    }

}