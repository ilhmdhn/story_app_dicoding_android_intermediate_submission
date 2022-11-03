package com.ilhmdhn.storyapp.view.mapstory

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ilhmdhn.storyapp.R
import com.ilhmdhn.storyapp.databinding.ActivityStoryMapsBinding
import com.ilhmdhn.storyapp.model.user.UserPreference
import com.ilhmdhn.storyapp.view.viewmodel.AppViewModel
import com.ilhmdhn.storyapp.view.viewmodel.ViewModelFactory


class StoryMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStoryMapsBinding
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory(this)
    }
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreference(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getData(){
        appViewModel.getStoryLocation(userPreference.getUser().token.toString()).observe(this, {dataMaps->
            if (dataMaps.error){
                Toast.makeText(this, dataMaps.message, Toast.LENGTH_SHORT).show()
            }else{
                dataMaps.listStory.forEach ({ listStoryItem ->
                    val latLng = LatLng(listStoryItem.lat, listStoryItem.lon)
                    mMap.addMarker(MarkerOptions().position(latLng).title(listStoryItem.name))
                })
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getData()
    }
}