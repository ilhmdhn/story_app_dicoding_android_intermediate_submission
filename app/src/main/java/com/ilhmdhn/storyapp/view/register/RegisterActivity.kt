package com.ilhmdhn.storyapp.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.storyapp.R
import com.ilhmdhn.storyapp.databinding.ActivityRegisterBinding
import com.ilhmdhn.storyapp.view.login.LoginActivity
import com.ilhmdhn.storyapp.view.viewmodel.AppViewModel
import com.ilhmdhn.storyapp.view.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val appViewModel: AppViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if (binding.edRegisterName.text.isNullOrEmpty()){
                Toast.makeText(this, resources.getString(R.string.invalid_name), Toast.LENGTH_SHORT).show()
            }else if (!binding.edRegisterEmail.error.isNullOrEmpty()){
                Toast.makeText(this, resources.getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
            }else if(!binding.edRegisterPassword.error.isNullOrEmpty()){
                Toast.makeText(this, resources.getString(R.string.invalid_password), Toast.LENGTH_SHORT).show()
            }else{
                InitRegister(binding.edRegisterName.text.toString(), binding.edRegisterEmail.text.toString(), binding.edRegisterPassword.text.toString())
            }
        }
    }

    private fun InitRegister(nama: String, email: String, password: String){
        appViewModel.postRegister(nama, email, password).observe(this, {responseRegister->
            if (responseRegister.error){
                Toast.makeText(this, responseRegister.message, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, responseRegister.message, Toast.LENGTH_SHORT).show()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })
    }
}