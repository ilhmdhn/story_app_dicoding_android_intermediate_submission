package com.ilhmdhn.storyapp.view.story

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ilhmdhn.storyapp.databinding.ActivityDetailStoryBinding
import com.ilhmdhn.storyapp.model.user.UserPreference
import com.ilhmdhn.storyapp.data.remote.response.ListStoryItem
import com.ilhmdhn.storyapp.view.viewmodel.AppViewModel
import com.ilhmdhn.storyapp.view.viewmodel.ViewModelFactory

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var user: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = UserPreference(this)
        val data = intent.getParcelableExtra<ListStoryItem>(DATA_DETAIL) as ListStoryItem

        getData(user.getUser().token.toString(), data.id)

    }

    private fun getData(auth: String, id: String){

        appViewModel.getDetailStory(auth, id).observe(this, {dataDetail->
            if (dataDetail.error == true){
                Toast.makeText(this, dataDetail.message, Toast.LENGTH_SHORT).show()
            }else{
                with(binding){
                    Glide.with(this@DetailStoryActivity)
                        .load(dataDetail.story?.photoUrl)
                        .into(binding.ivDetailPhoto)
                    tvDetailName.text = dataDetail.story?.name
                    tvDetailDescription.text = dataDetail.story?.description
                    if (dataDetail.story?.lat == null){
                        rowLat.visibility = View.INVISIBLE
                    }else{
                        tvLat.text = dataDetail.story.lat.toString()
                    }

                    if (dataDetail.story?.lon == null){
                        rowLong.visibility = View.INVISIBLE
                    }else{
                        tvLong.text = dataDetail.story.lon.toString()
                    }
                }
            }
        })
    }

    companion object{
        const val DATA_DETAIL = "data_detail"
    }
}