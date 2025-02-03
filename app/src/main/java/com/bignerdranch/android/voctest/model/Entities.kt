package com.bignerdranch.android.voctest.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @Embedded
    val categoryName: Category,
    val level: Int
)
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
    val categoryName: String
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
    fun getAllCategories(): List<Category>

    @Query("SELECT * FROM words")
    fun getAllWords(): List<Word>

    @Query("SELECT * FROM words WHERE categoryName LIKE '%' || :categoryName || '%'")
    suspend fun getWordsFromCategory(categoryName: String):List<Word>

    @Query("SELECT * FROM words WHERE level = :level AND categoryName LIKE '%' || :categoryName || '%'")
    fun getWordsFromLevelAndCategory(level: Int, categoryName: String):List<Word>
}