package io.to.offscanneur.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.to.models.Ingredient
import io.to.models.Product
import io.to.offscanneur.R
import kotlinx.android.synthetic.main.product_detail_summary.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productList: Array<Product> = arrayOf(
            Product(
                "Petits pois et carottes : send data",
                "Cassegrain",
                "3541334646387434",
                "A",
                "http",
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
                arrayOf()
            ),
            Product(
                "Assiette de charcuterie",
                "Papa Pig",
                "3543854643414",
                "D",
                "http",
                "100 g",
                "1000 kCal/part",
                arrayOf(),
                arrayOf(),
                arrayOf(),
                arrayOf()
            ),
            Product(
                "Salade composée",
                "Géant vert",
                "3543854643414",
                "B",
                "http",
                "250 g",
                "150 kCal/part",
                arrayOf(),
                arrayOf(),
                arrayOf(),
                arrayOf()
            ),
        )

        view.findViewById<RecyclerView>(R.id.products_view).run {
            adapter = ListAdapterProduct(
                productList,
                listener = object : ItemClickListener {
                    override fun onItemClicked(position: Int) {
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