package org.ieselcaminas.valentin.managesextinguisher

import android.annotation.SuppressLint
import org.ieselcaminas.valentin.managesextinguisher.database.extinguisher.Extinguisher
import org.ieselcaminas.valentin.managesextinguisher.database.flask.Flask
import java.text.SimpleDateFormat
import java.util.*

/**
 * Take the Long milliseconds returned by the system and stored in Room,
 * and convert it to a nicely formatted string for display.
 *
 * EEEE - Display the long letter version of the weekday
 * MMM - Display the letter abbreviation of the nmotny
 * dd-yyyy - day in month and full year numerically
 * HH:mm - Hours and minutes in 24hr format
 */
object DateUtils {
    @JvmStatic
    fun toSimpleString(date: Date) : String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(date)
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(date)
    }

}

class SingletonExt {
    companion object {
        var itemExt: Extinguisher = Extinguisher()
    }
}

class SingletonFlask {
    companion object {
        var itemFlask: Flask = Flask()
    }
}