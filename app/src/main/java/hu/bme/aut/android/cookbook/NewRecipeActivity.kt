package hu.bme.aut.android.cookbook

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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

    private lateinit var ingrAdapter: IngredientAdapter
    private lateinit var ingrRecyclerView: RecyclerView
    private lateinit var ingredients: ArrayList<String>

    private lateinit var stepsAdapter: IngredientAdapter
    private lateinit var stepsRecyclerView: RecyclerView
    private lateinit var steps: ArrayList<String>

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
                    RecipeDatabase.getInstance(applicationContext).recipeDao().insert(ri)
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

        if (intent.getBooleanExtra("EDIT", false)) {
            editInit()
            recipe_name.requestFocus()
            btnSave.setOnClickListener {
                ingredients = ingrAdapter.getItems()
                steps = stepsAdapter.getItems()

                val ri = getExistingRecipeItem()
                Log.d("recipe", ri.toString())
                if (ri != null) {
                    thread {
                        RecipeDatabase.getInstance(applicationContext).recipeDao().update(ri)
                    }
                }

                finish()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun editInit() {
        tvNewRecipe.text = "Edit recipe"
        recipe_name.setText(intent.getStringExtra("RECIPE_NAME"))
        spinnerCategory.setSelection(intent.getIntExtra("CATEGORY", 4))

        var ingrList = ArrayList<String>()
        if (intent.getStringExtra("INGREDIENTS")?.split("\t")?.size == 1)
            ingrList.add(intent.getStringExtra("INGREDIENTS")!!)
        else
            ingrList = intent.getStringExtra("INGREDIENTS")?.split("\t") as ArrayList<String>
        ingrAdapter.setItems(ingrList)

        var descList = ArrayList<String>()
        if (intent.getStringExtra("DESCRIPTION")?.split("\t")?.size == 1)
            descList.add(intent.getStringExtra("DESCRIPTION")!!)
        else
            descList = intent.getStringExtra("DESCRIPTION")?.split("\t") as ArrayList<String>
        stepsAdapter.setItems(descList)

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

    private fun getStringFromList(list: List<String>): String {
        var returnString = ""
        for (stringItem in list) {
            if (list.indexOf(stringItem) == list.size - 1)
                returnString = returnString + stringItem
            else
                returnString = returnString + stringItem + "\t"
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

    private fun getExistingRecipeItem() =
        RecipeItem.Category.getByOrdinal(spinnerCategory.selectedItemPosition)?.let {
            RecipeItem(
                id = intent.getLongExtra("ID", 0),
                name = recipe_name.text.toString(),
                category = it,
                ingredients = getStringFromList(ingredients),
                description = getStringFromList(steps)
            )
        }

}