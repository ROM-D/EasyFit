package mx.madg.easyfit.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mx.madg.easyfit.MainActivity
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.Models.Dieta.Comida
import mx.madg.easyfit.Models.Dieta.Dia
import mx.madg.easyfit.Models.Dieta.Opcion
import mx.madg.easyfit.Models.Entrenador.Entrenador
import mx.madg.easyfit.Models.Nutriologo.Nutriologo
import mx.madg.easyfit.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {  // TODO: Check

    private val CODIGO_SIGNIN: Int = 500
    private var tipoAcceso: Int = 3
    private lateinit var binding: ActivityLoginBinding
    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var referencia: DatabaseReference
    private lateinit var tabla: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        baseDatos = FirebaseDatabase.getInstance()
        configurarEventos()
    }

    override fun onStart() {
        super.onStart()
        val usuario = FirebaseAuth.getInstance().currentUser
        Log.i("EXISTING", "EXISTING: ${tipoAcceso}")
        if(usuario != null) {
            val view = Intent(this, MainActivity::class.java) // TODO: NAVIGATE
            startActivity(view)
        }
    }

    private fun configurarEventos() {
        val switchEntrenador = binding.switchEntrenador
        val switchNutriologo = binding.switchNutriologo
        val switchCliente = binding.switchCliente

        switchNutriologo.setOnCheckedChangeListener{ compoundButton, b ->
            if(switchCliente.isChecked && switchNutriologo.isChecked){
                switchCliente.setChecked(false)
                checarUsuario()
            }
            if(switchEntrenador.isChecked && switchNutriologo.isChecked){
                switchEntrenador.setChecked(false)
                checarUsuario()
            }
        }

        switchEntrenador.setOnCheckedChangeListener{ compoundButton, b ->
            if(switchCliente.isChecked && switchEntrenador.isChecked){
                switchCliente.setChecked(false)
                checarUsuario()
            }
            if(switchEntrenador.isChecked && switchNutriologo.isChecked){
                switchNutriologo.setChecked(false)
                checarUsuario()
            }
        }

        switchCliente.setOnCheckedChangeListener { compoundButton, b ->
            if(switchCliente.isChecked && switchEntrenador.isChecked){
                switchEntrenador.setChecked(false)
                checarUsuario()
            }
            if(switchCliente.isChecked && switchNutriologo.isChecked){
                switchNutriologo.setChecked(false)
                checarUsuario()
            }
        }

        binding.btnGoogleSignIn.setOnClickListener {
            checarUsuario()
            autentificar()
        }
    }

    private fun checarUsuario() {
        val switchEntrenador = binding.switchEntrenador
        val switchNutriologo = binding.switchNutriologo
        val switchCliente = binding.switchCliente

        if(switchEntrenador.isChecked){
            tipoAcceso = 2
        }else if(switchNutriologo.isChecked){
            tipoAcceso = 1
        }else if(switchCliente.isChecked){
            tipoAcceso = 0
        }
    }

    private fun autentificar() {
        // val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        if(tipoAcceso==3){
            Toast.makeText(this, "Seleccione un campo", Toast.LENGTH_SHORT).show()
        }else{
            val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
            //Quiero empezar una actividad y espero un resultado
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                CODIGO_SIGNIN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODIGO_SIGNIN) {
            when (resultCode) {
                RESULT_OK -> {
                    //La instancia es un objeto que administra la autentificacion
                    val usuario = FirebaseAuth.getInstance().currentUser
                    Log.i("EXISTING", "EXISTING: ${tipoAcceso}")

                    if (tipoAcceso == 0) {
                        tabla = "Clientes"
                    } else if (tipoAcceso == 1) {
                        tabla = "Nutriologos"
                    } else if (tipoAcceso == 2) {
                        tabla = "Entrenadores"
                    }

                    referencia = baseDatos.getReference("${tabla}/${usuario?.uid}/")

                    val existing = baseDatos.getReference()
                        .child(tabla).child("${usuario?.uid}")

                    Log.i("EXISTING", "EXISTING: ${existing}")
                    if(tipoAcceso == 0){
                        if (existing == null) {
                            // ARRAYS
                            val dieta = asignarDatosDefaultDieta()
                            val cliente = Cliente(usuario!!.uid, usuario?.displayName , usuario?.email, dieta)

                            referencia.setValue(cliente)
                            // Lanzar otra actividad
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("tipo",tipoAcceso)
                            startActivity(intent)
                        }
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("tipo",tipoAcceso)
                        startActivity(intent)
                    }
                    if(tipoAcceso == 1){
                        if(existing ==null){
                            val nutriologo = Nutriologo(usuario!!.uid,usuario?.displayName,usuario?.email)
                            referencia.setValue(nutriologo)
                            // Lanzar otra actividad
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("tipo",tipoAcceso)
                            startActivity(intent)
                        }
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("tipo",tipoAcceso)
                        startActivity(intent)

                    }
                    if(tipoAcceso == 2){
                        if(existing ==null){
                            val entrenador = Entrenador(usuario!!.uid,usuario?.displayName,usuario?.email)
                            referencia.setValue(entrenador)
                            // Lanzar otra actividad
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("tipo",tipoAcceso)
                            startActivity(intent)
                        }
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("tipo",tipoAcceso)
                        startActivity(intent)
                    }


                }

                RESULT_CANCELED -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //val response = IdpResponse.fromResultIntent(data)
                    Toast.makeText(this, "No se ha podido iniciar sesión", Toast.LENGTH_SHORT).show()
                } }

        }

    }

    private fun asignarDatosDefaultDieta(): ArrayList<Dia> {
        val opciones = asignarDatosOpciones()
        val comidas = asignarDatosComidas(opciones)
        val dias = asignarDatosDias(comidas)
        return dias
    }

    private fun asignarDatosDias(comidas: ArrayList<Comida>): ArrayList<Dia> {
        var dia: Dia?
        val dias = arrayListOf<Dia>()
        val diasSemana = arrayListOf(
            "LUNES",
            "MARTES",
            "MIÉRCOLES",
            "JUEVES",
            "VIERNES",
            "SÁBADO",
            "DOMINGO"
        )
        for(i in diasSemana.indices) {
            dia = Dia(diasSemana[i], comidas)
            dias.add(dia)
        }
        return dias
    }

    private fun asignarDatosComidas(opciones: ArrayList<Opcion>): ArrayList<Comida> {
        var comida: Comida?
        val comidas = arrayListOf<Comida>()
        val tiposComida = arrayListOf(
            "Desayuno",
            "Colación 1",
            "Comida",
            "Colación 2",
            "Cena"
        )
        for(i in tiposComida.indices) {
            comida = Comida("00:00", tiposComida[i], opciones)
            comidas.add(comida)
        }
        return comidas
    }

    private fun asignarDatosOpciones() : ArrayList<Opcion> {
        var opcion: Opcion?
        val opciones = arrayListOf<Opcion>()
        for(i in 0..2) {
            opcion = Opcion("", "")
            opciones.add(opcion)
        }
        return opciones
    }
}