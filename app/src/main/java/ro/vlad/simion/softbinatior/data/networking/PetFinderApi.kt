package ro.vlad.simion.softbinatior.data.networking

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalModel
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalResponse
import ro.vlad.simion.softbinatior.data.networking.model.animal.AnimalsResponse
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerRequest
import ro.vlad.simion.softbinatior.data.networking.model.authorizaiton.AuthorizationBearerResponse

interface PetFinderApi {

    @POST("/v2/oauth2/token")
    suspend fun getAuthorizationBearer(@Body authRequest: AuthorizationBearerRequest): Response<AuthorizationBearerResponse>

    @GET("/v2/animals")
    suspend fun getAnimals(@Query("page") pageNumber: Int,
    ): Response<AnimalsResponse>

    @GET("/v2/animals/{id}")
    suspend fun getAnimal(@Path("id") id: String,
    ): Response<AnimalResponse>


    @POST("/v2/oauth2/token")
    fun getAuthorizationBearerRx(@Body authRequest: AuthorizationBearerRequest): Observable<AuthorizationBearerResponse>

    @GET("/v2/animals")
    fun getAnimalsRx(@Query("page") pageNumber: Int,
    ): Observable<AnimalsResponse>

    @GET("/v2/animals/{id}")
    fun getAnimalRx(@Path("id") id: String,
    ): Observable<AnimalResponse>
}