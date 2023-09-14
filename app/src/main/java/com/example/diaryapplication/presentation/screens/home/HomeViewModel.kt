package com.example.diaryapplication.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapplication.data.repository.Diaries
import com.example.diaryapplication.data.repository.MongoDB
import com.example.diaryapplication.util.RequestState
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var diaries : MutableState<Diaries> = mutableStateOf(RequestState.Idle)

    init {
        observeAllDiaries()
    }

    private fun observeAllDiaries(){
        viewModelScope.launch {
            MongoDB.getAllDiaries().collect{ result ->
                diaries.value = result
            }
        }
    }
}