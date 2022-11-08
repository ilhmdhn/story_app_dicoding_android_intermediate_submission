package com.ilhmdhn.storyapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ilhmdhn.storyapp.data.AppRepository
import com.ilhmdhn.storyapp.data.remote.RemoteDataSource
import com.ilhmdhn.storyapp.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AppViewModel(private val appRepository: AppRepository, private val remoteDataSource: RemoteDataSource): ViewModel() {

    fun postLogin(email: String, password: String):LiveData<LoginResponse> =
        remoteDataSource.postLogin(email, password)

    fun postRegister(name: String, email: String, password: String):LiveData<BaseResponse>{
        return remoteDataSource.postRegister(name, email, password);
    }

    fun getStory(auth: String): LiveData<PagingData<ListStoryItem>>{
        return appRepository.getStory(auth).cachedIn(viewModelScope)
    }

    fun getStoryLocation(auth: String): LiveData<StoryResponse>{
        return remoteDataSource.getStoryLocation(auth)
    }

    fun postStory(auth: String, file: MultipartBody.Part?, description: RequestBody?):LiveData<BaseResponse>{
        return remoteDataSource.postStory(auth, file, description)
    }

    fun getDetailStory(auth: String, id: String): LiveData<DetailResponse>{
        return remoteDataSource.getDetailStory(auth, id)
    }
}