package com.ilhmdhn.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ilhmdhn.storyapp.data.lokal.StoryDatabase
import com.ilhmdhn.storyapp.data.remote.ApiService
import com.ilhmdhn.storyapp.util.DataDummy
import com.ilhmdhn.storyapp.view.viewmodel.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class AppRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var appRepository: AppRepository
    private lateinit var apiService: ApiService
    private lateinit var storyDatabase: StoryDatabase
    private val dummy = DataDummy

    @Before
    fun setUp() {
        appRepository = AppRepository(storyDatabase, apiService)
    }

    @Test
    fun postRegister() {
        val expectResponse = dummy.dummyResponseRegisterSuccess()
        `when`(apiService.postRegister("", "", "")).thenReturn(expectResponse) //ini merah soalnya pada apiservice return call<BaseResponse>
        val actualResponse = appRepository.postRegister("", "","")

        Mockito.verify(apiService).postRegister("", "", "")

        assertNotNull(actualResponse)
        assertEquals(expectResponse, actualResponse)
    }

    @Test
    fun postLogin() {
    }

    @Test
    fun getStory() {
    }

    @Test
    fun getStoryLocation() {
    }

    @Test
    fun postStory() {
    }

    @Test
    fun getDetailStory() {
    }
}