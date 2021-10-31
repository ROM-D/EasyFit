package mx.madg.easyfit.ui.Dieta.OpcionesComida

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.madg.easyfit.Models.*
import mx.madg.easyfit.Models.Adaptadores.OptionAdapter
import mx.madg.easyfit.Models.Listeners.DietDayListener
import mx.madg.easyfit.R
import mx.madg.easyfit.databinding.OpcionesComidaFragmentBinding

class OpcionesComidaFragment : Fragment(), DietDayListener {

    companion object {
        fun newInstance() = OpcionesComidaFragment()
    }

    private lateinit var viewModel: OpcionesComidaViewModel
    private  lateinit var dietOptionsAdapter: OptionAdapter
    private lateinit var binding: OpcionesComidaFragmentBinding
    // TODO: Referenciar datos obtenidos del fragmento anterior
    private val args : OpcionesComidaFragmentArgs by navArgs<OpcionesComidaFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.opciones_comida_fragment, container, false)
        setupRecyclerView(view)
        setupExternalData(view)
        return view
    }

    private fun setupRecyclerView(view: View?) {
        val context = requireContext()
        dietOptionsAdapter = OptionAdapter(context, MyOption.optionList!!)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.dietoption_recycler_view)
        recyclerView?.adapter = dietOptionsAdapter
        recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView?.layoutManager = layoutManager

        dietOptionsAdapter.listener = this
    }

    private fun setupExternalData(view: View?) {
        val tvDiaDeLaSemana = view?.findViewById<TextView>(R.id.tvDiaDeLaSemana)
        val tvComidaDelDia = view?.findViewById<TextView>(R.id.tvMeal)
        tvDiaDeLaSemana?.text = args.opcionDelDia.day
        tvComidaDelDia?.text = args.opcionDelDia.meal
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OpcionesComidaViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun clickDay(position: Int) {
        TODO("Not used but required as it is an interface")
    }

    override fun clickMeal(position: Int) {
        TODO("Not used but required as it is an interface")
    }

    override fun clickOption(position: Int) {
        Log.i("Position", "Position meal: $position")
        val opcion = dietOptionsAdapter.options[position]
        opcion.day = args.opcionDelDia.day
        val accion = OpcionesComidaFragmentDirections.actionOpcionesComidaFragmentToOpcionFragment(opcion)
        findNavController().navigate(accion)
    }

}