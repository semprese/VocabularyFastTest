package com.bignerdranch.android.voctest.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val level: LanguageLevel,
    @Embedded
    val categoryName: Category = Category("")
)
enum class LanguageLevel {
    A1, A2, B1, B2, C1, C2
}

//
//@Fts4(contentEntity = Word::class)
//@Entity(tableName = "words_fts")
//data class WordFTS(
//    @PrimaryKey(autoGenerate = true)
//    val rowid: Int,
//    val name: String,
//    val categoryName: String,
//    val level: Int
//)


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val categoryName: String = ""
)

data class ExamWordState(
    val name: String,
    val state: MutableState<Boolean>
)
@Dao
interface WordDao {
    @Upsert
    suspend fun upsertWord(word: Word): Long

    @Delete
    suspend fun deleteWord(word: Word)

    @Upsert
    suspend fun upsertCategory(category: Category): Long

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT * FROM words WHERE level = :level ORDER BY RANDOM() LIMIT :count")
    suspend fun getWordsFromLevel(level: Int, count: Int):List<Word>

    @Query("SELECT * FROM words WHERE categoryName LIKE '%' || :categoryName || '%'")
    suspend fun getWordsFromCategory(categoryName: String):List<Word>

    @Query("SELECT * FROM words WHERE level = :level AND categoryName LIKE '%' || :categoryName || '%'")
    suspend fun getWordsFromLevelAndCategory(level: Int, categoryName: String):List<Word>
}