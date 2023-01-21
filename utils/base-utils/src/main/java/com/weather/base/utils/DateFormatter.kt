package com.weather.base.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    /** Воскренье, 15 Января */
    private const val TODAY_PATTERN = "EEEE, d MMM"
    private const val DEFAULT_PATTERN = "dd.MM.yyyy"

    private val todayDateFormatter = SimpleDateFormat(TODAY_PATTERN, Locale.forLanguageTag("RU"))
    private val defaultDateFormatter =
        SimpleDateFormat(DEFAULT_PATTERN, Locale.forLanguageTag("RU"))


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
        return defaultDateFormatter.format(Date(date))
    }

    /**
     * @return Получить текущую дату в формате: dd.MM.yyyy
     */
    fun getCurrentDateInDefaultFormat(): String {
        return getDefaultFormatDate(Date().time)
    }

}