package mx.madg.easyfit.ui.Mensajeria

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.madg.easyfit.R

class mensajeriaFragment : Fragment() {

    companion object {
        fun newInstance() = mensajeriaFragment()
    }

    private lateinit var viewModel: MensajeriaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mensajeria_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MensajeriaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}