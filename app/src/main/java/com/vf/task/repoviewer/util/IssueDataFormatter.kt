package com.vf.task.repoviewer.util

object IssueDataFormatter {

    fun formatDateAndName(date: String, name: String): String {
        val res = date.split("T")
        val ymd = res[0].split("-")
        val year = ymd[0]
        val month = getMonthLetters(ymd[2])
        val day = ymd[2]
        return "opened on $month $day, $year by $name"
    }

    private fun getMonthLetters(month: String): String {
        return when (month) {
            "01" -> "Jan"
            "02" -> "Feb"
            "03" -> "Mar"
            "04" -> "Apr"
            "05" -> "May"
            "06" -> "Jun"
            "07" -> "Jul"
            "08" -> "Aug"
            "09" -> "Sep"
            "10" -> "Oct"
            "11" -> "Nov"
            "12" -> "Dec"
            else -> "Sep"

        }
    }
}
