package mx.madg.easyfit.Models.Nutriologo

import mx.madg.easyfit.Models.Contacto.Contacto

data class Nutriologo (
    var id:String? = "",
    var nombre:String? = "",
    var mail:String? = "",
    var celular:Long?=0,
    var tipo:Int=1,
    var clientes:ArrayList<Contacto>? = arrayListOf()

    //var dieta:String=""
)
