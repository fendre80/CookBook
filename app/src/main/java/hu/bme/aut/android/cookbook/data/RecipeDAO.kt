package hu.bme.aut.android.cookbook.data

import androidx.room.*

@Dao
interface RecipeDAO {
    @Query("SELECT * FROM recipetitem")
    fun getAll(): List<RecipeItem>

    @Query("SELECT name FROM recipetitem")
    fun getAllNames() : List<String>

    @Insert
    fun insert(recipeItem: RecipeItem): Long

    @Update
    fun update(vararg recipeItem: RecipeItem)

    @Delete
    fun deleteItem(recipeItem: RecipeItem)
}