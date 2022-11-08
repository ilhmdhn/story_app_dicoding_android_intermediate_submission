package com.ilhmdhn.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.ilhmdhn.storyapp.data.lokal.StoryDatabase
import com.ilhmdhn.storyapp.data.remote.ApiService
import com.ilhmdhn.storyapp.data.remote.response.*
import com.ilhmdhn.storyapp.util.DataDummy
import com.ilhmdhn.storyapp.view.story.DetailStoryActivity
import com.ilhmdhn.storyapp.view.story.paging.StoryAdapter
import com.ilhmdhn.storyapp.view.viewmodel.MainDispatcherRule
import com.ilhmdhn.storyapp.view.viewmodel.StoryPagingSource
import com.ilhmdhn.storyapp.view.viewmodel.getOrAwaitValue
import com.ilhmdhn.storyapp.view.viewmodel.noopListUpdateCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class AppRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var appRepository: AppRepository

    /*
    cuma bisa nerapin test begini pada repository, soalnya belum ketemu contoh test pada repository
    adanya test pada ViewModel dan RemoteMediator, jika ini salah tolong minta contoh bagaimana yang benar,
    terutama tipe data return pada class yang di mock
     */

//    @Before
//    fun setUp(){
//        appRepository = AppRepository(storyDatabase, apiService)
//    }

    private val dummy = DataDummy

    @Test
    fun getStory() = runTest{
        val dummyStory = dummy.dummyResponseGetStorySuccess()
        val data = StoryPagingSource.snapshot(dummyStory)
        val expectedResult = MutableLiveData<PagingData<ListStoryItem>>()
        expectedResult.value = data
        `when`(appRepository.getStory("")).thenReturn(expectedResult)

       val actualResponse: PagingData<ListStoryItem> = appRepository.getStory("").getOrAwaitValue()


        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualResponse)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory, differ.snapshot())
    }
}

