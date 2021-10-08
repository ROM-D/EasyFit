package mx.romd.easyfit.ui.contactos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import mx.romd.easyfit.databinding.FragmentContactosBinding

class ContactosFragment : Fragment() {

    private lateinit var contactosViewModel: ContactosViewModel
    private var _binding: FragmentContactosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactosViewModel =
            ViewModelProvider(this).get(ContactosViewModel::class.java)

        _binding = FragmentContactosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textContactos
        contactosViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}