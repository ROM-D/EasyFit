package mx.madg.easyfit.Models

import java.io.Serializable

data class RutinaDay (
    val day: String = "",
    val isCompleted: Boolean = false
) : Serializable

object MyRutine {

    private val days = arrayListOf(
        "LUNES",
        "MARTES",
        "MIÉRCOLES",
        "JUEVES",
        "VIERNES",
        "SÁBADO",
        "DOMINGO"
    )

    var dayList: ArrayList<RutinaDay>? = null
        get() {
            if (field != null)
                return field

            field = ArrayList()
            for (i in days.indices) {
                val dayName = days[i]
                val day = RutinaDay(dayName, false)
                field!!.add(day)
            }

            return field
        }

    var daysCompleted: MutableList<RutinaDay> = mutableListOf()

}

