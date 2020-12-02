package hu.bme.aut.android.cookbook

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cookbook.adapter.MyListAdapter
import hu.bme.aut.android.cookbook.data.RecipeItem
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        tvRecipeName_details.text = intent.getStringExtra("RECIPE_NAME")
        val categoryIndex = intent.getIntExtra("CATEGORY", 4)
        tvcategory_details.text = "Category: " + RecipeItem.Category.getByOrdinal(categoryIndex).toString()
        var ingrAdapter = MyListAdapter(true)
        val ingrList = intent.getStringExtra("INGREDIENTS")?.split("\n")
//        Log.i("myintent", "INGREDIENTS" + intent.getStringExtra("INGREDIENTS").toString())
        if (ingrList != null) {
            ingrAdapter.setItems(ingrList)
        }
        rvIngredients_details.adapter = ingrAdapter
        var descAdapter = MyListAdapter(false)
        val descList = intent.getStringExtra("DESCRIPTION")?.split("\n")
//        Log.i("myintent", "DESCRIPTION" + intent.getStringExtra("DESCRIPTION").toString())
        if (descList != null) {
            descAdapter.setItems(descList)
        }
        rvDescription_details.adapter = descAdapter
    }
}