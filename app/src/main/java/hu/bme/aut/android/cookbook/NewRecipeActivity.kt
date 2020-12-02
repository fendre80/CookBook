package hu.bme.aut.android.cookbook

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cookbook.adapter.IngredientAdapter
import hu.bme.aut.android.cookbook.adapter.RecipeAdapter
import hu.bme.aut.android.cookbook.data.RecipeDatabase
import hu.bme.aut.android.cookbook.data.RecipeItem
import kotlinx.android.synthetic.main.activity_new_recipe_item.*
import kotlin.concurrent.thread

class NewRecipeActivity : AppCompatActivity() {

    private lateinit var ingrAdapter : IngredientAdapter
    private lateinit var ingrRecyclerView: RecyclerView
    private lateinit var ingredients: ArrayList<String>

    private lateinit var stepsAdapter : IngredientAdapter
    private lateinit var stepsRecyclerView: RecyclerView
    private lateinit var steps : ArrayList<String>

    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recipe_item)

        btnCancel.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            ingredients = ingrAdapter.getItems()
            steps = stepsAdapter.getItems()

            val ri = getRecipeItem()
            if (ri != null) {
                thread {
                    val newId = RecipeDatabase.getInstance(applicationContext).recipeDao().insert(ri)
                }
            }

            finish()
        }

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        initIngrRecycleView()
        initStepsRecycleView()

    }

    private fun initIngrRecycleView() {
        runOnUiThread {
            ingrRecyclerView = ingredients_list
            ingrAdapter = IngredientAdapter()
            ingrRecyclerView.adapter = ingrAdapter
        }
    }

    private fun initStepsRecycleView() {
        runOnUiThread {
            stepsRecyclerView = recyclerViewSteps
            stepsAdapter = IngredientAdapter()
            stepsRecyclerView.adapter = stepsAdapter
        }
    }

    //TODO ez nem biztos hogy kell
    private fun getContentView() {
    }

    private fun getStringFromList(list: List<String>) : String {
        var returnString = ""
        for (stringItem in list) {
            returnString = returnString + stringItem +"\n"
        }

        return returnString
    }

    private fun getRecipeItem() =
        RecipeItem.Category.getByOrdinal(spinnerCategory.selectedItemPosition)?.let {
            RecipeItem(
            id = null,
            name = recipe_name.text.toString(),
            category = it,
            ingredients = getStringFromList(ingredients),
            description = getStringFromList(steps)
        )
        }
}