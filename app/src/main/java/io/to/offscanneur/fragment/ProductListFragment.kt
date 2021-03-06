package io.to.offscanneur.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.to.models.Ingredient
import io.to.models.Nutriment
import io.to.models.NutrimentLevel
import io.to.models.Product
import io.to.offscanneur.R

class ProductListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.product_list_fragment,
            container,
            false
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && data != null) {

            val barcode_type = data.getStringExtra("SCAN_RESULT_FORMAT")
            val barcode_value = data.getStringExtra("SCAN_RESULT")

            val toast = Toast.makeText(requireContext(), "$barcode_type : $barcode_value", Toast.LENGTH_LONG);
            toast.show()

            view?.findViewById<TextView>(R.id.scan_result)?.text = "$barcode_type : $barcode_value"
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productList: Array<Product> = arrayOf(
            Product(
                "Petits pois et carottes : send data",
                "Cassegrain",
                "3541334646387434",
                "A",
                "https://media.istockphoto.com/photos/peas-and-baby-carrot-with-mint-parsley-dressing-picture-id686689994?s=612x612",
                "400 g",
                "234 kCal/part",
                arrayOf("France", "Espagne"),
                arrayOf(
                    Ingredient(
                        "Petits pois",
                        66.0,
                        null
                    ),
                    Ingredient(
                        "eau",
                        null,
                        null
                    ),
                    Ingredient(
                        "garniture",
                        2.8,
                        "salede, oignon grelot"
                    )
                ),
                arrayOf(),
                arrayOf(),
                arrayOf(
                    Nutriment(
                        "0.5",
                        "1",
                        "sel",
                        NutrimentLevel.NUTRIMENT_LEVEL_LOW,
                        "g"
                    ),
                    Nutriment(
                        "0.25",
                        "05",
                        "poivre",
                        NutrimentLevel.NUTRIMENT_LEVEL_LOW,
                        "g"
                    ),
                    Nutriment(
                        "0.5",
                        "2.3",
                        "acides gras satur??s",
                        NutrimentLevel.NUTRIMENT_LEVEL_MODERATE,
                        "g"
                    ),
                    Nutriment(
                        "10",
                        "5.2",
                        "sucre",
                        NutrimentLevel.NUTRIMENT_LEVEL_HIGH,
                        "g"
                    )
                )
            ),
            Product(
                "Assiette de charcuterie",
                "Papa Pig",
                "3543854643414",
                "D",
                "https://media.istockphoto.com/photos/meat-appetizer-platter-with-sausage-and-cold-cuts-above-view-on-a-picture-id1186418590?s=612x612",
                "100 g",
                "1000 kCal/part",
                arrayOf(),
                arrayOf(),
                arrayOf(),
                arrayOf(),
                arrayOf()
            ),
            Product(
                "Salade compos??e",
                "G??ant vert",
                "3543854643414",
                "B",
                "https://media.istockphoto.com/photos/breakfast-boiled-egg-and-vegetables-picture-id1194610986",
                "250 g",
                "150 kCal/part",
                arrayOf(),
                arrayOf(),
                arrayOf(),
                arrayOf(),
                arrayOf()
            ),
        )

        view.findViewById<Button>(R.id.products_start_scan).setOnClickListener {

            val scanner = Intent("com.google.zxing.client.android.SCAN")
            scanner.putExtra("SCAN_FORMATS ", "EAN_13")
            startActivityForResult(scanner, 100)

        }

        view.findViewById<RecyclerView>(R.id.products_view).run {
            adapter = ListAdapterProduct(
                productList,
                listener = object : ItemClickListener {
                    override fun onItemClicked(position: Int) {
                        Log.d("PRODUCT", position.toString())
                        findNavController().navigate(
                            ProductListFragmentDirections.actionListProductFragmentToProductDetailsFragment(
                                productList[position]
                            )
                        )
                    }

                }
            )
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }


    class ListAdapterProduct(
        val products: Array<Product>,
        val listener: ItemClickListener
    ) : RecyclerView.Adapter<ListItemProduct>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemProduct {
            return ListItemProduct(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ListItemProduct, position: Int) {
            holder.product_name.text = products[position].name
            holder.product_brand.text = products[position].marque
            holder.nutri_score.text = "Nutriscore : ${products[position].nutriscore}"
            holder.calories.text = products[position].caloris

            holder.itemView.setOnClickListener {
                listener.onItemClicked(position)
            }

            Picasso.get().load(products[position].url).into(holder.img_product_item)
        }

        override fun getItemCount(): Int {
            return products.size
        }

    }

    class ListItemProduct(v: View) : RecyclerView.ViewHolder(v) {
        val img_product_item = v.findViewById<ImageView>(R.id.img_product_item)
        val product_name = v.findViewById<TextView>(R.id.product_name)
        val product_brand = v.findViewById<TextView>(R.id.product_brand)
        val nutri_score = v.findViewById<TextView>(R.id.nutri_score)
        val calories = v.findViewById<TextView>(R.id.calories)
    }

    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }
}