package ro.vlad.simion.softbinatior.data.networking.model.animal

import com.google.gson.annotations.SerializedName

data class AnimalsResponse(
    @SerializedName("animals") var animals: ArrayList<AnimalModel> = arrayListOf(),
    @SerializedName("pagination") var pagination: Pagination? = Pagination()
)

data class Pagination(
    @SerializedName("count_per_page") var countPerPage: Int? = null,
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("current_page") var currentPage: Int? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    )