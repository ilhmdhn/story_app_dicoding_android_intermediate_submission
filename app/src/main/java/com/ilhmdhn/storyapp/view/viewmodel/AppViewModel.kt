package com.ilhmdhn.storyapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.ilhmdhn.storyapp.data.remote.AppRepository
import com.ilhmdhn.storyapp.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AppViewModel(private val appRepository: AppRepository): ViewModel() {
    fun postLogin(email: String, password: String):LiveData<LoginResponse> =
        appRepository.postLogin(email, password)

    fun postRegister(name: String, email: String, password: String):LiveData<BaseResponse>{
        return appRepository.postRegister(name, email, password);
    }

    fun getStory(auth: String): LiveData<PagingData<ListStoryItem>>{
        return appRepository.getStory(auth)
    }

    fun getStoryLocation(auth: String): LiveData<StoryResponse>{
        return appRepository.getStoryLocation(auth)
    }

    fun postStory(auth: String, file: MultipartBody.Part, description: RequestBody):LiveData<BaseResponse>{
        return appRepository.postStory(auth, file, description)
    }

    fun getDetailStory(auth: String, id: String): LiveData<DetailResponse>{
        return appRepository.getDetailStory(auth, id)
    }
}