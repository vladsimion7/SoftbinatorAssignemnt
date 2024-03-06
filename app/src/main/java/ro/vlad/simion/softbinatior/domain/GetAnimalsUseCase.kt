package ro.vlad.simion.softbinatior.domain

import kotlinx.coroutines.flow.Flow
import ro.vlad.simion.softbinatior.data.networking.model.NetworkResult
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalsResponse
import ro.vlad.simion.softbinatior.data.networking.repository.PetFinderRepository
import javax.inject.Inject

class GetAnimalsUseCase @Inject constructor(private val repository: PetFinderRepository) {
    fun getAnimals(page: Int): Flow<NetworkResult<AnimalsResponse>> {
        return repository.getAnimals(page)
    }
}