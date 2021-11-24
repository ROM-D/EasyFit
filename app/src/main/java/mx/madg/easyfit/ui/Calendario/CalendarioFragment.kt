package mx.madg.easyfit.ui.Calendario

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import mx.madg.easyfit.R

class CalendarioFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarioFragment()
    }

    private lateinit var viewModel: CalendarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.calendario_fragment, container, false)
        showDatePicker(view)
        return view
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
        view?.findViewById<TextView>(R.id.tvCitaAgendada)?.setText("Date: $day/$Month/$year")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarioViewModel::class.java)
        // TODO: Use the ViewModel
    }



}