package io.to.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    val name: String,
    val percentage: Double?,
    val description: String?
) : Parcelable {

    fun toStringFormat(): String{
        if (percentage == null && description == null){
            return name
        }
        if (description == null){
            return "$name $percentage%"
        }
        return "$name $percentage% ($description)"
    }

}