package com.ilhmdhn.storyapp.util

import com.ilhmdhn.storyapp.data.remote.response.*

object DataDummy {
    fun dummyResponseLoginSuccess(): LoginResponse{
        return LoginResponse(LoginResult("Ilham Dohaan", "ilhmdhn", "123"), false, "success")
    }

    fun dummyResponseLoginFailed(): LoginResponse{
        return LoginResponse(LoginResult("","",""), true, "login failed")
    }

    fun dummyResponseRegisterSuccess(): BaseResponse{
        return BaseResponse(false, "Register Success")
    }

    fun dummyResponseRegisterFailed(): BaseResponse{
        return BaseResponse(true, "Register Failed")
    }

    fun dummyResponseGetStorySuccess(): List<ListStoryItem>{
        return generateListStoryItem()
    }

    fun dummyResponseGetStoryFailed(): List<ListStoryItem>{
        return mutableListOf()
    }

    fun dummyResponseGetStoryLocationSuccess(): StoryResponse{
        return StoryResponse(generateListStoryItem(), false, "Get Story Location Failed")
    }

    fun dummyResponseGetStoryLocationFailed(): StoryResponse{
        return StoryResponse(mutableListOf(), true, "Get Story Failed")
    }

    fun dummyResponsePostStorySuccess(): BaseResponse{
        return BaseResponse(false, "Post Story Success")
    }

    fun dummyResponsePostStoryFailed(): BaseResponse{
        return BaseResponse(true, "Post Story Failed")
    }

    fun dummyResponseGetDetailStorySuccess(): DetailResponse{
        return DetailResponse(false, "Success get detail story", StoryDetail("google.com/image", "01-01-2001", "Name Story", "Dummy Detail Story", 1.1, "123", 2.2))
    }

    fun dummyResponseGetDetailStoryFailed(): DetailResponse{
        return DetailResponse(true, "Fail to get detail story", null)
    }

    fun generateListStoryItem(): List<ListStoryItem>{
        val storyList = ArrayList<ListStoryItem>()
        for(i in 0..100){
            val story = ListStoryItem(
                i,
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                "Story Name",
                "Description Story Dummy",
                0.1,
                "id dummy",
                1.1
            )
            storyList.add(story)
        }
        return storyList
    }
}