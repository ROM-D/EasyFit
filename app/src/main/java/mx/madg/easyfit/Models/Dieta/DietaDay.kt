package mx.madg.easyfit.Models

import mx.madg.easyfit.Models.Dieta.Comida
import mx.madg.easyfit.R
import java.io.Serializable

data class DietaDay (
    var day: String="",
    var isCompleted: Boolean,
    var selected: Int,
    var comidas: ArrayList<Int>? = arrayListOf( 0, 1, 2, 3, 4 )
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
                val day = DietaDay(dayName, false, i)
                field!!.add(day)
            }

            return field
        }

    var daysCompleted: MutableList<DietaDay> = mutableListOf()

}