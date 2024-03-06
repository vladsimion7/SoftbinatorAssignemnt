package ro.vlad.simion.softbinatior.data.networking.repository

import io.reactivex.Observable
import ro.vlad.simion.softbinatior.data.networking.PetFinderApi
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalResponse
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalsResponse
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerRequest
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerResponse
import javax.inject.Inject

class PetFinderRepositoryImpl @Inject constructor(
    private val api: PetFinderApi
) : BaseRepository(), PetFinderRepository {
    override fun getAuthorizationBearer(authBearerRequest: AuthorizationBearerRequest) =
        safeApiCall {
            api.getAuthorizationBearer(authBearerRequest)
        }

    override fun getAnimals(page: Int) =
        safeApiCall {
            api.getAnimals(page)
        }

    override fun getAnimal(id: String) = safeApiCall {
        api.getAnimal(id)
    }

    override fun getAuthorizationBearerRx(authBearerRequest: AuthorizationBearerRequest): Observable<AuthorizationBearerResponse> {
        return api.getAuthorizationBearerRx(authBearerRequest)
    }

    override fun getAnimalsRx(page: Int): Observable<AnimalsResponse> {
        return api.getAnimalsRx(page)
    }

    override fun getAnimalRx(id: String): Observable<AnimalResponse> {
        return api.getAnimalRx(id)
    }

}