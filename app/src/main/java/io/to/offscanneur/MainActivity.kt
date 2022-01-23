package io.to.offscanneur

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import io.to.offscanneur.fragment.ProductDetailsFragment
import io.to.offscanneur.fragment.ProductListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.toolbar))

//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setLogo(R.drawable.ic_qr_code)
//        supportActionBar?.setDisplayUseLogoEnabled(true)


    }


}