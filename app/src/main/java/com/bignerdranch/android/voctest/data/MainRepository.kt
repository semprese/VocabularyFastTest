package com.bignerdranch.android.voctest.data

import com.bignerdranch.android.voctest.model.Category
import com.bignerdranch.android.voctest.model.Word
import com.bignerdranch.android.voctest.model.WordDao


class MainRepository(
    private val wordDao: WordDao,
) {
    suspend fun insertWord(word: Word){
        wordDao.upsertWord(word)
    }
    suspend fun insertCategory(category: Category){
        wordDao.upsertCategory(category)
    }
    suspend fun getWord(category: String): List<Word>{
        return wordDao.getWordsFromCategory(category)
    }

}