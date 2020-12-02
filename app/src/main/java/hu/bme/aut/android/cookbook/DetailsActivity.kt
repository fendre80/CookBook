package hu.bme.aut.android.cookbook

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cookbook.adapter.MyListAdapter
import hu.bme.aut.android.cookbook.data.RecipeDatabase
import hu.bme.aut.android.cookbook.data.RecipeItem
import kotlinx.android.synthetic.main.activity_details.*
import kotlin.concurrent.thread

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        init()

        btnDelete.setOnClickListener {
            thread {
                val item = RecipeDatabase.getInstance(this).recipeDao().getRecipe(intent.getLongExtra("ID",0))
                Log.i("myitem", item.name)
                RecipeDatabase.getInstance(this).recipeDao().deleteItem(item)
            }
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        tvRecipeName_details.text = intent.getStringExtra("RECIPE_NAME")
        val categoryIndex = intent.getIntExtra("CATEGORY", 4)
        tvcategory_details.text = "Category: " + RecipeItem.Category.getByOrdinal(categoryIndex).toString()
        val ingrAdapter = MyListAdapter(true)
        val ingrList = intent.getStringExtra("INGREDIENTS")?.split("\n") as ArrayList<String>
        ingrList.removeAt(ingrList.size - 1)
        ingrAdapter.setItems(ingrList)
        rvIngredients_details.adapter = ingrAdapter
        rvIngredients_details.layoutManager = LinearLayoutManager(this)
        val descAdapter = MyListAdapter(false)
        val descList = intent.getStringExtra("DESCRIPTION")?.split("\n") as ArrayList<String>
        descList.removeAt(descList.size - 1)
        descAdapter.setItems(descList)
        rvDescription_details.adapter = descAdapter
        rvDescription_details.layoutManager = LinearLayoutManager(this)

    }
}