package com.bignerdranch.android.voctest.data

import androidx.room.TypeConverter
import com.bignerdranch.android.voctest.model.Category

class Converters {

    @TypeConverter
    fun fromCategoryToString(category: Category): String {
        return category.categoryName
    }

    @TypeConverter
    fun fromStringToCategory(name: String):Category {
        return Category(categoryName = name)
    }
}