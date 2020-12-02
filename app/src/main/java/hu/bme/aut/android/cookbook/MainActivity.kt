package hu.bme.aut.android.cookbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import hu.bme.aut.android.cookbook.adapter.RecipeAdapter
import hu.bme.aut.android.cookbook.data.RecipeDatabase
import hu.bme.aut.android.cookbook.data.RecipeItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recipelist.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), RecipeAdapter.RecipeItemClickListener {

//    private lateinit var database: RecipeDatabase
    private lateinit var adapter: RecipeAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.topAppBar))


        fab.setOnClickListener {
            val intent = Intent(this, NewRecipeActivity::class.java)
            startActivity(intent)
        }

        initRecycleView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val item = menu?.findItem(R.id.app_bar_search)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("onQueryTextChange", "query: " + newText)
                adapter.filter.filter(newText)
                return true
            }
        })
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                adapter.filter.filter("")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        loadItemsInBackground()
        super.onResume()
    }

    private fun initRecycleView() {
        recyclerView = MainRecyclerView
        adapter = RecipeAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    private fun loadItemsInBackground() {
        thread {
            val items = RecipeDatabase.getInstance(this).recipeDao().getAll()
            runOnUiThread {
                adapter.updateRecipeItem(items)
            }
        }
    }

    override fun onItemClicked(item: RecipeItem) {
        thread {
            val recipe = RecipeDatabase.getInstance(this).recipeDao().getRecipe(item.id)
                val intent = Intent(this, DetailsActivity::class.java)
            runOnUiThread {
                intent.putExtra("ID", recipe.id)
                intent.putExtra("RECIPE_NAME",recipe.name)
                intent.putExtra("CATEGORY", RecipeItem.Category.toInt(recipe.category))
                intent.putExtra("INGREDIENTS", recipe.ingredients)
                intent.putExtra("DESCRIPTION", recipe.description)
                startActivity(intent)
            }
        }
    }

}