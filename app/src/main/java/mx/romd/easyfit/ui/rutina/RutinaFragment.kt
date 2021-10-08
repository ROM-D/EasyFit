package mx.romd.easyfit.ui.rutina

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.romd.easyfit.R

class RutinaFragment : Fragment() {

    companion object {
        fun newInstance() = RutinaFragment()
    }

    private lateinit var viewModel: RutinaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rutina_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RutinaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}