package mx.madg.easyfit.ui.Calendario

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.madg.easyfit.Models.Calendario.Cita
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.Models.Nutriologo.Nutriologo
import mx.madg.easyfit.R
import java.util.*

class CalendarioFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarioFragment()
    }

    private lateinit var viewModel: CalendarioViewModel
    private var camino: String = ""
    private var cliente: Cliente = Cliente()
    private var nutriologo: Nutriologo = Nutriologo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.calendario_fragment, container, false)
        showDatePicker(view)
        agendar(view)
        return view
    }

    private fun agendar(view: View?) {
        view?.findViewById<Button>(R.id.btnAgendar)?.setOnClickListener {
            agendarCita()
        }
    }

    private fun agendarCita() {
        val baseDatos = Firebase.database
        val usuario = FirebaseAuth.getInstance().currentUser

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)


        if(view?.findViewById<TextView>(R.id.tvCitaAgendada)?.text != "Ninguna cita agendada"){
            var referenciaCliente = baseDatos.getReference("Clientes/${usuario?.uid}").get().addOnSuccessListener { dataCliente ->
                this.cliente = dataCliente.getValue(Cliente::class.java)!!
                var referenciaNutriologo = baseDatos.getReference("Nutriologos/${this.cliente.nutriologo.toString()}").get().addOnSuccessListener { dataNutriologo ->
                    this.nutriologo = dataNutriologo.getValue(Nutriologo::class.java)!!
                    val cita = Cita("",this.cliente, this.nutriologo)
                    val referencia = baseDatos.getReference("Citas/${camino}/${hour}:${minute}")
                    Toast.makeText(context, "Se ha agendado la cita exitosamente", Toast.LENGTH_SHORT).show()
                    referencia.setValue(cita)
                }
            }
        } else{
            Toast.makeText(context,"Por favor ingrese una fecha",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePicker(view: View?) {
        val datePicker = view?.findViewById<EditText>(R.id.etDate)
        datePicker?.setOnClickListener{ showDatePickerDialog() }

    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val Month = month + 1
        camino = "${year}/${Month}/${day}"
        view?.findViewById<TextView>(R.id.tvCitaAgendada)?.setText("Date: $day/$Month/$year")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarioViewModel::class.java)
        // TODO: Use the ViewModel
    }



}