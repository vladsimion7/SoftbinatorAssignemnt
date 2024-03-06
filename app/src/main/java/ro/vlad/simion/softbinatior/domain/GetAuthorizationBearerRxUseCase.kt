package ro.vlad.simion.softbinatior.domain

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerRequest
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerResponse
import ro.vlad.simion.softbinatior.data.networking.repository.PetFinderRepository
import javax.inject.Inject

class GetAuthorizationBearerRxUseCase @Inject constructor(private val repository: PetFinderRepository) {
    fun getAuthorizationBearer(request: AuthorizationBearerRequest): Observable<AuthorizationBearerResponse> {
        return repository.getAuthorizationBearerRx(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}