package com.example.pelisyseriesapp.ui.buscarSeries

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pelisyseriesapp.R
import com.example.pelisyseriesapp.databinding.FragmentBuscarSeriesBinding
import com.example.pelisyseriesapp.domain.GenericoSeriesPelis
import com.example.pelisyseriesapp.ui.GenericoAdapter
import com.example.pelisyseriesapp.ui.MainActivity
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuscarSeriesFragment : Fragment() {
    private var _binding: FragmentBuscarSeriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var genericoAdapter: GenericoAdapter
    private val viewModel: BuscarSeriesViewModel by viewModels()

    private val callback by lazy {
        multiseleccion()
    }

    private lateinit var actionMode: ActionMode


    //Para que el menu sea distinto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuscarSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Para el menu
    //Hacerlo bien
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_busquedas, menu)

        val buscar = menu.findItem(R.id.searchBuscar).actionView as SearchView
        buscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.buscarSeries(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                        BuscarSeriesFragmentDirections.actionBuscarSeriesFragmentToBuscarSeriesDetallada(
                            id
                        )
                    findNavController().navigate(irsePantalla)
                }
            })
        binding.rvBuscarSeries.adapter = genericoAdapter

        viewModel.genericos.observe(this, { pelis ->
            genericoAdapter.submitList(pelis)
        })
        viewModel.error.observe(this, {
            Toast.makeText(this.requireContext(), it, Toast.LENGTH_SHORT).show()
        })
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
                            .map { viewModel.addSerieFavorita(it.id) }
                        Toast.makeText(context, Constantes.ADD_EXITO, Toast.LENGTH_SHORT).show()
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
}