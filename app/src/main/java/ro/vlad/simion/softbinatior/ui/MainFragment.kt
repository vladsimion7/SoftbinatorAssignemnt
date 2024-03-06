package ro.vlad.simion.softbinatior.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.text.capitalize
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ro.vlad.simion.softbinatior.R
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalModel
import ro.vlad.simion.softbinatior.databinding.AnimalListItemBinding
import ro.vlad.simion.softbinatior.databinding.FragmentMainBinding
import ro.vlad.simion.softbinatior.ui.pagination.AnimalsDiffCallback
import ro.vlad.simion.softbinatior.ui.pagination.PaginationScrollListener
import java.util.Locale

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: FragmentMainBinding
    private lateinit var animalsAdapter: ListDelegationAdapter<List<AnimalModel>>

    companion object {
        const val ANIMAL_ID_KEY = "animalId"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        addScrollListener()
        observeData()
    }


    private fun observeData() {
        lifecycleScope.launch {
            viewModel.authResponseFlow.collect {
                when (it) {
                    is AuthResponseState.Error -> {
                        binding.progressIndicator.isVisible = false
                        Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    is AuthResponseState.Loading -> {
                        binding.progressIndicator.isVisible = true
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.animalsResponseFlow.collect {
                when (it) {
                    is AnimalsResponseState.Success -> {
                        updateAnimals(it.animals)
                        binding.progressIndicator.isVisible = false
                    }

                    is AnimalsResponseState.Error -> {
                        binding.progressIndicator.isVisible = false
                        Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    AnimalsResponseState.Loading -> {
                        binding.progressIndicator.isVisible = true
                    }
                }
            }
        }

    }

    private fun setupRecyclerView() {
        val animalItemDelegate =
            adapterDelegateViewBinding<AnimalModel, AnimalModel, AnimalListItemBinding>({ layoutInflater, parent ->
                AnimalListItemBinding.inflate(layoutInflater, parent, false)
            }) {
                bind {
                    binding.txtName.text = item.name
                    binding.txtBreed.text = item.breeds?.primary
                    binding.txtStatus.text = item.status?.replaceFirstChar { it.titlecaseChar() }
                    binding.txtGender.text = item.gender
                    binding.txtSize.text = item.size
                    Glide.with(requireContext()).load(item.photos.firstOrNull()?.small)
                        .placeholder(R.drawable.ic_pet_placeholder)
                        .into(binding.imvAnimal)
                    binding.root.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putSerializable(ANIMAL_ID_KEY, item.id.toString())
                        findNavController().navigate(R.id.detailsFragment, bundle)
                    }
                }
            }

        animalsAdapter = ListDelegationAdapter(animalItemDelegate)
        binding.rvAnimals.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAnimals.adapter = animalsAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAnimals(animals: List<AnimalModel>) {
        if (animalsAdapter.items != null) {
            animalsAdapter.items?.let { items ->
                val diffCallback = AnimalsDiffCallback(animals, items)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                diffResult.dispatchUpdatesTo(animalsAdapter)
            }
        } else {
            animalsAdapter.items = animals
        }
        animalsAdapter.notifyDataSetChanged()
    }

    private fun addScrollListener() {
        binding.rvAnimals.addOnScrollListener(object :
            PaginationScrollListener(binding.rvAnimals.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                viewModel.fetchAnimals()
            }

            override fun isLastPage() = viewModel.areAllAnimalsLoaded()

            override fun isLoading() = viewModel.isLoading()
        })
    }

}