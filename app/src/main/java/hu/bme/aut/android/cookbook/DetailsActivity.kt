package hu.bme.aut.android.cookbook

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                RecipeDatabase.getInstance(this).recipeDao().deleteItem(item)
            }
            finish()
        }

        fab_edit.setOnClickListener {
            thread {
                val recipe = RecipeDatabase.getInstance(this).recipeDao().getRecipe(intent.getLongExtra("ID",0))
                val intent = Intent(this, NewRecipeActivity::class.java)
                runOnUiThread {
                    intent.putExtra("ID", recipe.id)
                    intent.putExtra("RECIPE_NAME",recipe.name)
                    intent.putExtra("CATEGORY", RecipeItem.Category.toInt(recipe.category))
                    intent.putExtra("INGREDIENTS", recipe.ingredients)
                    intent.putExtra("DESCRIPTION", recipe.description)
                    intent.putExtra("EDIT", true)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun init() {
        tvRecipeName_details.text = intent.getStringExtra("RECIPE_NAME")
        val categoryIndex = intent.getIntExtra("CATEGORY", 4)
        tvcategory_details.text = "Category: " + RecipeItem.Category.getByOrdinal(categoryIndex).toString()

        val ingrAdapter = MyListAdapter(true)
        var ingrList = ArrayList<String>()
        if (intent.getStringExtra("INGREDIENTS")?.split("\t")?.size == 1) {
            ingrList.add(intent.getStringExtra("INGREDIENTS")!!)
        }
        else
            ingrList = intent.getStringExtra("INGREDIENTS")?.split("\t") as ArrayList<String>
        ingrAdapter.setItems(ingrList)
        rvIngredients_details.adapter = ingrAdapter
        rvIngredients_details.layoutManager = LinearLayoutManager(this)

        val descAdapter = MyListAdapter(false)
        var descList = ArrayList<String>()
        if (intent.getStringExtra("DESCRIPTION")?.split("\t")?.size == 1) {
            descList.add(intent.getStringExtra("DESCRIPTION")!!)
        }
        else
            descList = intent.getStringExtra("DESCRIPTION")?.split("\t") as ArrayList<String>
        descAdapter.setItems(descList)
        rvDescription_details.adapter = descAdapter
        rvDescription_details.layoutManager = LinearLayoutManager(this)

    }
}