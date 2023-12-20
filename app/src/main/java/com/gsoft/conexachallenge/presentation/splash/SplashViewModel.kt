package com.gsoft.conexachallenge.presentation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsoft.conexachallenge.util.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(SplashState())
    val state: State<SplashState> = _state

    init {
        navigateToHome()
    }

    private fun navigateToHome(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(2000)
            _state.value = _state.value.copy(navigate = true)
        }
    }



}
