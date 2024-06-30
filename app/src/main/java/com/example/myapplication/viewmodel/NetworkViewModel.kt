package com.example.myapplication.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DataStoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepo: DataStoreRepo
) : AndroidViewModel(application) {

    private val networkStatus = MutableStateFlow(false)


    var backOnline = false

    val readBackOnline: Flow<Boolean> = dataStoreRepo.readBackOnline

    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.saveBackOnline(backOnline)
        }

    fun showNetworkStatus() {
        if (!networkStatus.value) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus.value) {
            if (backOnline) {
                Toast.makeText(getApplication(), "Back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

    fun updateNetworkStatus(status: Boolean) {
        networkStatus.value = status
    }
}
