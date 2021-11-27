package mx.madg.easyfit.Models.Cliente
import mx.madg.easyfit.Models.Dieta.Dia
import java.io.Serializable

data class Cliente (
    var id:String = "",
    var nombre:String? = "",
    var mail:String? = "",
    var dieta:ArrayList<Dia>? = arrayListOf(),
    var peso:Double = 0.0,
    var altura:Double = 0.0,
    var sexo:String="",
    var porcentajeDeMusculo:Double=0.0,
    var porcentajeGrasaCorporal:Double=0.0,
    var numeroDePliegues:Int = 0,
    var nutriologo:String?="",
    var entrenador:String?="",
    var tipo: Int = 0,
) : Serializable