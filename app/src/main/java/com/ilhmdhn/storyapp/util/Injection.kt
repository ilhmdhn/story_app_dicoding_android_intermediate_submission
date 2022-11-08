package com.ilhmdhn.storyapp.util

import android.content.Context
import com.ilhmdhn.storyapp.data.lokal.StoryDatabase
import com.ilhmdhn.storyapp.data.remote.ApiConfig
import com.ilhmdhn.storyapp.data.AppRepository
import com.ilhmdhn.storyapp.data.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): AppRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return AppRepository(database, apiService)
    }
}