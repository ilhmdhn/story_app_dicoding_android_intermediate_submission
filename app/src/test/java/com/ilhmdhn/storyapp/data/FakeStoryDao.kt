package com.ilhmdhn.storyapp.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.ilhmdhn.storyapp.data.lokal.StoryDao
import com.ilhmdhn.storyapp.data.remote.response.ListStoryItem
import com.ilhmdhn.storyapp.view.viewmodel.StoryPagingSource

class FakeStoryDao :StoryDao {

    private var storyData = mutableListOf<ListStoryItem>()

    override suspend fun insertStory(story: List<ListStoryItem>) {
        storyData.addAll(story)
    }

    override fun getAllStory(): PagingSource<Int, ListStoryItem> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        storyData.clear()
    }
}