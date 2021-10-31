package mx.madg.easyfit.ui.Contactos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.madg.easyfit.R

class ContactosFragment : Fragment() {

    companion object {
        fun newInstance() = ContactosFragment()
    }

    private lateinit var viewModel: ContactosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contactos_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactosViewModel::class.java)
        // TODO: Use the ViewModel
    }

}