package com.bignerdranch.android.voctest.data

import androidx.room.TypeConverter
import com.bignerdranch.android.voctest.model.Category
import com.bignerdranch.android.voctest.model.LanguageLevel

class Converters {

    @TypeConverter
    fun fromCategoryToString(category: Category): String {
        return category.categoryName
    }

    @TypeConverter
    fun fromStringToCategory(name: String):Category {
        return Category(categoryName = name)
    }

    @TypeConverter
    fun fromLevelToInt(level: LanguageLevel): Int {
        return level.ordinal
    }

    @TypeConverter
    fun fromIntToLevel(int: Int):LanguageLevel {
        return LanguageLevel.entries[int]
    }


}