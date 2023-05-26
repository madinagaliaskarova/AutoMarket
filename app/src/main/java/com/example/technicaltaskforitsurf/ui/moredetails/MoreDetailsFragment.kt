package com.example.technicaltaskforitsurf.ui.moredetails

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.notesm.R
import com.example.notesm.databinding.FragmentMoreDetailsBinding
import com.example.technicaltaskforitsurf.ui.base.BaseFragment
import com.example.technicaltaskforitsurf.ui.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

@AndroidEntryPoint
class MoreDetailsFragmentFragment :
    BaseFragment<FragmentMoreDetailsBinding, CarDetailViewModel>(R.layout.fragment_more_details) {

    override val binding by viewBinding(FragmentMoreDetailsBinding::bind)
    override val viewModel by viewModels<CarDetailViewModel>()

    private val args by navArgs<MoreDetailsFragmentFragmentArgs>()

    override fun constructListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun launchObservers() {
        fetchData()
    }

    private fun fetchData() {
        viewModel.getCar(args.id)
        safeFlowGather {
            viewModel.carState.collectLatest {
                it?.let {
                    binding.myImageview.loadImage(it.imageUrl)
                    binding.tvCarName.text = it.carName
                    binding.tvEngineValue.text = it.engineValue.toString()
                    binding.tvYear.text = it.releaseYear.toString()
                    binding.tvDateJoin.text = Date(it.dateJoined).toString()
                }
            }
        }
    }
}