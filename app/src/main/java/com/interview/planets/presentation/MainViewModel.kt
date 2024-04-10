package com.interview.planets.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(BaseUIState(isLoading = true))
    val uiState: StateFlow<BaseUIState> = _uiState

    /** Function allows user to update the base ui state with new value*/
    fun updateBaseUIState(baseUIState: BaseUIState) {
        _uiState.update {
            baseUIState
        }
    }

    data class BaseUIState(
        var isLoading: Boolean = false,
        var isError: Boolean = false
    )
}