package mx.madg.easyfit.Models.Entrenador

data class Entrenador(
    var id:String? = "",
    var nombre:String? = "",
    var mail:String? = "",
    var celular:Long?=0,
    var tipo:Int=2,
    var clientes:ArrayList<String>? = arrayListOf()

    //var dieta:String=""
)
