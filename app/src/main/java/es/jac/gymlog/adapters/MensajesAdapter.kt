package es.jac.gymlog.adapters

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import es.jac.gymlog.R
import es.jac.gymlog.managers.AuthManager
import es.jac.gymlog.models.Mensaje
import es.jac.gymlog.models.Usuario


class MensajesAdapter (private val context: Context,
                       private val items: MutableList<Mensaje>,
                       private val nombresChat: MutableList<Usuario>) :
    RecyclerView.Adapter<MensajesAdapter.ViewHolder>() {

    private lateinit var vista: View


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        vista = LayoutInflater.from(context).inflate(R.layout.mensaje_item, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindItem(item,vista,nombresChat)
    }



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvNombreUsuario: TextView = view.findViewById(R.id.tvRemitente)
        private val tvContenido: TextView = view.findViewById(R.id.tvContent)
        private val cardView: CardView = view.findViewById(R.id.cardView_mensaje)


        fun bindItem(item: Mensaje,view: View,nombresChat: MutableList<Usuario>) {


            val remitente = nombresChat.find { it.idUsuario == item.idRemitente }
            if (remitente != null) {
                if (!remitente.nombreChat.isNullOrBlank()){
                    tvNombreUsuario.text = remitente.nombreChat
                }else{
                    tvNombreUsuario.text = item.idRemitente
                }
            } else {
                tvNombreUsuario.text = item.idRemitente
            }

            //tvNombreUsuario.text = item.idRemitente

            tvContenido.text = item.mensaje
            if (item.idRemitente == AuthManager().getCurrentUser()?.email.toString()){
                tvContenido.gravity = Gravity.END
                tvNombreUsuario.gravity = Gravity.END
                val prefs = view.context.getSharedPreferences("es.jac.gymlog_preferences",
                    AppCompatActivity.MODE_PRIVATE
                )
                var colorFondoPreferencias : Int = prefs.getInt("color_preference", 0)
                cardView.setCardBackgroundColor(colorFondoPreferencias)

                val layoutParams = cardView.layoutParams as RelativeLayout.LayoutParams
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, 0)
                cardView.layoutParams = layoutParams
            }else{
                //Poner el color por defecto
                val colorPorDefecto: Int = view.context.getColor(R.color.azul)
                cardView.setCardBackgroundColor(colorPorDefecto)
                tvContenido.gravity = Gravity.START
                tvNombreUsuario.gravity = Gravity.START
                //Poner el menssaje en su sitio por defecto
                val layoutParams = cardView.layoutParams as RelativeLayout.LayoutParams
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START)
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, 0)
                cardView.layoutParams = layoutParams

            }
        }
    }

}