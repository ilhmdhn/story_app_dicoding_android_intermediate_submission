package com.ilhmdhn.storyapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ilhmdhn.storyapp.data.lokal.StoryDatabase
import com.ilhmdhn.storyapp.data.remote.ApiService
import com.ilhmdhn.storyapp.data.remote.response.ListStoryItem

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator (
    private val auth: String,
    private val database: StoryDatabase,
    private val apiService: ApiService
    ): RemoteMediator<Int, ListStoryItem>(){
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListStoryItem>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        try{
            val responseData = apiService.getStory("Bearer "+auth, page, state.config.pageSize)

            val endOfPaginationReached = responseData.listStory.isEmpty()

            database.withTransaction{
                if (loadType == LoadType.REFRESH){
                    database.storyDao().deleteAll()
                }
                database.storyDao().insertStory(responseData.listStory)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch(e: Exception){
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }
}