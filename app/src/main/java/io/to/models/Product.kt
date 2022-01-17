package io.to.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val name: String,
    val marque: String,
    val code_barres: String,
    val nutriscore: String,
    val url: String,
    val quantite: String,
    val caloris: String,
    val liste_pays: Array<String>,
    val liste_ingredients: Array<Ingredient>,
    val liste_substances: Array<String>,
    val liste_aditifs: Array<String>,
    val liste_nutriment: Array<Nutriment>
) : Parcelable {


    fun paysFormat(): String {
        return liste_pays.joinToString(", ")
    }

    fun ingredientsFormat(): String {
        if (liste_ingredients.isEmpty()) {
            return "Aucun"
        }
        val ingredientListFormat: ArrayList<String> = ArrayList()
        liste_ingredients.forEach { ingredient -> ingredientListFormat.add(ingredient.toStringFormat()) }
        return ingredientListFormat.joinToString(", ")
    }

    fun substancesFormat(): String {
        if (liste_substances.isEmpty()) {
            return "Aucune"
        }
        return liste_substances.joinToString(", ")
    }

    fun aditifsFormat(): String {
        if (liste_aditifs.isEmpty()) {
            return "Aucun"
        }
        return liste_aditifs.joinToString(", ")
    }

    override fun toString(): String {
        return "Product(nom='$name', marque='$marque', code_barres='$code_barres', nutriscore='$nutriscore', url='$url', quantité='$quantite', caloris='$caloris', liste_pays=${liste_pays.contentToString()}, liste_ingredients=${liste_ingredients.contentToString()}, liste_substances=${liste_substances.contentToString()}, liste_aditifs=${liste_aditifs.contentToString()})"
    }
}