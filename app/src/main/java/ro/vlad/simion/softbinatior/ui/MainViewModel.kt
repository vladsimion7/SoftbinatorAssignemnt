package ro.vlad.simion.softbinatior.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ro.vlad.simion.softbinatior.BuildConfig
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalModel
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerRequest
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerResponse
import ro.vlad.simion.softbinatior.data.networking.model.doOnFailure
import ro.vlad.simion.softbinatior.data.networking.model.doOnLoading
import ro.vlad.simion.softbinatior.data.networking.model.doOnSuccess
import ro.vlad.simion.softbinatior.data.storage.InMemoryDataStore
import ro.vlad.simion.softbinatior.domain.GetAnimalsUseCase
import ro.vlad.simion.softbinatior.domain.GetAuthorizationBearerRxUseCase
import ro.vlad.simion.softbinatior.domain.GetAuthorizationBearerUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAuthorizationBearerUseCase: GetAuthorizationBearerUseCase,
    private val getAuthorizationBearerRxUseCase: GetAuthorizationBearerRxUseCase,
    private val getAnimalsUseCase: GetAnimalsUseCase
) : ViewModel() {

    init {
        getAuthorizationBearer()
    }

    private var currentPage = 1
    private val loadedAnimals = arrayListOf<AnimalModel>()
    private var totalAnimalPages = -1

    private val _authResponseFlow =
        MutableStateFlow<AuthResponseState>(AuthResponseState.Loading)
    val authResponseFlow: StateFlow<AuthResponseState> = _authResponseFlow

    private val _animalsResponseFlow =
        MutableStateFlow<AnimalsResponseState>(AnimalsResponseState.Loading)
    val animalsResponseFlow: StateFlow<AnimalsResponseState> = _animalsResponseFlow

    private val _authResponseLiveData = MutableLiveData<AuthResponseState>()
    val authResponseLiveData: LiveData<AuthResponseState> = _authResponseLiveData

    private fun getAuthorizationBearer() {
        viewModelScope.launch {
            val authBearerRequest =
                AuthorizationBearerRequest(BuildConfig.API_KEY, BuildConfig.API_SECRET)
            getAuthorizationBearerUseCase.getAuthorizationBearer(authBearerRequest)
                .doOnSuccess {
                    InMemoryDataStore.setAuthBearer(it.accessToken)
                    fetchAnimals()
                }
                .doOnFailure {
                    _authResponseFlow.emit(
                        AuthResponseState.Error(
                            it?.message ?: ""
                        )
                    )
                }
                .collect()
        }
    }

    private fun getAuthorizationBearerRx() {
        val authBearerRequest =
            AuthorizationBearerRequest(BuildConfig.API_KEY, BuildConfig.API_SECRET)
        getAuthorizationBearerRxUseCase.getAuthorizationBearer(authBearerRequest)
            .subscribe(object : Observer<AuthorizationBearerResponse> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    _authResponseLiveData.value = AuthResponseState.Error(e.message ?: "")
                }

                override fun onComplete() {

                }

                override fun onNext(response: AuthorizationBearerResponse) {
                    InMemoryDataStore.setAuthBearer(response.accessToken)
                    fetchAnimals()
                }

            })
    }

    fun fetchAnimals() {
        viewModelScope.launch {
            getAnimalsUseCase.getAnimals(currentPage)
                .doOnSuccess {
                    if (it.animals.isNotEmpty()) {
                        totalAnimalPages = it.pagination?.totalPages ?: -1
                        loadedAnimals.addAll(it.animals)
                        _animalsResponseFlow.emit(AnimalsResponseState.Success(loadedAnimals))
                        currentPage += 1
                    }
                }
                .doOnLoading {
                    _animalsResponseFlow.emit(AnimalsResponseState.Loading)
                }
                .doOnFailure {
                    _animalsResponseFlow.emit(AnimalsResponseState.Error(it?.message ?: ""))
                }
                .collect()
        }
    }

    fun areAllAnimalsLoaded(): Boolean {
        return currentPage >= totalAnimalPages
    }

    fun isLoading(): Boolean {
        return animalsResponseFlow.value == AnimalsResponseState.Loading
    }

}

sealed class AuthResponseState {
    data class Error(val errorMessage: String) : AuthResponseState()
    data object Loading : AuthResponseState()
}

sealed class AnimalsResponseState {
    data class Success(val animals: ArrayList<AnimalModel>) : AnimalsResponseState()
    data class Error(val errorMessage: String) : AnimalsResponseState()
    data object Loading : AnimalsResponseState()
}