package com.ilhmdhn.storyapp.data.remote

import com.ilhmdhn.storyapp.data.remote.response.BaseResponse
import com.ilhmdhn.storyapp.data.remote.response.DetailResponse
import com.ilhmdhn.storyapp.data.remote.response.LoginResponse
import com.ilhmdhn.storyapp.data.remote.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
    @Field("email") email: String,
    @Field("password") password: String
    ): Call<LoginResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") bearer: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<BaseResponse>

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") bearer: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 10
    ): StoryResponse

    @GET("stories/{id}")
    fun getDetailStory(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ):Call<DetailResponse>

    @GET("stories")
    fun getStoryMaps(
        @Header("Authorization") bearer: String,
        @Query("location")location: String = "1"
    ):Call<StoryResponse>
}