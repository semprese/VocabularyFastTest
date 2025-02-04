package com.bignerdranch.android.voctest.func

class WordManager {
    private var currentGroup: WordGroup = WordGroup.createInitial()

    fun handleCheck(word: Word, isKnown: Boolean) {
        if (isKnown) currentGroup.distribution.markWordAsKnown(word)
        else currentGroup.distribution.markWordAsUnknown(word)
    }

    fun moveToNextGroup() {
        currentGroup = currentGroup.createNextGroup(180)
    }
}