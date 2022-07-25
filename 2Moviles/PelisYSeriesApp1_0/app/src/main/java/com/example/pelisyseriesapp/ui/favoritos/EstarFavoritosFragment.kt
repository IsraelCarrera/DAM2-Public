package com.example.pelisyseriesapp.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.pelisyseriesapp.databinding.FragmentEstarFavoritosBinding
import com.example.pelisyseriesapp.domain.Favoritos
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EstarFavoritosFragment : Fragment() {
    private var _binding: FragmentEstarFavoritosBinding? = null
    private val binding get() = _binding!!

    //Adaptador
    private lateinit var favoritosAdapter: FavoritosAdapter

    //viewModel
    private val viewModel: FavoritosViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEstarFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Adapter
        favoritosAdapter = FavoritosAdapter(this.requireContext(),
            object : FavoritosAdapter.GenericAction {
                override fun verMas(favoritos: Favoritos) {
                    if (favoritos.tipo == Constantes.PELICULA) {
                        val irsePantalla =
                            EstarFavoritosFragmentDirections.actionEstarFavoritosFragmentToBuscarPelisDetallada(
                                favoritos.id
                            )
                        findNavController().navigate(irsePantalla)
                    } else {
                        val irsePantalla =
                            EstarFavoritosFragmentDirections.actionEstarFavoritosFragmentToBuscarSeriesDetallada(
                                favoritos.id
                            )
                        findNavController().navigate(irsePantalla)
                    }
                }

                override fun deleteItem(favoritos: Favoritos) {
                    //Como no sé si se borra por ID o por objeto, hago la llamada al objeto.
                    if (favoritos.tipo == Constantes.PELICULA) {
                        viewModel.borrarPelicula(favoritos.id)
                        //Quería añadir un undo pero no me da tiempo.
                    } else {
                        viewModel.borrarSerie(favoritos.id)
                    }
                    //NO sé como actualizarlo, así que meto un getAllFavoritos en el VM y pista.
                }
            })
        binding.rvFavoritos.adapter = favoritosAdapter

        //Para el borrar. Para que vaya a la derecha y se borre, vamos.
        val touchHelper = ItemTouchHelper(favoritosAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvFavoritos)

        //Viewmodel
        viewModel.favoritos.observe(this, { fav ->
            favoritosAdapter.submitList(fav)
        })
        viewModel.error.observe(this, {
            Toast.makeText(this.requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.avisos.observe(this, {
            Toast.makeText(this.requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.getAllFavoritos()
    }
}