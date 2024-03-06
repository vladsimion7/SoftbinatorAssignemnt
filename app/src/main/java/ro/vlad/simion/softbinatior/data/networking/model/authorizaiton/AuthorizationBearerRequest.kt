package ro.vlad.simion.softbinatior.data.networking.model.authorizaiton

import com.google.gson.annotations.SerializedName

data class AuthorizationBearerRequest(
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
    @SerializedName("grant_type")
    val grantType: String = "client_credentials"
)