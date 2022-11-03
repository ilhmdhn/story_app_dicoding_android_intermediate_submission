package com.ilhmdhn.storyapp.view.story

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.storyapp.R
import com.ilhmdhn.storyapp.databinding.ActivityAddStoryBinding
import com.ilhmdhn.storyapp.model.user.UserPreference
import com.ilhmdhn.storyapp.view.helper.CameraActivity
import com.ilhmdhn.storyapp.view.helper.Utils
import com.ilhmdhn.storyapp.view.helper.Utils.uriToFile
import com.ilhmdhn.storyapp.view.login.LoginActivity
import com.ilhmdhn.storyapp.view.viewmodel.AppViewModel
import com.ilhmdhn.storyapp.view.viewmodel.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding

    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory(this)
    }
    private var getFile: File? = null

    companion object{
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSION = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionGranted()){
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION, REQUEST_CODE_PERMISSION)
        }

        binding.btnUpload.setOnClickListener {
            if (getFile == null){
                Toast.makeText(this, resources.getString(R.string.put_image), Toast.LENGTH_SHORT).show()
            }
            else if (binding.edAddDescription.text.isNullOrEmpty()){
                Toast.makeText(this, resources.getString(R.string.put_description), Toast.LENGTH_SHORT).show()
            }else{
                uploadStory()
            }
        }

        binding.btnCamera.setOnClickListener {
            startCameraX()
        }

        binding.btnGallery.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "Choose a Picture")
            launcherIntentGalery.launch(chooser)
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == CAMERA_X_RESULT){
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile

            val result = Utils.rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.ivPhoto.setImageBitmap(result)
        }
    }

    private val launcherIntentGalery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){result ->
        if (result.resultCode == RESULT_OK){
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@AddStoryActivity)
            getFile = myFile
            binding.ivPhoto.setImageURI(selectedImg)
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSION.all{
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSION){
            if (!allPermissionGranted()){
                finish()
                Toast.makeText(this, resources.getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadStory() {

        val description = binding.edAddDescription.text.toString()
        val userPreference = UserPreference(this)

        val token = userPreference.getUser().token

        if (token.isNullOrEmpty()){
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }else{

            val file = Utils.reduceFileImage(getFile as File)

            val descriptionFix = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            appViewModel.postStory(token, imageMultipart, descriptionFix).observe(this, {uploadResponse ->
                    if (uploadResponse.error){
                        Toast.makeText(this, uploadResponse.message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, uploadResponse.message, Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
            })
        }
    }
}