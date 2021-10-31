package mx.madg.easyfit.Models

import mx.madg.easyfit.R
import java.io.Serializable

data class DietaDay (
    var day: String="",
    var isCompleted: Boolean
) : Serializable

object MyDiet {

    private val days = arrayListOf(
        "LUNES",
        "MARTES",
        "MIÉRCOLES",
        "JUEVES",
        "VIERNES",
        "SÁBADO",
        "DOMINGO"
    )

    var dayList: ArrayList<DietaDay>? = null
        get() {
            if (field != null)
                return field

            field = ArrayList()
            for (i in days.indices) {
                val dayName = days[i]
                val day = DietaDay(dayName, false)
                field!!.add(day)
            }

            return field
        }

    var daysCompleted: MutableList<DietaDay> = mutableListOf()

}