package mx.madg.easyfit

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
import mx.madg.easyfit.Models.DayAdapter
import mx.madg.easyfit.Models.DietDayListener
import mx.madg.easyfit.Models.MyDiet

class DietasFragment : Fragment(), DietDayListener {

    companion object {
        fun newInstance() = DietasFragment()
    }

    private lateinit var viewModel: NavDietasViewModel
    private lateinit var biding: DietasFragment
    private lateinit var  dietDaysAdapter : DayAdapter
    //private var dietDaysAdapter = DayAdapter(requireContext(), MyDiet.dayList!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dietas_fragment, container, false)
        setupRecyclerView(view)
        return view
    }

    private fun setupRecyclerView(view: View?) {
        val context = requireContext()
        dietDaysAdapter = DayAdapter(context, MyDiet.dayList!!)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.dietday_recycler_view)
        recyclerView?.adapter = dietDaysAdapter
        recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView?.layoutManager = layoutManager

        dietDaysAdapter.listener = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NavDietasViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun clickDay(position: Int) {
        Log.i("Position", "Position: $position")
        val dayMeal = dietDaysAdapter.days[position]
        val accion = DietasFragmentDirections.actionNavDietasToNavComidaDia(dayMeal)
        findNavController().navigate(accion)
    }


}