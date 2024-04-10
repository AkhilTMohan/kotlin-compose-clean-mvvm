package com.interview.planets.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.interview.planets.data.models.Planet
import com.interview.planets.domain.PlanetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: PlanetUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BaseUIState(isLoading = true))
    val uiState: StateFlow<BaseUIState> = _uiState

    /** Function allows user to update the base ui state with new value*/
    fun updateBaseUIState(baseUIState: BaseUIState) {
        _uiState.update {
            baseUIState
        }
    }

    fun getPlanets() {
        val pageFlow = useCase.getPlanets()
        _uiState.update {
            it.copy(
                planets = pageFlow
            )
        }
    }

    data class BaseUIState(
        var isLoading: Boolean = false,
        var isError: Boolean = false,
        val planets: Flow<PagingData<Planet>>? = null
    )
}