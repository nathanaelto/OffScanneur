package io.to.offscanneur.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.to.models.Nutriment
import io.to.models.NutrimentLevel
import io.to.models.Product
import io.to.offscanneur.R
import kotlin.math.absoluteValue

class ProductNutritionFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.product_nutrition,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Nutritions"

        val detailsFragment: ProductDetailsFragment =
            requireParentFragment().requireParentFragment() as ProductDetailsFragment
        val product: Product =
            ProductDetailsFragmentArgs.fromBundle(detailsFragment.requireArguments()).product

        Log.d("PRODUCT", product.name)


        view.findViewById<RecyclerView>(R.id.nutriment_list).run {
            adapter = ListAdapterNutriment(
                product.liste_nutriment,
                context
            )
            layoutManager = GridLayoutManager(requireContext(), 1)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


        }

    }

    class ListAdapterNutriment(
        val nutriments: Array<Nutriment>,
        val context: Context
    ) : RecyclerView.Adapter<ListItemNutriment>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemNutriment {
            return ListItemNutriment(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_nutrition_item, parent, false)
            )
        }

        @SuppressLint("UseCompatLoadingForColorStateLists")
        override fun onBindViewHolder(holder: ListItemNutriment, position: Int) {


            Log.d("PRODUCT", nutriments[position].formated())
            holder.item_nutriment.text = nutriments[position].formated()
            when(nutriments[position].level) {
                NutrimentLevel.NUTRIMENT_LEVEL_LOW -> {
                    holder.item_nutriment_level.text = "en faible quantité"
                    DrawableCompat.setTintList(holder.indicateur.background, context.resources.getColorStateList(R.color.nutrient_level_low))
                }
                NutrimentLevel.NUTRIMENT_LEVEL_MODERATE -> {
                    holder.item_nutriment_level.text = "en quantité modéré"
                    DrawableCompat.setTintList(holder.indicateur.background, context.resources.getColorStateList(R.color.nutrient_level_moderate))
                }
                NutrimentLevel.NUTRIMENT_LEVEL_HIGH -> {
                    holder.item_nutriment_level.text = "en forte quantité"
                    DrawableCompat.setTintList(holder.indicateur.background, context.resources.getColorStateList(R.color.nutrient_level_high))
                }
            }
        }

        override fun getItemCount(): Int {
            return nutriments.size
        }

    }


    class ListItemNutriment(v: View): RecyclerView.ViewHolder(v) {
        val item_nutriment = v.findViewById<TextView>(R.id.item_nutriment)
        val item_nutriment_level = v.findViewById<TextView>(R.id.item_nutriment_level)
        val indicateur = v.findViewById<View>(R.id.indicator)
    }
}