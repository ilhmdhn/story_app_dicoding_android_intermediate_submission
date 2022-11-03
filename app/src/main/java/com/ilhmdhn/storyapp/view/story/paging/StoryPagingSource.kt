package com.ilhmdhn.storyapp.view.story.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilhmdhn.storyapp.data.remote.ApiService
import com.ilhmdhn.storyapp.data.remote.response.ListStoryItem

//class StoryPagingSource(private val apiService: ApiService, val auth: String):PagingSource<Int, ListStoryItem>() {
//    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
//        return state.anchorPosition?.let {anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?:anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
//        return try{
//            val position = params.key ?: INITIAL_PAGE_INDEX
//
//           val getData = apiService.getStory("Bearer "+auth, position).listStory
//
//            LoadResult.Page(
//                data = getData,
//                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
//                nextKey = if(getData.isEmpty()) null else position +1
//            )
//        }catch(e: Exception){
//            return LoadResult.Error(e)
//        }
//    }
//
//    private companion object{
//        const val INITIAL_PAGE_INDEX = 1
//    }
//}