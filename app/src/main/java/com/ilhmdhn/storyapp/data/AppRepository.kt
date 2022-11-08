package com.ilhmdhn.storyapp.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ilhmdhn.storyapp.data.lokal.StoryDatabase
import com.ilhmdhn.storyapp.data.remote.ApiService
import com.ilhmdhn.storyapp.data.remote.StoryRemoteMediator
import com.ilhmdhn.storyapp.data.remote.response.*

class AppRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    fun getStory(auth: String): LiveData<PagingData<ListStoryItem>>{
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(auth, storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}