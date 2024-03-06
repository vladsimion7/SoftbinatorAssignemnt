package ro.vlad.simion.softbinatior.data.networking.repository

import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerRequest
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerResponse
import ro.vlad.simion.softbinatior.data.networking.model.NetworkResult
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalModel
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalResponse
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalsResponse

interface PetFinderRepository {
    fun getAuthorizationBearer(authBearerRequest: AuthorizationBearerRequest): Flow<NetworkResult<AuthorizationBearerResponse>>
    fun getAnimals(page:Int): Flow<NetworkResult<AnimalsResponse>>
    fun getAnimal(id:String): Flow<NetworkResult<AnimalResponse>>

    fun getAuthorizationBearerRx(authBearerRequest: AuthorizationBearerRequest): Observable<AuthorizationBearerResponse>
    fun getAnimalsRx(page:Int): Observable<AnimalsResponse>
    fun getAnimalRx(id:String): Observable<AnimalResponse>
}