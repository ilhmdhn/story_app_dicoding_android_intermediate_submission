package com.ilhmdhn.storyapp.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ilhmdhn.storyapp.data.remote.response.BaseResponse
import com.ilhmdhn.storyapp.data.remote.response.DetailResponse
import com.ilhmdhn.storyapp.data.remote.response.LoginResponse
import com.ilhmdhn.storyapp.data.remote.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    fun postRegister(name: String, email: String, password: String): LiveData<BaseResponse> {
        val dataResponse = MutableLiveData<BaseResponse>()
        val client = ApiConfig.getApiService().postRegister(name, email, password)
        client.enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful){
                    dataResponse.postValue(response.body())
                }else{
                    val gson = Gson()
                    val type = object : TypeToken<BaseResponse>() {}.type
                    val errorResponse: BaseResponse = gson.fromJson(response.errorBody()?.charStream(), type)
                    dataResponse.postValue(errorResponse)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                dataResponse.postValue(BaseResponse(true, t.message.toString()))
            }
        })
        return dataResponse
    }

    fun postLogin(email: String, password: String): LiveData<LoginResponse> {
        val dataResponse = MutableLiveData<LoginResponse>()
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("responnya", response.toString())
                if (response.isSuccessful){
                    dataResponse.postValue(response.body())
                }else{
                    val gson = Gson()
                    val type = object : TypeToken<LoginResponse>() {}.type
                    val errorResponse: LoginResponse = gson.fromJson(response.errorBody()?.charStream(), type)
                    dataResponse.postValue(errorResponse)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                dataResponse.postValue(LoginResponse(null, true, t.message.toString()))
            }
        })
        return dataResponse
    }

    fun getStoryLocation(auth: String): LiveData<StoryResponse> {
        val dataResponse = MutableLiveData<StoryResponse>()
        val client = ApiConfig.getApiService().getStoryMaps("Bearer "+auth)
        client.enqueue(object: Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful){
                    dataResponse.postValue(response.body())
                }else{
                    val gson = Gson()
                    val type = object : TypeToken<StoryResponse>() {}.type
                    val errorResponse: StoryResponse = gson.fromJson(response.errorBody()?.charStream(), type)
                    dataResponse.postValue(errorResponse)
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                dataResponse.postValue(StoryResponse(mutableListOf(), true, t.message.toString()))
            }
        })
        return dataResponse
    }

    fun postStory(auth: String, file: MultipartBody.Part?, description: RequestBody?): LiveData<BaseResponse> {
        val dataResponse = MutableLiveData<BaseResponse>()
        val client = ApiConfig.getApiService().postStory("Bearer "+auth, file, description)
        client.enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful){
                    dataResponse.postValue(response.body())
                }else{
                    val gson = Gson()
                    val type = object : TypeToken<BaseResponse>() {}.type
                    val errorResponse: BaseResponse = gson.fromJson(response.errorBody()?.charStream(), type)
                    dataResponse.postValue(errorResponse)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                dataResponse.postValue(BaseResponse(true, t.message.toString()))
            }
        })
        return dataResponse
    }

    fun getDetailStory(auth: String, id: String): LiveData<DetailResponse> {
        val dataResponse = MutableLiveData<DetailResponse>()
        val client = ApiConfig.getApiService().getDetailStory("Bearer "+ auth, id)
        client.enqueue(object: Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                if (response.isSuccessful){
                    dataResponse.postValue(response.body())
                }else{
                    val gson = Gson()
                    val type = object : TypeToken<DetailResponse>() {}.type
                    val errorResponse: DetailResponse = gson.fromJson(response.errorBody()?.charStream(), type)
                    dataResponse.postValue(errorResponse)
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                dataResponse.postValue(DetailResponse(true, t.message))
            }
        })
        return dataResponse
    }
}