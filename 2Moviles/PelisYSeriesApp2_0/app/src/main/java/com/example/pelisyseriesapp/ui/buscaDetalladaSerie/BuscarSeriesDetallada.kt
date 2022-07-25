package com.example.pelisyseriesapp.ui.buscaDetalladaSerie

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.pelisyseriesapp.R
import com.example.pelisyseriesapp.databinding.FragmentBuscarSeriesDetalladaBinding
import com.example.pelisyseriesapp.domain.Serie
import com.example.pelisyseriesapp.domain.Temporada
import com.example.pelisyseriesapp.ui.ActoresActuanAdapter
import com.example.pelisyseriesapp.utils.Constantes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BuscarSeriesDetallada : Fragment() {
    private var _binding: FragmentBuscarSeriesDetalladaBinding? = null
    private val binding get() = _binding!!

    private lateinit var serie: Serie
    private var menuFragment: Menu? = null

    //Adaptador de Cast
    private lateinit var actoresActuanAdapter: ActoresActuanAdapter

    //Adaptador de temporada
    private lateinit var temporadasAdapter: TemporadasAdapter

    //ViewModel
    private val viewModel: BuscarSeriesDetalladaViewModel by viewModels()

    //Para que el menu sea distinto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //Inflamos
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBuscarSeriesDetalladaBinding.inflate(inflater, container, false)
        return binding.root
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detallado, menu)
        menuFragment = menu
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: BuscarSeriesDetalladaArgs by navArgs()
        //El adaptador del cast.
        adapterCast()
        //Adaptador de temporadas
        adapterTemporadas()
        //ViewModel
        viewModel()

        if (args.id != 0) {
            viewModel.handleEvent(DetalladaSerieContract.DetalladaSeriesEvent.BuscarSerie(args.id))
        } else {
            Toast.makeText(this.requireContext(), Constantes.SERIE_NO_CARGADA, Toast.LENGTH_SHORT)
                .show()
        }
    }

    //Opciones del menu
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.Menufavorito -> {
                if (!serie.esFavorita) {
                    viewModel.handleEvent(DetalladaSerieContract.DetalladaSeriesEvent.AddSerieFavorita(
                        serie.id))
                    menuFragment?.findItem(R.id.Menufavorito)
                        ?.setIcon(R.drawable.ic_baseline_star_24)
                    menuFragment?.findItem(R.id.Menufavorito)?.isEnabled = false
                    menuFragment?.findItem(R.id.MenuVista)?.isVisible = true
                }
                //No hay else porque si es favorito, NO puede tocarla más.
                true
            }
            R.id.MenuVista -> {
                if (!serie.haSidoVista) {
                    serie.haSidoVista = true
                    viewModel.handleEvent(DetalladaSerieContract.DetalladaSeriesEvent.UpdateSerie(
                        serie))
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
            if (serie.esFavorita) {
                esFavorita?.setIcon(R.drawable.ic_baseline_star_24)
                esFavorita?.isEnabled = false
                if (serie.haSidoVista) {
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

    private fun adapterCast() {
        actoresActuanAdapter = ActoresActuanAdapter(this.requireContext())
        binding.rvActoresSeries.adapter = actoresActuanAdapter
    }

    private fun adapterTemporadas() {
        temporadasAdapter = TemporadasAdapter(this.requireContext(),
            object : TemporadasAdapter.GenericAction {
                override fun verMas(temporada: Temporada) {
                    if (serie.esFavorita) {
                        val irsePantalla =
                            BuscarSeriesDetalladaDirections.actionBuscarSeriesDetalladaToVerCapitulosTemporada(
                                temporada
                            )
                        findNavController().navigate(irsePantalla)
                    }
                }

            })
        binding.rvTemporadaCapitulos.adapter = temporadasAdapter
    }

    private fun viewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    if (value.isLoading) {
                        binding.pbSeriesDetallada.visibility = View.VISIBLE
                    } else {
                        binding.pbSeriesDetallada.visibility = View.GONE
                    }
                    serie = value.serie
                    with(binding) {
                        tvPonerFecha.text = serie.fechaEstreno
                        tvPonerFechaFin.text = serie.fechaFinal
                        tvPonerLenguajeOriginal.text = serie.lenguajeOriginal
                        tvPonerSipnosis.text = serie.sipnosis
                        tvPonerTitulo.text = serie.titulo
                        tvPonerTituloOriginal.text = serie.tituloOriginal
                        ivPortadaDetallada.load(serie.poster)
                        //RV de cast
                        actoresActuanAdapter.submitList(serie.cast)
                        //RV Temporadas y dentro el RV de episodios
                        temporadasAdapter.submitList(serie.temporadas)
                        cambiarOpcionesMenu()
                    }
                    value.error?.let {
                        Toast.makeText(this@BuscarSeriesDetallada.requireContext(),
                            it,
                            Toast.LENGTH_SHORT).show()
                        viewModel.handleEvent(DetalladaSerieContract.DetalladaSeriesEvent.ErrorMostrado)
                    }
                }
            }
        }
    }
}