package mx.madg.easyfit.ui.Rutina.Rutinas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.madg.easyfit.Models.*
import mx.madg.easyfit.Models.Adaptadores.RutinaDayAdapter
import mx.madg.easyfit.Models.Listeners.RutinaDayListener
import mx.madg.easyfit.R
import mx.madg.easyfit.databinding.NavHeaderMainBinding


class NavRutinas : Fragment(), RutinaDayListener {

    companion object {
        fun newInstance() = NavRutinas()
    }

    private lateinit var viewModel: NavRutinasViewModel
    private lateinit var binding: NavHeaderMainBinding
    private lateinit var rutinaDaysAdapter : RutinaDayAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.nav_rutinas_fragment, container, false)
        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View?) {
        val context = requireContext()
        rutinaDaysAdapter = RutinaDayAdapter(context, MyRutine.dayList!!)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rutinaday_recycler_view)
        recyclerView?.adapter = rutinaDaysAdapter
        recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView?.layoutManager = layoutManager

        rutinaDaysAdapter.listener = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NavRutinasViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun clickDay(position: Int) {
        Log.i("Position", "Position: $position")
        val rutinaDay = rutinaDaysAdapter.days[position]
        val accion = NavRutinasDirections.actionNavRutinasToOpcionesRutinasFragment(rutinaDay)
        findNavController().navigate(accion)
    }

}