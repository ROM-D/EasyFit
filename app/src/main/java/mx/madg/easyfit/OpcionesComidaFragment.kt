package mx.madg.easyfit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import mx.madg.easyfit.databinding.OpcionesComidaFragmentBinding

class OpcionesComidaFragment : Fragment() {

    companion object {
        fun newInstance() = OpcionesComidaFragment()
    }

    private lateinit var viewModel: OpcionesComidaViewModel
    // TODO: Create and set an Adatpter for options
    private lateinit var binding: OpcionesComidaFragmentBinding
    // TODO: Referenciar datos obtenidos del fragmento anterior
    private val args : OpcionesComidaFragmentArgs by navArgs<OpcionesComidaFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.opciones_comida_fragment, container, false)
        setupExternalData(view)
        return view
    }

    private fun setupExternalData(view: View?) {
        val tvDiaDeLaSemana = view?.findViewById<TextView>(R.id.tvDiaDeLaSemana)

        tvDiaDeLaSemana?.text = args.opcionDelDia.day

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OpcionesComidaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}