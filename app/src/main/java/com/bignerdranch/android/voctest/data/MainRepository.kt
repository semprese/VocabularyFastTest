package com.bignerdranch.android.voctest.data

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bignerdranch.android.voctest.model.Category
import com.bignerdranch.android.voctest.model.Word
import com.bignerdranch.android.voctest.model.WordDao


class MainRepository(
    private val wordDao: WordDao,
) {
    suspend fun insertWord(word: Word):Long{
        return wordDao.upsertWord(word)
    }
    suspend fun deleteWord(word: Word){
        return wordDao.deleteWord(word)
    }

    suspend fun insertCategory(category: Category):Long{
        return wordDao.upsertCategory(category)
    }
    suspend fun deleteCategory(category: Category){
        return wordDao.deleteCategory(category)
    }

    suspend fun getAllCategories(): List<Category>{
        return wordDao.getAllCategories()
    }
    suspend fun getAllWords(): List<Word>{
        return wordDao.getAllWords()
    }

    suspend fun getWordsFromCategory(category: Category): List<Word>{
        return wordDao.getWordsFromCategory(category.categoryName)
    }
    suspend fun getWordsFromLevelAndCategory(level: Int, category: Category):List<Word>{
        return wordDao.getWordsFromLevelAndCategory(level, categoryName = category.categoryName)
    }
}