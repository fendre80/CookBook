package hu.bme.aut.android.cookbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "recipetitem")
data class RecipeItem (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: Category,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "description") val description: String
) {
    enum class Category {
        STARTER, SOUP, MAIN_COURSE, DESSERT, OTHER;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Category? {
                return values().find{it.ordinal == ordinal}
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: Category): Int {
                return category.ordinal
            }

        }

    }
}