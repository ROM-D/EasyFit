package mx.madg.easyfit.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import mx.madg.easyfit.R
import mx.madg.easyfit.databinding.FragmentHomeBinding
import mx.madg.easyfit.loginActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val textView: TextView = binding.textView10
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
           textView.text = it
       })
        configurarEventos()

        return root
    }

    private fun configurarEventos() {
        binding.btnSignout.setOnClickListener {
            AuthUI.getInstance().signOut(requireContext())
            startActivity(Intent(activity,loginActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}