package com.example.pelisyseriesapp.ui.verCapitulosTemporada

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.pelisyseriesapp.R
import com.example.pelisyseriesapp.databinding.FragmentVerCapitulosTemporadaBinding
import com.example.pelisyseriesapp.domain.Episodio
import com.example.pelisyseriesapp.domain.Temporada
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerCapitulosTemporada : Fragment() {
    private var _binding: FragmentVerCapitulosTemporadaBinding? = null
    private val binding get() = _binding!!

    //El RV
    private lateinit var capitulosAdapter: CapitulosAdapter

    //El VM
    private val viewModel: VerCapitulosViewModel by viewModels()

    private var menuFragment: Menu? = null
    private lateinit var temporada: Temporada

    //Lo del menu distinto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //Inflando
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVerCapitulosTemporadaBinding.inflate(inflater, container, false)
        return binding.root
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_capitulos_temporada, menu)
        menuFragment = menu
        if (temporada.haSidoVista) {
            menu.findItem(R.id.MenuVista)?.setIcon(R.drawable.ic_baseline_tv_24)
            menu.findItem(R.id.MenuVista)?.isEnabled = false

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: VerCapitulosTemporadaArgs by navArgs()
        temporada = args.temporadaVer!!
        //Adapter
        adapter()
        viewModel()
        binding.tvNombreTemporada.text = temporada.nombre
        viewModel.handleEvent(VerCapitulosTemporadaContract.VerCapitulosTemporadaEvent.GetEpisodios(
            temporada.id,
            temporada.numeroTemporada))
    }

    //Opciones del menu
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.MenuVista -> {
                if (!temporada.haSidoVista) {
                    temporada.haSidoVista = true
                    viewModel.handleEvent(VerCapitulosTemporadaContract.VerCapitulosTemporadaEvent.UpdateTemporada(
                        temporada))
                    menuFragment?.findItem(R.id.MenuVista)?.setIcon(R.drawable.ic_baseline_tv_24)
                    menuFragment?.findItem(R.id.MenuVista)?.isEnabled = false
                }
                //Idem arriba. no hay else porque NO se puede tocar cuando es visible.
            }
            else -> false
        }
        return super.onOptionsItemSelected(menuItem)
    }

    private fun adapter() {
        capitulosAdapter = CapitulosAdapter(this.requireContext(),
            object : CapitulosAdapter.GenericAction {
                override fun updateCapitulo(episodio: Episodio) {
                    episodio.haSidoVisto = true
                    viewModel.handleEvent(VerCapitulosTemporadaContract.VerCapitulosTemporadaEvent.UpdateEpisodio(
                        episodio))
                }
            })
        binding.rvCapitulos.adapter = capitulosAdapter
    }

    private fun viewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    value.error?.let {
                        Toast.makeText(this@VerCapitulosTemporada.requireContext(),
                            it,
                            Toast.LENGTH_SHORT).show()
                        viewModel.handleEvent(VerCapitulosTemporadaContract.VerCapitulosTemporadaEvent.ErrorMostrado)
                    }
                    capitulosAdapter.submitList(value.capitulos)
                }
            }
        }
    }
}