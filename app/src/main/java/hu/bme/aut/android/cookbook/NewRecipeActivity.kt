package hu.bme.aut.android.cookbook

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.cookbook.adapter.IngredientAdapter
import kotlinx.android.synthetic.main.activity_new_recipe_item.*

class NewRecipeActivity : AppCompatActivity() {

    private lateinit var ingrAdapter : IngredientAdapter
    private lateinit var ingrRecyclerView: RecyclerView
    private lateinit var ingredients: ArrayList<String>

    private lateinit var stepsAdapter : IngredientAdapter
    private lateinit var stepsRecyclerView: RecyclerView
    private lateinit var steps : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recipe_item)

        btnCancel.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            //TODO btnSave
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
            ingrAdapter = IngredientAdapter(this)
            ingrRecyclerView.layoutManager = LinearLayoutManager(this)
            ingrRecyclerView.adapter = ingrAdapter
        }
    }

    private fun initStepsRecycleView() {
        runOnUiThread {
            stepsRecyclerView = recyclerViewSteps
            stepsAdapter = IngredientAdapter(this)
            ingrRecyclerView.layoutManager = LinearLayoutManager(this)
            ingrRecyclerView.adapter = stepsAdapter
        }
    }

}