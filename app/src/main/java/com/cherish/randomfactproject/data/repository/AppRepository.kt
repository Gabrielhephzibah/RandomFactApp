package com.cherish.randomfactproject.data.repository

import com.cherish.randomfactproject.data.local.StoreDao
import com.cherish.randomfactproject.data.model.AddStoreResponse
import com.cherish.randomfactproject.data.model.StoreRequest
import com.cherish.randomfactproject.data.model.StoreResponse
import com.cherish.randomfactproject.data.remote.ApiService
import com.cherish.randomfactproject.data.remote.ResponseManager
import com.cherish.randomfactproject.utils.IODispatcher
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val apiService: ApiService, private val storeDao: StoreDao, @IODispatcher private val IODispatcher : CoroutineDispatcher) {
    fun getAllStoreItem() = flow{
        val response = apiService.getAllStoreItem()
        emit(ResponseManager.Success(response))
        insertItemToDb(response)
        emit(ResponseManager.Loading(false))
    }.flowOn(IODispatcher)
        .catch { emit(ResponseManager.Failure(it.message, it)) }
        .onStart { emit(ResponseManager.Loading(true)) }
        .onCompletion { emit(ResponseManager.Loading(false)) }


   suspend fun addNewItem(item : StoreRequest) = apiService.addStoreItem(item)
    suspend fun insertItemToDb(item : List<StoreResponse>) = storeDao.insertAllItemToDb(item)

    fun getAllItemFromDb() = flow {
       val response = storeDao.getAllItemFromDb()
       emit(ResponseManager.Success(response))
       emit(ResponseManager.Loading(false))
   }.flowOn(Dispatchers.IO)
       .catch { emit(ResponseManager.Failure(it.message, it)) }
       .onStart { emit(ResponseManager.Loading(true)) }
       .onCompletion { emit(ResponseManager.Loading(false)) }

//    fun getRepository(repositoryName: String) = flow{
//        val response =  apiService.getRepository(repositoryName)
//        emit(ResponseManager.Success(response))
//        emit(ResponseManager.Loading())
//    }.flowOn(Dispatchers.IO)
//        .catch {
//            emit(ResponseManager.Failure(it)) }
//        .onStart {
//            emit(ResponseManager.Loading())
//        }
//        .onCompletion {
//            emit(ResponseManager.Loading())
//        }
//
//    fun getRepositoryCommits(repositoryName: String) = flow{
//        val response =  apiService.getRepositoryCommits(repositoryName, AppConstant.QUERY_PARAMS)
//        emit(ResponseManager.Success(response))
//        emit(ResponseManager.Loading(false))
//    }.flowOn(Dispatchers.IO)
//        .catch {
//            emit(ResponseManager.Failure(it)) }
//        .onStart {
//            emit(ResponseManager.Loading(true))
//        }
//        .onCompletion {
//            emit(ResponseManager.Loading(false))
//        }


}

