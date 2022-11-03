package com.ilhmdhn.storyapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ilhmdhn.storyapp.view.story.MainActivity
import com.ilhmdhn.storyapp.R
import com.ilhmdhn.storyapp.databinding.ActivityLoginBinding
import com.ilhmdhn.storyapp.model.user.UserPreference
import com.ilhmdhn.storyapp.view.register.RegisterActivity
import com.ilhmdhn.storyapp.view.viewmodel.AppViewModel
import com.ilhmdhn.storyapp.view.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

        private lateinit var binding: ActivityLoginBinding
        private lateinit var userPreference: UserPreference
    val appViewModel: AppViewModel by viewModels {
        ViewModelFactory(this)
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            userPreference = UserPreference(this)

            checkLogin()

            binding.tvRegister.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }

            binding.btnLogin.setOnClickListener {
                if (!binding.edRegisterEmail.error.isNullOrEmpty()) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.invalid_email),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (!binding.edRegisterPassword.error.isNullOrEmpty()) {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.invalid_password),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    initLogin(binding.edRegisterEmail.text.toString(), binding.edRegisterPassword.text.toString())
                }
            }

        }
        private fun initLogin(email: String, password: String) {
            appViewModel.postLogin(email, password).observe(this, { dataLogin ->
                if (dataLogin.error == true) {
                    Toast.makeText(applicationContext, dataLogin.message, Toast.LENGTH_SHORT).show()
                } else {
                    dataLogin.loginResult?.let {
                        userPreference.setUser(it)
                    }
                    finish()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            })
        }

        private fun checkLogin() {
            if (userPreference.getUser().isLogin) {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
