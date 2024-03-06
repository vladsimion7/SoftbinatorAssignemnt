package ro.vlad.simion.softbinatior.domain

import kotlinx.coroutines.flow.Flow
import ro.vlad.simion.softbinatior.data.networking.model.NetworkResult
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalResponse
import ro.vlad.simion.softbinatior.data.networking.repository.PetFinderRepository
import javax.inject.Inject

class GetAnimalUseCase @Inject constructor(private val repository: PetFinderRepository) {
    fun getAnimal(id: String): Flow<NetworkResult<AnimalResponse>> {
        return repository.getAnimal(id)
    }
}