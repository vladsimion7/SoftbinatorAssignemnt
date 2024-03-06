package ro.vlad.simion.softbinatior.data.networking.model.animal

import com.google.gson.annotations.SerializedName

data class AnimalResponse(@SerializedName("animal") val animal: AnimalModel)
