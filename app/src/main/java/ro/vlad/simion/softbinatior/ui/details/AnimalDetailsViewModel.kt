package ro.vlad.simion.softbinatior.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalModel
import ro.vlad.simion.softbinatior.data.networking.model.doOnFailure
import ro.vlad.simion.softbinatior.data.networking.model.doOnLoading
import ro.vlad.simion.softbinatior.data.networking.model.doOnSuccess
import ro.vlad.simion.softbinatior.domain.GetAnimalUseCase
import javax.inject.Inject

@HiltViewModel
class AnimalDetailsViewModel @Inject constructor(
    private val getAnimalUseCase: GetAnimalUseCase
) : ViewModel() {


    private val _animalResponseFlow =
        MutableStateFlow<AnimalResponseState>(AnimalResponseState.Loading)
    val animalResponseFlow: StateFlow<AnimalResponseState> = _animalResponseFlow

    fun getAnimal(id: String) {
        viewModelScope.launch {
            getAnimalUseCase.getAnimal(id)
                .doOnSuccess {
                    _animalResponseFlow.emit(AnimalResponseState.Success(it.animal))
                }
                .doOnLoading {
                    _animalResponseFlow.emit(AnimalResponseState.Loading)
                }
                .doOnFailure {
                    _animalResponseFlow.emit(AnimalResponseState.Error(it?.message ?: ""))
                }
                .collect()
        }
    }
}

sealed class AnimalResponseState {
    data class Success(val animal: AnimalModel) : AnimalResponseState()
    data class Error(val errorMessage: String) : AnimalResponseState()
    data object Loading : AnimalResponseState()
}