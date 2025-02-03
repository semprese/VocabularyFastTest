package com.bignerdranch.android.voctest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.voctest.data.MainRepository
import com.bignerdranch.android.voctest.model.Category
import com.bignerdranch.android.voctest.model.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ExamUiState(
    val stage: StageTest,
    val isLoading: Boolean,
    val listWords: List<Word> = emptyList()
)

enum class StageTest {
    HOME, FIRST, SECOND
}
/*
Level
    A1 : 0
    A2 : 1
    B1 : 2
    B2 : 3
    C1 : 4
    C2 : 5
 */
class ExamViewModel(
    val mainRepository: MainRepository
):ViewModel() {

    private val _uiState = MutableStateFlow(ExamUiState(StageTest.HOME, false))
    val ExamUiState: StateFlow<ExamUiState> = _uiState.asStateFlow()

    var listCategories = emptyList<Category>()

    init {
        viewModelScope.launch {
            listCategories = mainRepository.getAllCategories()
        }
    }



//Temp methods
    fun insertWord(word: Word){
        viewModelScope.launch {
            mainRepository.insertWord(word)
        }
    }
    fun insertCategory(category: Category){
        viewModelScope.launch {
            mainRepository.insertCategory(category)
        }
    }
    fun deleteWord(word: Word){
        viewModelScope.launch {
            mainRepository.deleteWord(word)
        }
    }
    fun deleteCategory(category: Category){
        viewModelScope.launch {
            mainRepository.deleteCategory(category)
        }
    }


    fun getAllWords(){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val tList = mainRepository.getAllWords()
            _uiState.update { it.copy(isLoading = false, listWords = tList) }
        }
    }
    fun getAllWordsFromCategory(category: Category){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val tList = mainRepository.getWordsFromCategory(category)
            _uiState.update { it.copy(isLoading = false, listWords = tList) }
        }
    }
    fun getWordsFromLevelAndCategory(level:Int, category: Category){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val tList = mainRepository.getWordsFromLevelAndCategory(level, category)
            _uiState.update { it.copy(isLoading = false, listWords = tList) }
        }
    }



    companion object {
        fun provideFactory(
            repository: MainRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ExamViewModel(
                    repository,
                ) as T
            }
        }
    }
}