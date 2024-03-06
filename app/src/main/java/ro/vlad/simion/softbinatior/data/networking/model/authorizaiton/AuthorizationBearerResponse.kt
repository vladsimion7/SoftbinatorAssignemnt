package ro.vlad.simion.softbinatior.data.networking.model.authorizaiton

import com.google.gson.annotations.SerializedName

data class AuthorizationBearerResponse(
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("access_token")
    val accessToken: String
)

//{"token_type":"Bearer","expires_in":3600,"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI4RnZCOTJDT0wzbG9Ka1JIQm96R1BMT1ZLWlRHNENnWGFsNkRvdTZFanNINWxqMlNYQiIsImp0aSI6ImQzYTQ2NGRhYzMwZjc5Yzk1MmViNTVkNTNlOTUzN2YxNTY0YmYzM2U3NWE0ODcwODU3N2I2Yjk3NjBiMmI4NTU1MmVjN2EwNDE4YzM2NjljIiwiaWF0IjoxNzA5NTY0OTM3LCJuYmYiOjE3MDk1NjQ5MzcsImV4cCI6MTcwOTU2ODUzNywic3ViIjoiIiwic2NvcGVzIjpbXX0.mfg1M2t11s1SFwbGiEC28W_g2yrFDmku2Ut1mF84yHA9hCAgmNADeMjpGU1ivWuwmeoQBBAih4_PVb1zI4gKw1s5hcVPvS3_zXqdIzr6znPIpD8fMqoNQB6DDh-cqD5GSLliMhK05E1G-qx9UKvbdLF9oaFwQyCY1ay-MzmNjLJXtGs9G_Pt_51yiA9r-Kj-pg2VZiFU4qodqC-08xWWtS5FNKgbX5cwcGrlOEm6z7s4RJrvQjmYyTXh3v4Ny6xYikj_6RqPzTL8lpO8_xZu-2OZLIQ8OPc531_H-BQCVonyUmwLXHoFOmaDXI2GExqPdJ6o7AngIcI74HgC3MZczw"}
