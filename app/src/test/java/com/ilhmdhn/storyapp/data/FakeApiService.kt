package com.ilhmdhn.storyapp.data

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ilhmdhn.storyapp.data.remote.response.*
import com.ilhmdhn.storyapp.util.DataDummy
import retrofit2.Call
import retrofit2.http.GET


abstract class FakeApiService{

     val dummyResponse = DataDummy

    fun postRegister(){
    }

    fun postLogin():LoginResponse{
        return dummyResponse.dummyResponseLoginSuccess()
    }

    fun postStory(): BaseResponse {
        return dummyResponse.dummyResponsePostStorySuccess()
    }

    fun getStory(): List<ListStoryItem> {
        return dummyResponse.dummyResponseGetStorySuccess()
    }

    fun getDetailStory(): DetailResponse {
        return dummyResponse.dummyResponseGetDetailStorySuccess()
    }

    fun getStoryMaps(): StoryResponse {
        return dummyResponse.dummyResponseGetStoryLocationSuccess()
    }
}