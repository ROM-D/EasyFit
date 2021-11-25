package mx.madg.easyfit.Models.Dieta

data class Comida(
    var horario: String? = "",
    var opciones: ArrayList<Opcion>? = arrayListOf()
)
