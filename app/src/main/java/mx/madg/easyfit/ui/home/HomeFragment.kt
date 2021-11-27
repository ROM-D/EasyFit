package mx.madg.easyfit.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.R
import mx.madg.easyfit.databinding.FragmentHomeBinding
import mx.madg.easyfit.ui.Contactos.ContactosFragmentDirections


import mx.madg.easyfit.ui.home.HomeViewModel
import mx.madg.easyfit.ui.login.LoginActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textView10
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })

        configurarEventos()
        return root
    }

    private fun configurarEventos() {
        binding.btnGoToDietas.setOnClickListener {
            val accion = HomeFragmentDirections.actionNavHomeToNavDietas()
            findNavController().navigate(accion)
        }

        binding.btnGoToRutinas.setOnClickListener {
            val accion = HomeFragmentDirections.actionNavHomeToNavRutinas()
            findNavController().navigate(accion)
        }

        binding.btnAcercaDe.setOnClickListener {
            val accion = HomeFragmentDirections.actionNavHomeToAcercaDeFragment()
            findNavController().navigate(accion)
        }
    }

    override fun onStart() {
        super.onStart()
    }

}