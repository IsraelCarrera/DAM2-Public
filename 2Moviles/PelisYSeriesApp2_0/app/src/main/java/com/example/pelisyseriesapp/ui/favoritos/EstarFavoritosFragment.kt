package com.example.pelisyseriesapp.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.pelisyseriesapp.databinding.FragmentEstarFavoritosBinding
import com.example.pelisyseriesapp.domain.Favoritos
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EstarFavoritosFragment : Fragment() {
    private var _binding: FragmentEstarFavoritosBinding? = null
    private val binding get() = _binding!!

    //Adaptadores
    private lateinit var favoritosAdapterPelis: FavoritosAdapter
    private lateinit var favoritosAdapterSeries: FavoritosAdapter

    //viewModel
    private val viewModel: FavoritosViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEstarFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Adapter
        adapterPelis()
        adapterSerie()
        //Para el borrar. Para que vaya a la derecha y se borre, vamos.
        touchBorrarPelis()
        touchBorrarSeries()
        //Viewmodel
        viewModelFavoritos()
        viewModel.handleEvent(FavoritosContract.FavoritosEvent.GetAllFavoritos)
    }

    private fun adapterPelis() {
        favoritosAdapterPelis = FavoritosAdapter(this.requireContext(),
            object : FavoritosAdapter.GenericAction {
                override fun verMas(favoritos: Favoritos) {
                    val irsePantalla =
                        EstarFavoritosFragmentDirections.actionEstarFavoritosFragmentToBuscarPelisDetallada(
                            favoritos.id
                        )
                    findNavController().navigate(irsePantalla)
                }

                override fun deleteItem(favoritos: Favoritos) {
                    viewModel.handleEvent(FavoritosContract.FavoritosEvent.BorrarPelicula(favoritos.id))
                }
            })

        binding.rvFavoritosPelis.adapter = favoritosAdapterPelis
    }

    private fun adapterSerie() {
        favoritosAdapterSeries = FavoritosAdapter(this.requireContext(),
            object : FavoritosAdapter.GenericAction {
                override fun verMas(favoritos: Favoritos) {
                    val irsePantalla =
                        EstarFavoritosFragmentDirections.actionEstarFavoritosFragmentToBuscarSeriesDetallada(
                            favoritos.id
                        )
                    findNavController().navigate(irsePantalla)
                }

                override fun deleteItem(favoritos: Favoritos) {
                    viewModel.handleEvent(FavoritosContract.FavoritosEvent.BorrarSerie(favoritos.id))
                }
            })

        binding.rvFavoritosSeries.adapter = favoritosAdapterSeries
    }

    private fun touchBorrarPelis() {
        val touchHelper = ItemTouchHelper(favoritosAdapterPelis.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvFavoritosPelis)
    }

    private fun touchBorrarSeries() {
        val touchHelper = ItemTouchHelper(favoritosAdapterSeries.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvFavoritosSeries)
    }

    private fun viewModelFavoritos() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    if (value.isLoading) {
                        binding.pbCargarFavoritos.visibility = View.VISIBLE
                    } else {
                        binding.pbCargarFavoritos.visibility = View.GONE
                    }
                    favoritosAdapterPelis.submitList(value.pelis)
                    favoritosAdapterSeries.submitList(value.series)
                    value.error?.let {
                        Toast.makeText(this@EstarFavoritosFragment.requireContext(),
                            it,
                            Toast.LENGTH_SHORT).show()
                        viewModel.handleEvent(FavoritosContract.FavoritosEvent.ErrorMostrado)
                    }
                    value.mensaje?.let {
                        Toast.makeText(this@EstarFavoritosFragment.requireContext(),
                            it,
                            Toast.LENGTH_SHORT).show()
                        viewModel.handleEvent(FavoritosContract.FavoritosEvent.MensajeMostrado)
                    }
                }
            }
        }
    }
}