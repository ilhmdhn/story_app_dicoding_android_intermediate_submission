package com.ilhmdhn.storyapp.view.story

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhmdhn.storyapp.R
import com.ilhmdhn.storyapp.databinding.ActivityMainBinding
import com.ilhmdhn.storyapp.model.user.UserPreference
import com.ilhmdhn.storyapp.view.login.LoginActivity
import com.ilhmdhn.storyapp.view.mapstory.StoryMapsActivity
import com.ilhmdhn.storyapp.view.story.paging.LoadingStateAdapter
import com.ilhmdhn.storyapp.view.story.paging.StoryAdapter
import com.ilhmdhn.storyapp.view.viewmodel.AppViewModel
import com.ilhmdhn.storyapp.view.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }

        getStoryData()
    }

    private fun getStoryData(){

        val storyAdapter = StoryAdapter()
        with(binding.rvStory){
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)

            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
        }
        appViewModel.getStory(userPreference.getUser().token.toString()).observe(this,
            { dataStory ->
                Log.d("data main act", dataStory.toString())
                storyAdapter.submitData(lifecycle, dataStory)
            })
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            R.id.action_story_maps ->{
                startActivity(Intent(this, StoryMapsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        userPreference.delUser()
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}