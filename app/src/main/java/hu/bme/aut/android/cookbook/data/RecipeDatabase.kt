package hu.bme.aut.android.cookbook.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeItem::class], version = 2)
@TypeConverters(value = [RecipeItem.Category::class])
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDAO

    companion object {
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    RecipeDatabase::class.java, "recipe-list")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}