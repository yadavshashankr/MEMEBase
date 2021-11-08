package com.example.memebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import com.example.memebase.R
import com.example.memebase.viewModels.LoginActivityViewModel
import androidx.databinding.DataBindingUtil
import com.example.memebase.databinding.ActivityLoginBinding
import com.example.memebase.utils.Tools.Companion.toEditable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(
            this,
            R.layout.activity_login
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.liveUsrFnd.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }else{
                Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.liveUsrReg.observe(this) {
            if (it) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

            }
        }
        viewModel.regLog.observe(this) {
            if (it.equals("REGISTER")) {
                bringSignIn(binding)
            } else if (it.equals("LOGIN")) {
                bringSignUp(binding)
            }
        }
        viewModel.liveSubmitted.observe(this){
            if (it){
                binding.btnSubmit.setBackgroundColor(resources.getColor(android.R.color.white))
                binding.btnSubmit.setTextColor(resources.getColor(R.color.purple_700))
            }else{
                binding.btnSubmit.setBackgroundColor(resources.getColor(R.color.purple_700))
                binding.btnSubmit.setTextColor(resources.getColor(android.R.color.white))
            }
        }
    }

    private fun bringSignIn(binding: ActivityLoginBinding) {
        binding.llRegister.animate()?.translationX(-1000f)?.duration = 1000
        binding.llSignIn.animate()?.translationX(0f)?.duration = 1000
        binding.etUserId.text = "".toEditable()
        binding.etUserPassword.text = "".toEditable()
        binding.etUserName.text = "".toEditable()
        binding.tvRegSign.text = getString(R.string.signUp)
    }
    private fun bringSignUp(binding: ActivityLoginBinding) {
        binding.llRegister.animate()?.translationX(0f)?.duration = 1000
        binding.llSignIn.animate()?.translationX(1000f)?.duration = 1000
        binding.etUserIdSign.text = "".toEditable()
        binding.etUserPasswordSign.text = "".toEditable()
        binding.tvRegSign.text = getString(R.string.logIn)
    }
}