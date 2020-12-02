package hu.bme.aut.android.cookbook.data

import androidx.room.*

@Dao
interface RecipeDAO {
    @Query("SELECT * FROM receptitem")
    fun getAll(): List<RecipeItem>

    @Insert
    fun insert(receptItem: RecipeItem): Long

    @Update
    fun update(receptItem: RecipeItem)

    @Delete
    fun deleteItem(receptItem: RecipeItem)
}