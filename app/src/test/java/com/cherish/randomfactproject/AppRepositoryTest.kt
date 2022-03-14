package com.cherish.randomfactproject

import app.cash.turbine.test
import com.cherish.randomfactproject.data.local.StoreDao
import com.cherish.randomfactproject.data.model.AddStoreResponse
import com.cherish.randomfactproject.data.model.Rating
import com.cherish.randomfactproject.data.model.StoreRequest
import com.cherish.randomfactproject.data.model.StoreResponse
import com.cherish.randomfactproject.data.remote.ApiService
import com.cherish.randomfactproject.data.remote.ResponseManager
import com.cherish.randomfactproject.data.repository.AppRepository
import com.cherish.randomfactproject.utils.TestUtils
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import org.mockito.kotlin.*


@ExperimentalCoroutinesApi
class AppRepositoryTest {
    lateinit var repository : AppRepository

    private val apiService = mock<ApiService>()

    private val storeDao = mock<StoreDao>()

    val testDispatcher = TestCoroutineScheduler()

    @Before
    fun setUp(){
        repository = AppRepository(apiService, storeDao)
    }

    @Test
    fun `test that getItem emit successfully `() = runBlocking{


        //make api request
      apiService.stub {
         onBlocking { getAllStoreItem() }.doReturn(TestUtils.list)
      }

       //test and verify

       repository.getAllStoreItem().test {
           assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(true)) //emit start
           assertThat(awaitItem()).isEqualTo(ResponseManager.Success(TestUtils.list)) // emit data
           assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(false)) //emit completion
           cancelAndIgnoreRemainingEvents()
       }

    }

    @Test(expected = Throwable::class)
    fun `test that getStoreRequest emits null on error`() = runBlocking  {

        //mock request
        apiService.stub {
            onBlocking {
               getAllStoreItem()
            }.doAnswer {
                throw  Throwable()
            }
        }

        //test
        repository.getAllStoreItem().test {
            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(true))
            assertThat(awaitItem()).isEqualTo(ResponseManager.Failure(null, Throwable()))
            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(false)) //emit completion
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test that addStoreItem returns Response`() = runBlocking {
        val request = StoreRequest("food","des","image",30.0,"cup")
        val response = AddStoreResponse("food","des",21,"image","34","cup")


        apiService.stub {
            onBlocking {
                addStoreItem(request)
            }.doReturn(response)
        }

        //verify and test
        val expected = repository.addNewItem(request)

        assertThat(expected).isEqualTo(response)
    }

    @Test
    fun `test that data is inserted into the db`() = runBlocking {
        val list = listOf(StoreResponse(
            1,
            "category",
            "description",
            2,"https://i.pravatar.cc",
            2.0,
            Rating(2,3.8),
            "test",
        ))

        repository.insertItemToDb(list)

        verify(storeDao, times(1)).insertAllItemToDb(list)
    }

    @Test
    fun `test that data is fetched from the db`() = runBlocking {

        //mock request
        whenever(storeDao.getAllItemFromDb()).doReturn(TestUtils.list)

        repository.getAllItemFromDb().test {
            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(true))//on start
            assertThat(awaitItem()).isEqualTo(ResponseManager.Success(TestUtils.list)) //on success
            assertThat(awaitItem()).isEqualTo(ResponseManager.Loading(false)) //oncompletion
            cancelAndIgnoreRemainingEvents()
        }
    }
}