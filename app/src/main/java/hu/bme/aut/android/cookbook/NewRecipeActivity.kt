package hu.bme.aut.android.cookbook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cookbook.adapter.IngredientAdapter
import hu.bme.aut.android.cookbook.data.RecipeItem
import kotlinx.android.synthetic.main.activity_new_recipe_item.*

class NewRecipeActivity : AppCompatActivity() {

    private lateinit var ingrAdapter : IngredientAdapter
    private lateinit var ingrRecyclerView: RecyclerView
    private lateinit var ingredients: ArrayList<String>

    private lateinit var stepsAdapter : IngredientAdapter
    private lateinit var stepsRecyclerView: RecyclerView
    private lateinit var steps : ArrayList<String>

    private lateinit var recipeNameEditText : EditText
    private lateinit var categorySpinner : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recipe_item)

        btnCancel.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            ingrAdapter.saveItems()
            stepsAdapter.saveItems()
            ingredients = ingrAdapter.getItems()
            steps = stepsAdapter.getItems()
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
        recipeNameEditText = this.findViewById(R.id.recipe_name)
        categorySpinner = this.findViewById(R.id.spinnerCategory)
    }

    private fun getStringFromList(list: List<String>) : String {
        var returnString = ""
        for (stringItem in list) {
            returnString = returnString + stringItem +"\n"
        }

        return returnString
    }

    private fun getRecipeItem() = RecipeItem(
        id = null,
        name = recipeNameEditText.text.toString(),
        //category = RecipeItem.Category.getByOrdinal(categorySpinner.selectedItemPosition), //TODO szar
        category = RecipeItem.Category.OTHER,
        ingredients = getStringFromList(ingredients),
        description = getStringFromList(steps)
    )

}