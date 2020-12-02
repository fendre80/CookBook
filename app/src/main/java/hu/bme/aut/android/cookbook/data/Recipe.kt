package hu.bme.aut.android.cookbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "receptitem")
data class RecipeItem (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "description") val description: String
) {
    enum class Category {
        MAIN_COURSE, DESSERT, LUNCH, OTHER;
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