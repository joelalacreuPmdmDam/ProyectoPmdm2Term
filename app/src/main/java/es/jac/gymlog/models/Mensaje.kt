package es.jac.gymlog.models
import java.util.Date

data class Mensaje(
    var id: String? = null,
    var idRemitente: String = "",
    var mensaje: String = "",
    var idDestinatario: String = "",
    var createdData: Date? = null

)
