package com.ilhmdhn.storyapp

import android.content.Context
import com.ilhmdhn.storyapp.data.lokal.StoryDatabase
import com.ilhmdhn.storyapp.data.remote.ApiConfig
import com.ilhmdhn.storyapp.data.remote.AppRepository

object Injection {
    fun provideRepository(context: Context): AppRepository{
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return AppRepository(database, apiService)
    }
}