package com.bignerdranch.android.voctest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.voctest.model.Category
import com.bignerdranch.android.voctest.model.Word
import com.bignerdranch.android.voctest.model.WordDao
//import com.bignerdranch.android.voctest.model.WordFTS

@Database(
    entities = [
        Word::class ,
//        WordFTS::class,
        Category::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class VocDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var Instance: VocDatabase? = null

        fun getDatabase(context: Context): VocDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    VocDatabase::class.java,
                    "words_database"
                )
                    .createFromAsset("database/wordsPreDatabase.db")
//                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}