package com.cherish.randomfactproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cherish.randomfactproject.data.local.StoreDao

import com.cherish.randomfactproject.data.model.AddStoreResponse
import com.cherish.randomfactproject.data.model.StoreRequest
import com.cherish.randomfactproject.data.remote.ApiService
import com.cherish.randomfactproject.data.remote.ResponseManager
import com.cherish.randomfactproject.data.repository.AppRepository
import com.cherish.randomfactproject.ui.home.HomeViewModel
import com.cherish.randomfactproject.utils.MainCoroutineScopeRule
import com.cherish.randomfactproject.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.kotlin.*
import kotlin.math.exp

@ExperimentalCoroutinesApi
class AppViewModelTest {

    val apiService = mock<ApiService>()
    val storeDao = mock<StoreDao>()

   lateinit var repository : AppRepository
   lateinit var viewModel : HomeViewModel

    @get:Rule
    var rule  = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()


    @get:Rule
    var mainCoroutineRule = MainCoroutineScopeRule()


    val observer = mock<Observer<ResponseManager<AddStoreResponse>>>()




    @Before
   fun setUp(){
       repository = AppRepository(apiService, storeDao, dispatcher)
       viewModel = HomeViewModel(repository)
   }


    @Test
    fun `test that addtest emit properrly`() = runBlocking{
        val request = StoreRequest("food", "des", "image", 30.0, "cup")
        val response = AddStoreResponse("food", "des", 21, "image", "34", "cup")
       whenever(apiService.addStoreItem(request)).doReturn(response)
        val expected = viewModel.addStoreItem(request).getOrAwaitValue()
        assertThat(expected).isEqualTo(ResponseManager.Success(response))

    }

    @Test(expected = Throwable::class)
    fun `test that addtest emit error properrly`() = runBlocking{
        val request = StoreRequest("food", "des", "image", 30.0, "cup")
        val response = AddStoreResponse("food", "des", 21, "image", "34", "cup")
        whenever(apiService.addStoreItem(request)).doAnswer {
            throw  Throwable()
        }
        val expected = viewModel.addStoreItem(request).getOrAwaitValue()
        assertThat(expected).isEqualTo(ResponseManager.Failure(null, Throwable()))
        assertThat(expected).isEqualTo(ResponseManager.Loading(false))
    }
}