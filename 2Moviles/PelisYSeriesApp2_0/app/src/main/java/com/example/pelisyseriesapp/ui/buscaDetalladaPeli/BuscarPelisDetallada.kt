package com.example.pelisyseriesapp.ui.buscaDetalladaPeli

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.pelisyseriesapp.R
import com.example.pelisyseriesapp.databinding.FragmentBuscarPelisDetalladaBinding
import com.example.pelisyseriesapp.domain.Pelicula
import com.example.pelisyseriesapp.ui.ActoresActuanAdapter
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BuscarPelisDetallada : Fragment() {
    private var _binding: FragmentBuscarPelisDetalladaBinding? = null
    private val binding get() = _binding!!

    private lateinit var pelicula: Pelicula
    private var menuFragment: Menu? = null

    //Adaptador de Cast
    private lateinit var actoresActuanAdapter: ActoresActuanAdapter

    //ViewModel
    private val viewModel: BuscarPelisDetalladaViewModel by viewModels()

    //Para que el menu sea distinto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //Inflar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBuscarPelisDetalladaBinding.inflate(inflater, container, false)
        return binding.root
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detallado, menu)
        menuFragment = menu
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Vamos a recoger los datos que nos vienen del buscarPelis
        val args: BuscarPelisDetalladaArgs by navArgs()
        //Aquí irá el iniciar el adaptador del cast.
        adapter()
        //viewModel
        viewModel()
        if (args.idPeli != 0) {
            viewModel.handleEvent(DetalladaPelisContract.DetalladaPelisEvent.BuscarPeli(args.idPeli))
        } else {
            Toast.makeText(this.requireContext(), Constantes.PELI_NO_CARGADA, Toast.LENGTH_SHORT)
                .show()
        }
    }

    //Opciones del menu
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.Menufavorito -> {
                if (!pelicula.esFavorita) {
                    viewModel.handleEvent(DetalladaPelisContract.DetalladaPelisEvent.AddPeliFavorita(
                        pelicula.id))
                    menuFragment?.findItem(R.id.Menufavorito)
                        ?.setIcon(R.drawable.ic_baseline_star_24)
                    menuFragment?.findItem(R.id.Menufavorito)?.isEnabled = false
                    menuFragment?.findItem(R.id.MenuVista)?.isVisible = true
                }
                //No hay else porque si es favorito, NO puede tocarla más.
                true
            }
            R.id.MenuVista -> {
                if (!pelicula.haSidoVista) {
                    pelicula.haSidoVista = true
                    viewModel.handleEvent(DetalladaPelisContract.DetalladaPelisEvent.UpdatePeli(
                        pelicula))
                    menuFragment?.findItem(R.id.MenuVista)?.setIcon(R.drawable.ic_baseline_tv_24)
                    menuFragment?.findItem(R.id.MenuVista)?.isEnabled = false
                }
                //Idem arriba. no hay else porque NO se puede tocar cuando es visible.
            }
            else -> false
        }
        return super.onOptionsItemSelected(menuItem)
    }


    private fun cambiarOpcionesMenu() {
        if (menuFragment != null) {
            val haSidoVista = menuFragment?.findItem(R.id.MenuVista)
            val esFavorita = menuFragment?.findItem(R.id.Menufavorito)
            haSidoVista?.isVisible = true
            esFavorita?.isVisible = true
            if (pelicula.esFavorita) {
                esFavorita?.setIcon(R.drawable.ic_baseline_star_24)
                esFavorita?.isEnabled = false
                if (pelicula.haSidoVista) {
                    haSidoVista?.setIcon(R.drawable.ic_baseline_tv_24)
                    haSidoVista?.isEnabled = false
                } else {
                    haSidoVista?.setIcon(R.drawable.ic_baseline_tv_off_24)
                }
            } else {
                esFavorita?.setIcon(R.drawable.ic_baseline_star_outline_24)
                haSidoVista?.isVisible = false
            }
        }
    }

    private fun adapter() {
        actoresActuanAdapter = ActoresActuanAdapter(this.requireContext())
        binding.rvActoresPelis.adapter = actoresActuanAdapter
    }

    private fun viewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    if (value.isLoading) {
                        binding.pbCargarPelisDetallada.visibility = View.VISIBLE
                    } else {
                        binding.pbCargarPelisDetallada.visibility = View.GONE
                    }
                    pelicula = value.pelicula
                    with(binding) {
                        tvPonerFecha.text = pelicula.fechaEstreno
                        tvPonerLenguajeOriginal.text = pelicula.lenguajeOriginal
                        tvPonerSipnosis.text = pelicula.sipnosis
                        tvPonerTitulo.text = pelicula.titulo
                        tvPonerTituloOriginal.text = pelicula.tituloOriginal
                        ivPortadaDetallada.load(pelicula.poster)
                        actoresActuanAdapter.submitList(pelicula.cast)
                        cambiarOpcionesMenu()
                    }
                    value.error?.let {
                        Toast.makeText(this@BuscarPelisDetallada.requireContext(),
                            it,
                            Toast.LENGTH_SHORT).show()
                        viewModel.handleEvent(DetalladaPelisContract.DetalladaPelisEvent.ErrorMostrado)
                    }
                }
            }
        }

    }
}
