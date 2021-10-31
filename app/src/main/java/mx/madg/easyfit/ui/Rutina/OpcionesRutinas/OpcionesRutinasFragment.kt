package mx.madg.easyfit.ui.Rutina.OpcionesRutinas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.madg.easyfit.R

class OpcionesRutinasFragment : Fragment() {

    companion object {
        fun newInstance() = OpcionesRutinasFragment()
    }

    private lateinit var viewModel: OpcionesRutinasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.opciones_rutinas_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OpcionesRutinasViewModel::class.java)
        // TODO: Use the ViewModel
    }

}