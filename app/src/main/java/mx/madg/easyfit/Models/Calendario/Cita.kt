package mx.madg.easyfit.Models.Calendario

import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.Models.Nutriologo.Nutriologo

data class Cita (
    var hora:String = "",
    var cliente: Cliente,
    var especialista: Nutriologo
)
