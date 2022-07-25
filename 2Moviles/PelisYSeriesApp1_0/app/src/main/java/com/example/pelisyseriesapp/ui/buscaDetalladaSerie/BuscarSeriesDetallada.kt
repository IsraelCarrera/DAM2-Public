package com.example.pelisyseriesapp.ui.buscaDetalladaSerie

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

@AndroidEntryPoint
class BuscarSeriesDetallada : Fragment() {
    private var _binding: FragmentBuscarSeriesDetalladaBinding? = null
    private val binding get() = _binding!!

    private lateinit var seriePadre: Serie
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
        savedInstanceState: Bundle?
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
        actoresActuanAdapter = ActoresActuanAdapter(this.requireContext())
        binding.rvActoresSeries.adapter = actoresActuanAdapter
        //Adaptador de temporadas
        temporadasAdapter = TemporadasAdapter(this.requireContext(),
            object : TemporadasAdapter.GenericAction {
                override fun verMas(temporada: Temporada) {
                    if (seriePadre.esFavorita) {
                        val irsePantalla =
                            BuscarSeriesDetalladaDirections.actionBuscarSeriesDetalladaToVerCapitulosTemporada(
                                temporada
                            )
                        findNavController().navigate(irsePantalla)
                    }
                }

            })
        binding.rvTemporadaCapitulos.adapter = temporadasAdapter
        //ViewModel
        viewModel.serie.observe(this, { serie ->
            with(binding) {
                seriePadre = serie
                tvPonerFecha.text = serie.fechaEstreno
                tvPonerFechaFin.text = serie.fechaFinal
                tvPonerLenguajeOriginal.text = serie.lenguajeOriginal
                tvPonerSipnosis.text = serie.sipnosis
                tvPonerTitulo.text = serie.titulo
                tvPonerTituloOriginal.text = serie.tituloOriginal
                ivPortadaDetallada.load(serie.poster)
                //Falta añadir CAST principal y luego temporadas y episodios
                //RV de cast
                actoresActuanAdapter.submitList(serie.cast)
                //RV Temporadas y dentro el RV de episodios
                temporadasAdapter.submitList(serie.temporadas)
                cambiarOpcionesMenu()
            }
        })
        viewModel.error.observe(this, {
            Toast.makeText(this.requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        if (args.id != 0) {
            viewModel.buscarSerieId(args.id)
        } else {
            Toast.makeText(this.requireContext(), Constantes.SERIE_NO_CARGADA, Toast.LENGTH_SHORT)
                .show()
        }
    }

    //Opciones del menu
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.Menufavorito -> {
                if (!seriePadre.esFavorita) {
                    viewModel.addSerieFavorita(seriePadre.id)
                    menuFragment?.findItem(R.id.Menufavorito)
                        ?.setIcon(R.drawable.ic_baseline_star_24)
                    menuFragment?.findItem(R.id.Menufavorito)?.isEnabled = false
                    menuFragment?.findItem(R.id.MenuVista)?.isVisible = true
                }
                //No hay else porque si es favorito, NO puede tocarla más.
                true
            }
            R.id.MenuVista -> {
                if (!seriePadre.haSidoVista) {
                    seriePadre.haSidoVista = true
                    viewModel.updateSerie(seriePadre)
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
            if (seriePadre.esFavorita) {
                esFavorita?.setIcon(R.drawable.ic_baseline_star_24)
                esFavorita?.isEnabled = false
                if (seriePadre.haSidoVista) {
                    haSidoVista?.setIcon(R.drawable.ic_baseline_tv_24)
                    haSidoVista?.isEnabled = false
                } else {
                    haSidoVista?.setIcon(R.drawable.ic_baseline_tv_off_24)
                }
            } else {
                esFavorita?.setIcon(R.drawable.ic_baseline_star_outline_24)
                haSidoVista?.isVisible = false
            }
        } else {
            Toast.makeText(this.requireContext(), Constantes.MENU_NULO, Toast.LENGTH_SHORT).show()
        }
    }
}