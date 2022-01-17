package io.to.models

import io.to.offscanneur.R

enum class NutrimentLevel {
    NUTRIMENT_LEVEL_LOW{
        fun getValue() = R.color.nutrient_level_low
        fun getText() = "en faible quantité"
    },
    NUTRIMENT_LEVEL_MODERATE{
        fun getValue() = R.color.nutrient_level_moderate
        fun getText() = "en quantité modéré"
    },
    NUTRIMENT_LEVEL_HIGH {
        fun getValue() = R.color.nutrient_level_high
        fun getText() = "en forte quantité"
    }
}