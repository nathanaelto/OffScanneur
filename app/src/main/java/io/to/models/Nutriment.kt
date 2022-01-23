package io.to.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nutriment(
    val quantityServing: String,
    val quantityReference: String,
    val nutriment: String,
    val level: NutrimentLevel,
    val unit: String
): Parcelable {

    companion object {
        val vowel = "AEIOUYaeiouy"
    }

    fun formated(): String {
        if (nutriment[0] in vowel) {
            return "${quantityReference}g d'${nutriment}"
        }
        return "${quantityReference}g de $nutriment"
    }

}