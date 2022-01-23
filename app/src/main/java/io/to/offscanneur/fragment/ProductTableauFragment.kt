package io.to.offscanneur.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.to.models.Nutriment
import io.to.models.Product
import io.to.offscanneur.R
import io.to.offscanneur.utils.TableCellView

class ProductTableauFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.product_tableau,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailsFragment: ProductDetailsFragment =
            requireParentFragment().requireParentFragment() as ProductDetailsFragment
        val product: Product =
            ProductDetailsFragmentArgs.fromBundle(detailsFragment.requireArguments()).product

        view.findViewById<RecyclerView>(R.id.tableau_list).run {
            adapter = ListAdapterInfo(
                product.liste_nutriment
            )
            layoutManager = GridLayoutManager(requireContext(), 1)
        }

    }

    class ListAdapterInfo(val nutriments: Array<Nutriment>) : RecyclerView.Adapter<ListItemInfoTab>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemInfoTab {
            return ListItemInfoTab(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_tableau_item, parent, false)
            )
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ListItemInfoTab, position: Int) {
            holder.itemInfo.text = nutriments[position].nutriment
            if (nutriments[position].quantityReference.isNotEmpty()) {
                holder.item100g.text =
                    "${nutriments[position].quantityReference} ${nutriments[position].unit}"
            } else {
                holder.item100g.text = "?"
            }

            if (nutriments[position].quantityServing.isNotEmpty()) {
                holder.itemByPart.text =
                    "${nutriments[position].quantityServing} ${nutriments[position].unit}"
            } else {
                holder.itemByPart.text = "?"
            }
        }

        override fun getItemCount(): Int {
            return nutriments.size
        }

    }

    class ListItemInfoTab(v: View): RecyclerView.ViewHolder(v) {
        val itemInfo: TableCellView = v.findViewById<TableCellView>(R.id.col1)
        val item100g: TableCellView = v.findViewById<TableCellView>(R.id.col2)
        val itemByPart: TableCellView = v.findViewById<TableCellView>(R.id.col3)
    }
}