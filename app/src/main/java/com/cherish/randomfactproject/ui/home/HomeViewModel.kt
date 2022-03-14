package com.cherish.randomfactproject.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.cherish.randomfactproject.data.model.AddStoreResponse
import com.cherish.randomfactproject.data.model.StoreRequest
import com.cherish.randomfactproject.data.model.StoreResponse
import com.cherish.randomfactproject.data.remote.ResponseManager
import com.cherish.randomfactproject.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val appRepository: AppRepository): ViewModel() {
    fun getAllStoreData() = appRepository.getAllStoreItem()
     fun  getDataFromDb()= appRepository.getAllItemFromDb()


    fun addStoreItem(item : StoreRequest) = liveData(Dispatchers.IO) {
            try {
                val response = appRepository.addNewItem(item)
                println("REQUEST SENT")
              emit(ResponseManager.Success(response))
                emit(ResponseManager.Loading(false))
            }catch (e : Throwable){
                println("VIEWMODEL ERROR")
                emit(ResponseManager.Failure(e.message, e))
                emit(ResponseManager.Loading(false))
            }
    }

}