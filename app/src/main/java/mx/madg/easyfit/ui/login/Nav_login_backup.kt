/*package mx.madg.easyfit.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import mx.madg.easyfit.R

class nav_login : Fragment() {

    companion object {
        fun newInstance() = nav_login()
    }

    private lateinit var baseDatos: FirebaseDatabase


    private lateinit var viewModel: NavLoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.nav_login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NavLoginViewModel::class.java)
        // TODO: Use the ViewModel
        baseDatos = FirebaseDatabase.getInstance()
        //escribirDatos()
    }

    private fun escribirDatos() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Administradores/A01748632")

        //myRef.setValue("Oscar Macias Rodriguez")
        //myRef.setValue("Rodrigo Cravioto Caballero")
        //myRef.setValue("Diego Raúl Elizalde Uriarte")
        //myRef.setValue("Michel Antoine Dionne Gutiérrez")
    }

}*/