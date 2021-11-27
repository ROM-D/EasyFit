package mx.madg.easyfit.Models.Contacto

import java.io.Serializable

data class Contacto (
    val id: String = "",
    val nombre: String = "",
    val telefono: String = "",
    val correo: String = "",
) : Serializable
