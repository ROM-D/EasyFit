package mx.madg.easyfit.ui.Nutriologo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import mx.madg.easyfit.Models.Cliente.Cliente
import mx.madg.easyfit.Models.Dieta.Opcion
import mx.madg.easyfit.R


class DietaVistaNutriologoFragment : Fragment() {

    companion object {
        fun newInstance() = DietaVistaNutriologoFragment()
    }

    private lateinit var viewModel: DietaVistaNutriologoViewModel
    private lateinit var baseDatos: FirebaseDatabase
    private lateinit var referencia: DatabaseReference
    // TODO: Referenciar datos obtenidos del fragmento anterior
    private val args : DietaVistaNutriologoFragmentArgs by navArgs<DietaVistaNutriologoFragmentArgs>()

    private var diaSelected: Int = 0
    private var comidaSelected: Int = 0
    private var opcionSelected: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dieta_vista_nutriologo_fragment, container, false)
        baseDatos = FirebaseDatabase.getInstance()
        desplegarDieta(view)
        configurarEventos(view)

        return view
    }

    private fun configurarEventos(view: View?) {
        val asignarDietaCliente = view?.findViewById<Button>(R.id.btnAsignarDietaCliente)
        val alimentos = view?.findViewById<EditText>(R.id.etAlimentos)
        val bebidas = view?.findViewById<EditText>(R.id.etBebidas)

        asignarDietaCliente?.setOnClickListener {
            val opcion = Opcion(
                alimentos?.text.toString(),
                bebidas?.text.toString()
            )
            referencia.setValue(opcion)
            println("REF: ${referencia}")
            Toast.makeText(requireContext(), "DIETA ACTUALIZADA CON ÉXITO", Toast.LENGTH_SHORT).show()
        }
    }

    private fun desplegarDieta(view: View?) {
        referencia = baseDatos.getReference("Clientes/${args.vistaDieta.id}/dieta/${diaSelected}/comidas/${comidaSelected}/opciones/${opcionSelected}/")
        val alimentos = view?.findViewById<EditText>(R.id.etAlimentos)
        val bebidas = view?.findViewById<EditText>(R.id.etBebidas)
        // TODO: Leer dieta / Formato?
        referencia.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var opcion = snapshot.getValue(Opcion::class.java)
                alimentos?.setText(opcion?.alimentos.toString())
                bebidas?.setText(opcion?.bebidas.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error :'(")
            }
        })

        // TODO: Forms / Dropdowns
        val dias : MutableList<String> = arrayListOf(
            "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"
        )
        val meals : MutableList<String> = arrayListOf(
            "DESAYUNO", "COLACIÓN 1", "COMIDA", "COLACIÓN 2", "CENA"
        )
        val opciones : MutableList<String> = arrayListOf(
            "OPCIÓN 1", "OPCIÓN 2", "OPCIÓN 3"
        )

        val adapterDias: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, dias)
        val adapterMeals: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, meals)
        val adapterOpciones: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, opciones)

        val spinnerDia = view?.findViewById<Spinner>(R.id.spinnerDia)
        val spinnerComida = view?.findViewById<Spinner>(R.id.spinnerComida)
        val spinnerOpcion = view?.findViewById<Spinner>(R.id.spinnerOpcion)

        spinnerDia?.adapter = adapterDias
        spinnerComida?.adapter = adapterMeals
        spinnerOpcion?.adapter = adapterOpciones

        // TODO: Asignar dieta
        spinnerDia?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                diaSelected = position
                referencia = baseDatos.getReference("Clientes/${args.vistaDieta.id}/dieta/${diaSelected}/comidas/${comidaSelected}/opciones/${opcionSelected}")
                // TODO: Leer dieta / Formato?
                referencia.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var opcion = snapshot.getValue(Opcion::class.java)
                        alimentos?.setText(opcion?.alimentos.toString())
                        bebidas?.setText(opcion?.bebidas.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("Error :'(")
                    }
                })
                println("SE HA SELECCIONADO: $referencia")

                Toast.makeText(requireContext(), "DIA: $diaSelected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //referencia = baseDatos.getReference("Clientes/${args.vistaDieta.id}/dieta/${diaSelected}/comidas/${comidaSelected}/opciones/${opcionSelected}")
            }
        }

        spinnerComida?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                comidaSelected = position
                referencia = baseDatos.getReference("Clientes/${args.vistaDieta.id}/dieta/${diaSelected}/comidas/${comidaSelected}/opciones/${opcionSelected}")
                // TODO: Leer dieta / Formato?
                referencia.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var opcion = snapshot.getValue(Opcion::class.java)
                        alimentos?.setText(opcion?.alimentos.toString())
                        bebidas?.setText(opcion?.bebidas.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("Error :'(")
                    }
                })
                println("SE HA SELECCIONADO: $referencia")
                Toast.makeText(requireContext(), "COMDIA: $comidaSelected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // referencia = baseDatos.getReference("Clientes/${args.vistaDieta.id}/dieta/${diaSelected}/comidas/${comidaSelected}/opciones/${opcionSelected}")
            }
        }

        spinnerOpcion?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                opcionSelected = position
                referencia = baseDatos.getReference("Clientes/${args.vistaDieta.id}/dieta/${diaSelected}/comidas/${comidaSelected}/opciones/${opcionSelected}")
                // TODO: Leer dieta / Formato?
                referencia.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var opcion = snapshot.getValue(Opcion::class.java)
                        alimentos?.setText(opcion?.alimentos.toString())
                        bebidas?.setText(opcion?.bebidas.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("Error :'(")
                    }
                })
                println("SE HA SELECCIONADO: $referencia")
                Toast.makeText(requireContext(), "OPCION: $opcionSelected", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // referencia = baseDatos.getReference("Clientes/${args.vistaDieta.id}/dieta/${diaSelected}/comidas/${comidaSelected}/opciones/${opcionSelected}")
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DietaVistaNutriologoViewModel::class.java)
        // TODO: Use the ViewModel
    }



}