package com.shashank.memebase.ui


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shashank.memebase.R
import com.shashank.memebase.databinding.ActivityLoginBinding
import com.shashank.memebase.utils.Tools
import com.shashank.memebase.utils.Tools.Companion.shortToast
import com.shashank.memebase.utils.Tools.Companion.toEditable
import com.shashank.memebase.viewModels.LoginActivityViewModel
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

        if(viewModel.isUserPresent()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        viewModel.liveUsrFnd.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }else{
                shortToast(this, Tools.error)
            }
        }
        viewModel.liveUsrReg.observe(this) {
            if (it) {
                shortToast(this, "Success")
            }else{
                shortToast(this, Tools.error)
            }
        }
        viewModel.regLog.observe(this) {
            if (it.equals("REGISTER")) {
                bringSignIn(binding)
            } else if (it.equals("LOGIN")) {
                bringSignUp(binding)
            }
        }
        viewModel.liveSignInValidation.observe(this){
            when(it){
                false -> shortToast(this, Tools.error)
                else -> {}
            }
        }
    }

    private fun bringSignIn(binding: ActivityLoginBinding) {
        binding.llRegister.animate()?.translationX(-1000f)?.duration = 1000
        binding.llSignIn.animate()?.translationX(0f)?.duration = 1000
        binding.etUserId.text = "".toEditable()
        binding.etUserPassword.text = "".toEditable()
        binding.etUserName.text = "".toEditable()
    }
    private fun bringSignUp(binding: ActivityLoginBinding) {
        binding.llRegister.animate()?.translationX(0f)?.duration = 1000
        binding.llSignIn.animate()?.translationX(1000f)?.duration = 1000
        binding.etUserIdSign.text = "".toEditable()
        binding.etUserPasswordSign.text = "".toEditable()
    }
}