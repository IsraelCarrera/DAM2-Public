package com.example.pelisyseriesapp.ui.buscarPelis

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.pelisyseriesapp.R
import com.example.pelisyseriesapp.databinding.FragmentBuscarPelisBinding
import com.example.pelisyseriesapp.domain.GenericoSeriesPelis
import com.example.pelisyseriesapp.ui.GenericoAdapter
import com.example.pelisyseriesapp.ui.MainActivity
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BuscarPelisFragment : Fragment() {
    private var _binding: FragmentBuscarPelisBinding? = null
    private val binding get() = _binding!!

    //Adaptador
    private lateinit var genericoAdapter: GenericoAdapter

    //ViewModel
    private val viewModel: BuscarPelisViewModel by viewModels()

    //Para el multiselect
    private val callback by lazy {
        multiseleccion()
    }

    private lateinit var actionMode: ActionMode

    //Para que el menu sea distinto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //Para inflar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBuscarPelisBinding.inflate(inflater, container, false)
        return binding.root
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_busquedas, menu)

        val buscar = menu.findItem(R.id.searchBuscar).actionView as SearchView
        buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.handleEvent(BuscarPelisContract.BuscarPelisEvent.BuscarPeliculas(it))
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    //Create
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //El inicializar el adaptador.
        adapter()
        //Tema ViewModels
        viewModel()
        viewModel.handleEvent(BuscarPelisContract.BuscarPelisEvent.BuscarPeliculas(Constantes.VACIO))
    }

    //Tema multiseleccion
    private fun multiseleccion() =
        object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                //Aqui se infla el menu multiSelect
                requireActivity().menuInflater.inflate(R.menu.menu_add_favoritos, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                //Aquí irá el código para añadir a favoritos
                return when (item?.itemId) {
                    R.id.Menufavorito -> {
                        viewModel.mandarSeleccionadosFavoritos()
                            .map {
                                viewModel.handleEvent(BuscarPelisContract.BuscarPelisEvent.AddPeliFavorita(
                                    it.id))
                            }
                        Toast.makeText(context, Constantes.ADD_EXITO, Toast.LENGTH_SHORT).show()
                        //Una vez finalizado, salimos del multiselect
                        mode?.finish()
                        true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                //Cuando se le da a la X en el menu de multiselect
                viewModel.salirMultiSelect()
                genericoAdapter.salirDelSelectedMode()

            }
        }

    private fun adapter() {
        genericoAdapter = GenericoAdapter(this.requireContext(),
            object : GenericoAdapter.GenericAction {
                override fun empezandoSelectMode() {
                    //Para empezar el selectMode
                    (requireActivity() as MainActivity).startSupportActionMode(callback)?.let {
                        actionMode = it
                        it.title = Constantes.CERO_SELECT
                    }
                }

                override fun itemHasClicked(genericoSeriesPelis: GenericoSeriesPelis) {
                    viewModel.addItemMultiselect(genericoSeriesPelis)
                    actionMode.title =
                        viewModel.mandarSeleccionadosFavoritos().size.toString() + Constantes.SELECT
                }

                override fun isItemSelected(genericoSeriesPelis: GenericoSeriesPelis): Boolean {
                    return viewModel.estaSeleccionado(genericoSeriesPelis)
                }

                override fun verMas(id: Int) {
                    val irsePantalla =
                        BuscarPelisFragmentDirections.actionBuscarPelisFragmentToBuscarPelisDetallada(
                            id
                        )
                    findNavController().navigate(irsePantalla)
                }
            })
        binding.rvBuscarPelis.adapter = genericoAdapter

    }

    private fun viewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    if (value.isLoading) {
                        binding.pbCargarBuscarPelis.visibility = View.VISIBLE
                    } else {
                        binding.pbCargarBuscarPelis.visibility = View.GONE
                    }
                    genericoAdapter.submitList(value.pelis)
                    value.error?.let {
                        Toast.makeText(this@BuscarPelisFragment.requireContext(),
                            it,
                            Toast.LENGTH_SHORT).show()
                        viewModel.handleEvent(BuscarPelisContract.BuscarPelisEvent.ErrorMostrado)
                    }
                }
            }
        }
    }
}