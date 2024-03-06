package ro.vlad.simion.softbinatior.domain

import kotlinx.coroutines.flow.Flow
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerRequest
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerResponse
import ro.vlad.simion.softbinatior.data.networking.model.NetworkResult
import ro.vlad.simion.softbinatior.data.networking.repository.PetFinderRepository
import javax.inject.Inject

class GetAuthorizationBearerUseCase @Inject constructor(private val repository: PetFinderRepository) {
    fun getAuthorizationBearer(request: AuthorizationBearerRequest): Flow<NetworkResult<AuthorizationBearerResponse>> {
        return repository.getAuthorizationBearer(request)
    }
}