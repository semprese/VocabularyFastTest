package com.bignerdranch.android.voctest.func

import androidx.compose.runtime.LaunchedEffect
import com.bignerdranch.android.voctest.data.MainRepository
import com.bignerdranch.android.voctest.model.LanguageLevel
import com.bignerdranch.android.voctest.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//data class Word(val value: String, val level: LanguageLevel)

// Repository pattern for database operations
object WordRepository {
    fun getRandomWord(levelPool: LanguageLevel): Word {
        val word = ""//TODO("Get word count from SQL")

        return Word(name = word, level = levelPool)
    }

    fun count(levelPool: LanguageLevel): Int = TODO("Get word count from SQL")

    fun getRandomWords(
//        levelPool: LanguageLevel,
        requiredCount: Int,
        listWordsFromDatabase: (Int, Int) -> List<Word>,
    ): List<Word> {
//        val levelAsString: String = levelPool.name

        return TODO("SQL: SELECT word FROM words WHERE level = :levelAsString ORDER BY RANDOM() LIMIT :count")
    }
}

class Distribution private constructor(
    private val countByLevel: IntArray,
    private val knownCountByLevel: IntArray = IntArray(LanguageLevel.entries.size) { 0 },
) {
    operator fun get(level: LanguageLevel): Int = countByLevel[level.ordinal]

    fun markWordAsKnown(word: Word) {
        updateKnownCount(word.level.ordinal, 1)
    }

    fun markWordAsUnknown(word: Word) {
        updateKnownCount(word.level.ordinal, -1)
    }

    private fun updateKnownCount(levelOrdinal: Int, delta: Int) {
        knownCountByLevel[levelOrdinal] += delta
        require(knownCountByLevel[levelOrdinal] >= 0) { "Known words count cannot be negative" }
        require(knownCountByLevel[levelOrdinal] <= countByLevel[levelOrdinal]) { "Known words count cannot exceed preset count" }
    }

    fun createBasedOnAnswers(totalWords: Int): Distribution {
        val weights = IntArray(LanguageLevel.entries.size) { index ->
            val remaining = countByLevel[index] - knownCountByLevel[index]
            (remaining * 1000) / countByLevel[index].coerceAtLeast(1)
        }
        return create(weights, totalWords)
    }

    companion object {
        fun create(ratios: IntArray, totalWords: Int): Distribution {
            require(ratios.size == LanguageLevel.entries.size) {
                "Ratios array must match the number of language levels"
            }

            val countByLevel = calculateDistribution(ratios, totalWords)
            return Distribution(countByLevel)
        }

        private fun calculateDistribution(ratios: IntArray, totalWords: Int): IntArray {
            val totalRatio = ratios.sum()
            val baseCounts = IntArray(ratios.size) { index ->
                totalWords * ratios[index] / totalRatio
            }

            val remaining = totalWords - baseCounts.sum()
            baseCounts[LanguageLevel.B1.ordinal] += remaining
            return baseCounts
        }

    }
}


class WordGroup(
    val distribution: Distribution,
    val mainRepository: MainRepository,
) {
    private val allAppearedWords = mutableSetOf<String>()
    val words: Set<Word> by lazy { generateWordSet() }

    //////
    suspend fun getWordsFromDatabase(level: LanguageLevel, count: Int): List<Word> {
        return mainRepository.getWordsFromLevel(level = level.ordinal, count = count)
    }


    private fun generateWordSet(): Set<Word> {
        val result = linkedSetOf<Word>()

        LanguageLevel.entries.forEach { level ->
            var targetCount = minOf(distribution[level], WordRepository.count(level))

            while (targetCount-- > 0) {
                result.add(WordRepository.getRandomWord(level))
            }


//            val words = WordRepository.getRandomWords(level, targetCount)
//                .map { Word(it, level) }
//            result.addAll(words)
        }

        return result
    }

    fun createNextGroup(wordsNum: Int): WordGroup {
        val nextGroup = WordGroup(this.distribution.createBasedOnAnswers(wordsNum), mainRepository)
        nextGroup.allAppearedWords.addAll(allAppearedWords)
        return nextGroup
    }

    companion object {
        fun createInitial(mainRepository: MainRepository): WordGroup {
            val distribution = Distribution.create(
                ratios = intArrayOf(10, 20, 20, 20, 20, 10),
                totalWords = 60
            )
            return WordGroup(
                mainRepository = mainRepository,
                distribution = distribution
            )
        }

        fun createCustom(
            mainRepository: MainRepository,
            ratios: IntArray,
            totalWords: Int,
        ): WordGroup {
            val distribution = Distribution.create(ratios, totalWords)
            return WordGroup(
                mainRepository = mainRepository,
                distribution = distribution
            )
        }
    }
}