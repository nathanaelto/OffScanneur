package io.to.offscanneur.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import io.to.models.Product
import io.to.offscanneur.R

class ProductDetailSummaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.product_detail_summary,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Détails"

        val detailsFragment: ProductDetailsFragment =
            requireParentFragment().requireParentFragment() as ProductDetailsFragment
        val product: Product =
            ProductDetailsFragmentArgs.fromBundle(detailsFragment.requireArguments()).product

        Log.d("PRODUCT", product.marque)


        view.findViewById<TextView>(R.id.product_name).setMyStyle(product.name)

        view.findViewById<TextView>(R.id.product_brand).setMyStyle(product.marque)

        view.findViewById<TextView>(R.id.barcode).setBoldValue(
            getString(R.string.product_barcode, product.code_barres)
        )

        view.findViewById<TextView>(R.id.quantity).setBoldValue(
            getString(R.string.quantity, product.quantite)
        )

        view.findViewById<TextView>(R.id.sold_in).setBoldValue(
            getString(R.string.sold_in, product.paysFormat())
        )

        view.findViewById<TextView>(R.id.ingredients).setBoldValue(
            getString(R.string.ingredients, product.ingredientsFormat())
        )

        view.findViewById<TextView>(R.id.allergens).setBoldValue(
            getString(R.string.allergens, product.substancesFormat())
        )

        view.findViewById<TextView>(R.id.additives).setBoldValue(
            getString(R.string.additives, product.aditifsFormat())
        )

        initImageNutriScore(view, product.nutriscore)

        val url =
            "https://img.over-blog-kiwi.com/0/98/03/83/20151027/ob_d8e900_cfd0aef3e402736535ecc04b52b70c66-large.jpeg"
        val imageView = view.findViewById<ImageView>(R.id.placeholder)
        Picasso.get().load(url).into(imageView)
    }

    fun initImageNutriScore(v: View, nutriScore: String) {
        var imageView = v.findViewById<ImageView>(R.id.score_img)
        when (nutriScore) {
            "A" -> imageView.setImageResource(R.drawable.nutri_score_a)
            "B" -> imageView.setImageResource(R.drawable.nutri_score_b)
            "C" -> imageView.setImageResource(R.drawable.nutri_score_c)
            "D" -> imageView.setImageResource(R.drawable.nutri_score_d)
            "E" -> imageView.setImageResource(R.drawable.nutri_score_e)
            else -> Log.d("DETAILS", "Not set image because not fond nutriscore")
        }
    }

    fun TextView.setBoldValue(value: String) {
        val index = value.indexOf(':')
        val spannable = SpannableString(value)

        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, index, 0)
        setText(spannable)
    }

    fun TextView.setMyStyle(value: String) {
        val spannable = SpannableString(value)

        spannable.setSpan(StyleSpan(Typeface.BOLD_ITALIC), 0, value.length, 0)
        setText(spannable)
    }
}

