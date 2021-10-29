package mx.madg.easyfit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.madg.easyfit.Models.*
import mx.madg.easyfit.databinding.ComidaDiaFragmentBinding

class ComidaDiaFragment : Fragment(), DietDayListener {

    companion object {
        fun newInstance() = ComidaDiaFragment()
    }

    private lateinit var viewModel: ComidaDiaViewModel
    private lateinit var binding: ComidaDiaFragmentBinding
    private lateinit var dietMealsAdapter: MealAdapter
    // TODO: Referenciar datos obtenidos del fragmento anterior
    private val args : ComidaDiaFragmentArgs by navArgs<ComidaDiaFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.comida_dia_fragment, container, false)
        setupRecyclerView(view)
        setupExternalData(view)
        return view
    }

    private fun setupExternalData(view: View?) {
        val tvDia = view?.findViewById<TextView>(R.id.tvDia)
        tvDia?.text = args.comidaDia.day
    }

    private fun setupRecyclerView(view: View?) {
        val context = requireContext()
        dietMealsAdapter = MealAdapter(context, MyMeal.mealList!!)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.dietmeal_recycler_view)
        recyclerView?.adapter = dietMealsAdapter
        recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView?.layoutManager = layoutManager

        dietMealsAdapter.listener = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ComidaDiaViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun clickDay(position: Int) {
        TODO("Not used but required as it is an interface")
    }

    override fun clickMeal(position: Int) {
        Log.i("Position", "Position meal: $position")
        val meal = dietMealsAdapter.meals[position]
        meal.day = args.comidaDia.day
        val accion = ComidaDiaFragmentDirections.actionNavComidaDiaToOpcionesComidaFragment(meal)
        findNavController().navigate(accion)
    }



}