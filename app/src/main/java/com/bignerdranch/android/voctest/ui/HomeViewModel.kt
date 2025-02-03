package com.bignerdranch.android.voctest.ui

import androidx.lifecycle.ViewModel
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
    val isLoading: Boolean,
    val listCategory: List<Category> = emptyList()
)

class HomeViewModel(
    val mainRepository: MainRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(false))
    val HomeUiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            _uiState.update { it.copy(listCategory = mainRepository.getAllCategories(), isLoading = false) }
        }
    }


}