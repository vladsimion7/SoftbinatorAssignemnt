package ro.vlad.simion.softbinatior.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ro.vlad.simion.softbinatior.R
import ro.vlad.simion.softbinatior.data.networking.model.animal.Photos
import ro.vlad.simion.softbinatior.databinding.FragmentAnimalDetailsBinding

@AndroidEntryPoint
class AnimalDetailsFragment : Fragment() {

    private val navArgs: AnimalDetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<AnimalDetailsViewModel>()
    private lateinit var binding: FragmentAnimalDetailsBinding
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimalDetailsBinding.inflate(inflater)
        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAnimal(navArgs.animalId)
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.animalResponseFlow.collect {
                when (it) {
                    is AnimalResponseState.Error -> {
                        binding.progressIndicator.isVisible = false
                        Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    AnimalResponseState.Loading -> {
                        binding.progressIndicator.isVisible = true
                    }

                    is AnimalResponseState.Success -> {
                        binding.apply {
                            progressIndicator.isVisible = false
                            txtBreed.text = it.animal.breeds?.primary
                            txtStatus.text = it.animal.status
                            txtCoat.text = it.animal.coat
                            txtColor.text = it.animal.colors?.getColors()

                            txtName.text = it.animal.name
                            txtGender.text = it.animal.gender
                            txtSize.text = it.animal.size
                            txtAge.text = it.animal.age
                            txtDescription.text = it.animal.description
                            txtMeetName.text = getString(R.string.meet_name_format, it.animal.name)
                            txtDistance.text = it.animal.distance?.toString()
                            txtAddress.text = it.animal.contact?.address?.getFormattedAddress()

                            if (it.animal.photos.size > 0) {
                                counter.text =
                                    getString(
                                        R.string.page_counter_format,
                                        (1).toString(),
                                        it.animal.photos.size.toString()
                                    )
                            }
                        }

                        setupPhotosViewPager(it.animal.photos)

                    }
                }
            }
        }
    }

    private fun setupPhotosViewPager(photos: ArrayList<Photos>) {
        imageViewPagerAdapter = ImageViewPagerAdapter(photos.mapNotNull { it.full })

        binding.viewPager.adapter = imageViewPagerAdapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val currentPageIndex = 0
        binding.viewPager.currentItem = currentPageIndex

        // registering for page change callback
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.counter.text =
                        getString(
                            R.string.page_counter_format,
                            (position + 1).toString(),
                            photos.size.toString()
                        )
                }
            }
        )
    }
}