package mx.romd.easyfit.ui.dieta

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.romd.easyfit.R

class DietaFragment : Fragment() {

    companion object {
        fun newInstance() = DietaFragment()
    }

    private lateinit var viewModel: DietaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dieta_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DietaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}