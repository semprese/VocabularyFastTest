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


data class HomeUiState(
    val stage: StageTest,
    val isLoading: Boolean,
    val listWords: List<Word> = emptyList()
)

enum class StageTest {
    HOME, FIRST, SECOND
}

class ExamViewModel(
    val mainRepository: MainRepository
):ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(StageTest.HOME, true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()




    fun loadList(){

    }

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


    fun getWords(category: Category){
        viewModelScope.launch {
             val w = mainRepository.getWord(category.categoryName)
            if(w.isNotEmpty()) {
                _uiState.update { HomeUiState(StageTest.HOME, false) }
            }
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