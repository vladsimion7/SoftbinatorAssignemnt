package ro.vlad.simion.softbinatior.data.networking.model.animal

import com.google.gson.annotations.SerializedName

data class AnimalModel(
    @SerializedName("id") val id: Int,
    @SerializedName("organization_id") val organizationId: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("species") val species: String? = null,
    @SerializedName("age") val age: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("coat") val coat: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("photos") val photos: ArrayList<Photos> = arrayListOf(),
    @SerializedName("status") val status: String? = null,
    @SerializedName("distance") val distance: Double? = null,
    @SerializedName("breeds") val breeds: Breeds? = Breeds(),
    @SerializedName("colors") val colors: Colors? = Colors(),
    @SerializedName("contact") val contact: Contact? = Contact(),

    )

data class Photos(
    @SerializedName("small") val small: String? = null,
    @SerializedName("medium") val medium: String? = null,
    @SerializedName("large") val large: String? = null,
    @SerializedName("full") val full: String? = null
)

data class Breeds(
    @SerializedName("primary") val primary: String? = null,
    @SerializedName("secondary") val secondary: String? = null,
    @SerializedName("mixed") val mixed: Boolean? = null,
    @SerializedName("unknown") val unknown: Boolean? = null
)

data class Colors(
    @SerializedName("primary") val primary: String? = "",
    @SerializedName("secondary") val secondary: String? = "",
    @SerializedName("tertiary") val tertiary: String? = ""
) {
    fun getColors(): String {
        return "$primary $secondary $tertiary"
    }
}

data class Address(
    @SerializedName("address1") val address1: String? = "",
    @SerializedName("address2") val address2: String? = "",
    @SerializedName("city") val city: String? = "",
    @SerializedName("state") val state: String? = "",
    @SerializedName("postcode") val postcode: String? = "",
    @SerializedName("country") val country: String? = ""
) {
    fun getFormattedAddress(): String {
        return "$address1 $address2 $city $state $postcode $country"
    }
}

data class Contact(
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("address") val address: Address? = Address()
)