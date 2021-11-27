package mx.madg.easyfit.Models

import java.io.Serializable

data class DietaMeal (
    var meal: String = "",
    var isCompleted: Boolean,
    var selected: Int,
    var daySelected: Int = 0,
    var day: String = "",
) : Serializable

object MyMeal {

    private val meals = arrayListOf(
        "Desayuno",
        "Colación 1",
        "Comida",
        "Colación 2",
        "Cena"
    )

    var mealList: ArrayList<DietaMeal>? = null
        get() {
            if (field != null)
                return field

            field = ArrayList()
            for (i in meals.indices) {
                val mealName = meals[i]
                val meal = DietaMeal(mealName, false, i)
                field!!.add(meal)
            }

            return field
        }

    var mealsCompleted: MutableList<DietaMeal> = mutableListOf()

}