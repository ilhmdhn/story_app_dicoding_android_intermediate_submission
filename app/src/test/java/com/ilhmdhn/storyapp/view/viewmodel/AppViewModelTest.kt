package com.ilhmdhn.storyapp.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.ilhmdhn.storyapp.data.AppRepository
import com.ilhmdhn.storyapp.data.remote.RemoteDataSource
import com.ilhmdhn.storyapp.data.remote.response.*
import com.ilhmdhn.storyapp.util.DataDummy
import com.ilhmdhn.storyapp.view.story.paging.StoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AppViewModelTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var appRepository: AppRepository
    @Mock
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var appViewModel: AppViewModel
    private val dummy = DataDummy

    @Before
    fun setUp(){
        appViewModel = AppViewModel(appRepository, remoteDataSource)
    }

    @Test
    fun ` test postLogin success`() {
        val expectedResponse = MutableLiveData<LoginResponse>()
        expectedResponse.value = dummy.dummyResponseLoginSuccess()

        `when`(remoteDataSource.postLogin("","")).thenReturn(expectedResponse)
        val actualResponse = appViewModel.postLogin("","").getOrAwaitValue()

        Mockito.verify(remoteDataSource).postLogin("","")
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponseLoginSuccess(), actualResponse)
    }

    @Test
    fun `test postRegister success`() {
        val expectedResponse = MutableLiveData<BaseResponse>()
        expectedResponse.value = dummy.dummyResponseRegisterSuccess()

        `when`(remoteDataSource.postRegister("", "", "")).thenReturn(expectedResponse)
        val actualResponse = appViewModel.postRegister("", "", "").getOrAwaitValue()

        Mockito.verify(remoteDataSource).postRegister("", "", "")

        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponseRegisterSuccess(), actualResponse)
    }

    @Test
    fun `test getStory Paging success`() = runTest {
        val dummyStory = DataDummy.dummyResponseGetStorySuccess()
        val data: PagingData<ListStoryItem> = StoryPagingSource.snapshot(dummyStory)
        val expectedResponse = MutableLiveData<PagingData<ListStoryItem>>()
        expectedResponse.value = data

        `when`(appRepository.getStory("")).thenReturn(expectedResponse)

        val actualResponse: PagingData<ListStoryItem> = appViewModel.getStory("").getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualResponse)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory, differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
    }

    @Test
    fun `test getStoryLocation success`() {
        val expectedResponse = MutableLiveData<StoryResponse>()
        expectedResponse.value = dummy.dummyResponseGetStoryLocationSuccess()

        `when`(remoteDataSource.getStoryLocation("")).thenReturn(expectedResponse)
        val actualResponse = appViewModel.getStoryLocation("").getOrAwaitValue()

        Mockito.verify(remoteDataSource).getStoryLocation("")

        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponseGetStoryLocationSuccess(), actualResponse)
    }

    @Test
    fun `test postStory success`() {
        val expectedResponse = MutableLiveData<BaseResponse>()
        expectedResponse.value = dummy.dummyResponsePostStorySuccess()

        `when`(remoteDataSource.postStory("", null, null)).thenReturn(expectedResponse)
        val actualResponse = appViewModel.postStory("", null, null).getOrAwaitValue()

        Mockito.verify(remoteDataSource).postStory("", null, null)

        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponsePostStorySuccess(), actualResponse)
    }

    @Test
    fun `test getDetailStory success`() {
        val expectedResponse = MutableLiveData<DetailResponse>()
        expectedResponse.value = dummy.dummyResponseGetDetailStorySuccess()

        `when`(remoteDataSource.getDetailStory("", "")).thenReturn(expectedResponse)
        val actualResponse = appViewModel.getDetailStory("", "").getOrAwaitValue()

        Mockito.verify(remoteDataSource).getDetailStory("", "")

        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponseGetDetailStorySuccess(), actualResponse)
    }

    @Test
    fun ` test postLogin failed`() {
        val expectedResponse = MutableLiveData<LoginResponse>()
        expectedResponse.value = dummy.dummyResponseLoginFailed()

        `when`(remoteDataSource.postLogin("","")).thenReturn(expectedResponse)
        val actualResponse = appViewModel.postLogin("","").getOrAwaitValue()

        Mockito.verify(remoteDataSource).postLogin("","")
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponseLoginFailed(), actualResponse)
    }

    @Test
    fun `test postRegister failed`() {
        val expectedResponse = MutableLiveData<BaseResponse>()
        expectedResponse.value = dummy.dummyResponseRegisterFailed()

        `when`(remoteDataSource.postRegister("", "", "")).thenReturn(expectedResponse)
        val actualResponse = appViewModel.postRegister("", "", "").getOrAwaitValue()

        Mockito.verify(remoteDataSource).postRegister("", "", "")

        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponseRegisterFailed(), actualResponse)
    }

    @Test
    fun `test getStory failed`() = runTest {
        val dummyStory = DataDummy.dummyResponseGetStoryFailed()
        val data: PagingData<ListStoryItem> = StoryPagingSource.snapshot(dummyStory)
        val expectedResponse = MutableLiveData<PagingData<ListStoryItem>>()
        expectedResponse.value = data

        `when`(appRepository.getStory("")).thenReturn(expectedResponse)

        val actualResponse: PagingData<ListStoryItem> = appViewModel.getStory("").getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualResponse)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory, differ.snapshot())
        Assert.assertTrue(dummyStory.size < 1)
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
    }

    @Test
    fun `test getStoryLocation failed`() {
        val expectedResponse = MutableLiveData<StoryResponse>()
        expectedResponse.value = dummy.dummyResponseGetStoryLocationFailed()

        `when`(remoteDataSource.getStoryLocation("")).thenReturn(expectedResponse)
        val actualResponse = appViewModel.getStoryLocation("").getOrAwaitValue()

        Mockito.verify(remoteDataSource).getStoryLocation("")

        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponseGetStoryLocationFailed(), actualResponse)
    }

    @Test
    fun `test postStory failed`() {
        val expectedResponse = MutableLiveData<BaseResponse>()
        expectedResponse.value = dummy.dummyResponsePostStoryFailed()

        `when`(remoteDataSource.postStory("", null, null)).thenReturn(expectedResponse)
        val actualResponse = appViewModel.postStory("", null, null).getOrAwaitValue()

        Mockito.verify(remoteDataSource).postStory("", null, null)

        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponsePostStoryFailed(), actualResponse)
    }

    @Test
    fun `test getDetailStory failed`() {
        val expectedResponse = MutableLiveData<DetailResponse>()
        expectedResponse.value = dummy.dummyResponseGetDetailStoryFailed()

        `when`(remoteDataSource.getDetailStory("", "")).thenReturn(expectedResponse)
        val actualResponse = appViewModel.getDetailStory("", "").getOrAwaitValue()

        Mockito.verify(remoteDataSource).getDetailStory("", "")

        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummy.dummyResponseGetDetailStoryFailed(), actualResponse)
    }
}


class StoryPagingSource : PagingSource<Int, LiveData<List<ListStoryItem>>>() {
    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}