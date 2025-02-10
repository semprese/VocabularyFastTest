package com.bignerdranch.android.voctest.func

import com.bignerdranch.android.voctest.data.MainRepository
import com.bignerdranch.android.voctest.model.Word
import kotlinx.coroutines.CoroutineScope

class WordManager (mainRepository: MainRepository, coroutineScope: CoroutineScope){
    private var currentGroup: WordGroup = WordGroup.createInitial(mainRepository)

    fun handleCheck(word: Word, isKnown: Boolean) {
        if (isKnown) currentGroup.distribution.markWordAsKnown(word)
        else currentGroup.distribution.markWordAsUnknown(word)
    }

    fun getWordList() = currentGroup.words

    fun moveToNextGroup() {
        currentGroup = currentGroup.createNextGroup(180)
    }
}