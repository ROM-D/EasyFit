package mx.madg.easyfit.Models.Dieta

data class Dia(
    var dia: String = "",
    var comidas: ArrayList<Comida>? = arrayListOf()
)
