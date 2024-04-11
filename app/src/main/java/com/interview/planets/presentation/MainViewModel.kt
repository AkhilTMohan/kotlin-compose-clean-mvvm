package com.interview.planets.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.interview.planets.data.models.Planet
import com.interview.planets.data.network.Response
import com.interview.planets.domain.PlanetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        val pageFlow = useCase.getPlanets().cachedIn(viewModelScope)
        _uiState.update {
            it.copy(
                planets = pageFlow
            )
        }
    }

    fun fetchPlanetDataFromServer(planet: Planet?) {
        viewModelScope.launch {
            val planetResponse:Response<Planet> = useCase.fetchPlanetDataFromServer(planet)
            when (planetResponse) {
                is Response.Success -> {
                    updateBaseUIState(
                        _uiState.value.copy(
                            planetData = planetResponse.data,
                        )
                    )
                }

                is Response.Error -> {
                    updateBaseUIState(
                        _uiState.value.copy(
                            planetData = planet,
                            isError = planetResponse.type,
                        )
                    )
                }
            }
        }
    }

    data class BaseUIState(
        var isLoading: Boolean = false,
        var isError: Response.ErrorTypes? = null,
        var planetData: Planet? = null,
        val planets: Flow<PagingData<Planet>>? = null,
        var data: Any? = null
    )
}