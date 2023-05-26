package com.example.technicaltaskforitsurf.ui.fragment.home

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.notesm.R
import com.example.notesm.databinding.FragmentHomeBinding
import com.example.technicaltaskforitsurf.data.localdb.preferences.SettingsSharedPreferences
import com.example.technicaltaskforitsurf.ui.alertdialog.AlertDialogFragment
import com.example.technicaltaskforitsurf.ui.base.BaseFragment
import com.example.technicaltaskforitsurf.ui.fragment.home.adapter.HomeCarsAdapter
import com.example.technicaltaskforitsurf.ui.fragment.newcar.maxFreeViewedCars
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var preferences: SettingsSharedPreferences

    private val homeCarsAdapter = HomeCarsAdapter(this::onItemClick)

    override fun initialize() {
        initCarsAdapter()
    }

    private fun initCarsAdapter() {
        binding.rvHomeCars.adapter = homeCarsAdapter
    }

    override fun constructListeners() {
        setupSearch()
    }

    private fun setupSearch() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s?.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun performSearch(searchText: String?) {
        if (searchText.toString().isNotEmpty()) {
            viewModel.fetchCarsSearchName(searchText.toString())
        } else {
            viewModel.getCars()
        }
    }

    override fun launchObservers() {
        observeAllCars()
    }

    private fun observeAllCars() {
        safeFlowGather {
            viewModel.homeCarsState.collectLatest {
                homeCarsAdapter.submitList(it)
            }
        }
    }

    private fun onItemClick(id: Int) {
        when {
            preferences.isSubscribed -> {
                preferences.viewedCars += 1
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToMoreDetailsFragment(
                        id
                    )
                )
            }

            preferences.viewedCars < maxFreeViewedCars -> {
                preferences.viewedCars += 1
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToMoreDetailsFragment(
                        id
                    )
                )
            }

            else -> {
                val dialog = AlertDialogFragment.newInstance("Истекло", "Купите подписку !")
                dialog.show(childFragmentManager, "alert")
            }
        }
    }

}